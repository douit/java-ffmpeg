package xyz.carbule8.video.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.carbule8.video.config.VideoConfig;
import xyz.carbule8.video.exception.*;
import xyz.carbule8.video.pojo.HttpResult;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.pojo.VideoStatus;
import xyz.carbule8.video.service.VideoService;
import xyz.carbule8.video.service.impl.TransService;
import xyz.carbule8.video.util.SystemUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private TransService transService;

    @Autowired
    private VideoConfig videoConfig;


    @PostMapping("/upload")
    @ResponseBody
    public HttpResult upload(@ModelAttribute("file") MultipartFile file, Model model, HttpServletResponse response)
            throws IOException, FormatNotSupportedException, UploadException, TranscodingException, NullUploadFileException, UploadOSSException {

        if (file == null || file.isEmpty()) {
            throw new NullUploadFileException("上传视频不可为空");
        }

        String originalFilename = file.getOriginalFilename();
        Video video = new Video();
        video.setvId(UUID.randomUUID().toString());
        video.setvName(originalFilename.substring(0, originalFilename.lastIndexOf(".")));
        video.setvSuffix(originalFilename.substring(originalFilename.lastIndexOf(".")));

        // 判断文件类型
        List<String> list = new ArrayList<>();
        list.add(".mp4");
        list.add(".rmvb");
        list.add(".wmv");
        list.add(".avi");
        list.add(".mov");
        list.add(".flv");
        if (!list.contains(video.getvSuffix())) {
            throw new FormatNotSupportedException("文件格式不正确");
        }

        File upload = new File(videoConfig.getLocalPath());
        if (!upload.exists()) {
            upload.mkdirs();
        }
        try {
            file.transferTo(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
            video.setUploadTime(new Date());
            video.setvStatus(VideoStatus.UPLOAD_SUCCESS.toString()); // 状态: 上传成功
            videoService.insert(video);
        } catch (Exception e) {
            // 删除不完整上传视频
            log.warn("{}删除不完整视频中", video.getvId());
            SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
            if (e.getMessage().contains("Incorrect string value")) {
                throw new UploadException("文件名有特殊字符 请修改后重新上传", e);
            }
            throw new UploadException("文件上传出错", e);
        }

        // 加入任务队列
        transService.trans(video);
        log.info("{}视频上传成功", video.getvId());
        return new HttpResult(HttpStatus.OK.value(), "上传成功", "");
    }

    @GetMapping("/delete/{id}")
    public String deletePage(@PathVariable("id") String id, Model model) {
        try {
            Video selectVideo = videoService.findById(id);
            videoService.delete(id);
            if (VideoStatus.UPLOAD_SUCCESS.toString().equals(selectVideo.getvStatus()) ||
                    VideoStatus.TRANSCODING.toString().equals(selectVideo.getvStatus()) ||
                    VideoStatus.UPLOAD_OSS.toString().equals(selectVideo.getvStatus())) {
                throw new VideoNotCompleteException("该视频正在任务队列中 无法删除");
            }
        } catch (VideoNotFoundException | VideoNotCompleteException e) {
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        log.info("{}已被删除", id);
        return "redirect:/list";
    }

    @GetMapping("/watch/{id}")
    public String watchPage(@PathVariable("id") String id, Model model)
            throws VideoNotFoundException, VideoNotCompleteException {
        Video video = videoService.findById(id);
        if (video == null) {
            throw new VideoNotFoundException("视频不存在");
        }
        if (!VideoStatus.COMPLETE.toString().equals(video.getvStatus())) {
            throw new VideoNotCompleteException("视频正在处理中...");
        }
        model.addAttribute("data", video);
        model.addAttribute("accessUrl", videoConfig.getAccessUrl());
        return "watch";
    }

}
