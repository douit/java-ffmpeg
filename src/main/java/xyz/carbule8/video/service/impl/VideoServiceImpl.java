package xyz.carbule8.video.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ListObjectsRequest;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.carbule8.video.config.OSSConfig;
import xyz.carbule8.video.config.VideoConfig;
import xyz.carbule8.video.exception.TranscodingException;
import xyz.carbule8.video.exception.UploadOSSException;
import xyz.carbule8.video.exception.VideoNotFoundException;
import xyz.carbule8.video.mapper.VideoMapper;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.pojo.VideoExample;
import xyz.carbule8.video.pojo.VideoStatus;
import xyz.carbule8.video.service.VideoService;
import xyz.carbule8.video.util.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private OSSConfig ossConfig;

    @Autowired
    private TransService transService;

    @Autowired
    private VideoConfig videoConfig;

    @Override
    public List<Video> findAll(VideoExample example) {
        return videoMapper.selectByExample(example);
    }

    @Override
    public Page<Video> findAll(xyz.carbule8.video.pojo.Page page) {
        return PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPage(() -> {
            VideoExample example = new VideoExample();
            example.setOrderByClause("upload_time desc");
            ((VideoService) AopContext.currentProxy()).findAll(example);
        });
    }

    @Override
    public Page<Video> findAllComplete(xyz.carbule8.video.pojo.Page page) {
        if (page.getPageNum() == null) {
            page.setPageNum(1);
        }
        return PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPage(() -> {
            VideoExample example = new VideoExample();
            example.setOrderByClause("upload_time desc");
            example.createCriteria().andVStatusEqualTo(VideoStatus.COMPLETE.toString());
            ((VideoService) AopContext.currentProxy()).findAll(example);
        });
    }

    @Override
    public Page<Video> findAll(String name, xyz.carbule8.video.pojo.Page page) {
        if (page.getPageNum() == null) {
            page.setPageNum(1);
        }
        if (name == null || "".equals(name)) {
            return ((VideoService) AopContext.currentProxy()).findAll(page);
        }
        return PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPage(() -> {
            VideoExample example = new VideoExample();
            example.createCriteria().andVNameLike("%" + name + "%");
            example.setOrderByClause("upload_time desc");
            ((VideoService) AopContext.currentProxy()).findAll(example);
        });
    }

    @Override
    public List<Video> findAllNotCompleteVideos() throws TranscodingException, IOException, UploadOSSException {
        VideoExample example = new VideoExample();
        example.createCriteria().andVStatusNotEqualTo(VideoStatus.COMPLETE.toString());
        List<Video> videos = videoMapper.selectByExample(example);
        for (Video video : videos) {
            File file = new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix());
            if (file.exists()) {
                video.setvStatus(VideoStatus.UPLOAD_SUCCESS.toString());
                ((VideoService) AopContext.currentProxy()).update(video);
                transService.trans(video); // 执行本地未完成任务
            } else {
                video.setvStatus(VideoStatus.TRANSCODING_FAILED.toString()); // 如果视频文件不完整 记录转码失败状态
                ((VideoService) AopContext.currentProxy()).update(video);
            }
        }
        return videos;
    }


    @Override
    public Video findById(String id) throws VideoNotFoundException {
        Video selectVideo = videoMapper.selectByPrimaryKey(id);
        if (selectVideo == null) {
            throw new VideoNotFoundException("视频不存在");
        }
        return selectVideo;
    }

    @Override
    public void insert(Video video) {
        videoMapper.insert(video);
    }

    @Override
    public void update(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    public void delete(String id) throws VideoNotFoundException {
        Video video = ((VideoService) AopContext.currentProxy()).findById(id);
        videoMapper.deleteByPrimaryKey(id);

        // 删除本地视频
        SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId() + video.getvSuffix()));
        SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId()));
        // 删除oss中视频
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndPoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfig.getBucketName()).withMaxKeys(1000).withPrefix(video.getvId());
        SystemUtils.deleteOSSFiles(ossClient, listObjectsRequest, ossConfig.getBucketName());
    }
}
