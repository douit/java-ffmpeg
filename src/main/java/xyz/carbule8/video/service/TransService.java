package xyz.carbule8.video.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ListObjectsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.carbule8.video.command.CaptureScreenshotsCommand;
import xyz.carbule8.video.command.SliceUpCommand;
import xyz.carbule8.video.config.OSSConfig;
import xyz.carbule8.video.config.VideoConfig;
import xyz.carbule8.video.exception.CaptureException;
import xyz.carbule8.video.exception.TranscodingException;
import xyz.carbule8.video.exception.UploadOSSException;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.pojo.VideoStatus;
import xyz.carbule8.video.util.SystemUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TransService {
    private final VideoService videoService;

    private final OSSConfig ossConfig;

    private final VideoConfig videoConfig;

    public TransService(@Lazy VideoService videoService, OSSConfig ossConfig, VideoConfig videoConfig) {
        this.videoService = videoService;
        this.ossConfig = ossConfig;
        this.videoConfig = videoConfig;
    }

    @Async
    public void trans(Video video) throws TranscodingException {
        String path = videoConfig.getLocalPath() + video.getvId();
        mkdirPath(path);
        // 开始转码
        video.setvStatus(VideoStatus.TRANSCODING.toString());
        videoService.update(video);
        long start = System.currentTimeMillis(); // 任务开始时间
        try {
            new SliceUpCommand().execute(path + video.getvSuffix(), path);
//            new NewSliceUpCommand().execute(path + video.getvSuffix(), path);
            File outfile = new File(path);
            if (outfile.list() == null || outfile.list().length <= 10) {
                throw new TranscodingException("切片输出数量异常");
            }
            captureScreenshots(video);
            SystemUtils.deleteLocalFiles(new File(path + video.getvSuffix()));
            uploadOSS(video);
            // 删除原视频 节省空间
            SystemUtils.deleteLocalFiles(new File(videoConfig.getLocalPath() + video.getvId()));
        } catch (Exception e) {
            deleteFailedVideoFiles(video);
            throw new TranscodingException("转码出现错误", e);
        }
        video.setvStatus(VideoStatus.COMPLETE.toString());
        videoService.update(video);
        log.info("{}视频切片任务完成 用时: {} 分钟", video.getvId(), (System.currentTimeMillis() - start) / 1000 / 60);
    }

    private void uploadOSS(Video video) throws UploadOSSException {
        String path = videoConfig.getLocalPath() + video.getvId(); // 每个方法里面都单独定义 保证多线程同时工作时 不会出现数据紊乱的情况
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndPoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        File[] input = new File(path).listFiles();
        try {
            for (File f : input) {
                log.info("OSS: {}/{}上传中...", video.getvId(), f.getName());
                ossClient.putObject(ossConfig.getBucketName(), video.getvId() + "/" + f.getName(), f);
            }
        } catch (OSSException | ClientException e) { // 捕获OSS异常 用来清理垃圾数据
            video.setvStatus(VideoStatus.UPLOAD_OSS_FAILED.toString());
            videoService.update(video);
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(ossConfig.getBucketName()).withMaxKeys(1000).withPrefix(video.getvId());
            SystemUtils.deleteOSSFiles(ossClient, listObjectsRequest, ossConfig.getBucketName());
            throw new UploadOSSException(e);
        } finally {
            ossClient.shutdown();
        }
    }

    private void captureScreenshots(Video video) throws IOException, CaptureException { // 视频截图
        String path = videoConfig.getLocalPath() + video.getvId(); // 每个方法里面都单独定义 保证多线程同时工作时 不会出现数据紊乱的情况
        String outLog = new CaptureScreenshotsCommand() {
            @Override
            public List<String> getCommand(Object... args) {
                List<String> command = new ArrayList<>();
                command.add("ffmpeg");
                command.add("-i");
                command.add(String.valueOf(args[0]));
                return command;
            }
        }.execute(path + video.getvSuffix());
        // 从视频信息中解析时长
        String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
        Matcher m = Pattern.compile(regexDuration).matcher(outLog);
        if (m.find()) {
            int videoSeconds = getSeconds(m.group(1));
            log.info("{}视频时长: {}, 开始时间: {}, 比特率: {}kb/s", video.getvId(), videoSeconds, m.group(2), m.group(3));
            int captureTimePointer = videoSeconds / 10;  // 截图点时间差

            for (int i = 1; i <= 10; i++) {
                log.info("{}/screen-{}.jpg截图中...", video.getvId(), i);
                StringBuilder screenshotPath = new StringBuilder(path);
                screenshotPath.append("/screen-");
                screenshotPath.append(i);
                screenshotPath.append(".jpg");
                int captureTime = captureTimePointer * i;
                new CaptureScreenshotsCommand().execute(captureTime, path + video.getvSuffix(), screenshotPath.toString());
                while (checkImageColor(screenshotPath.toString(), 0.9f)) { // 如果图片中连续相同的像素点大于设定的百分比 就判定为是纯色的图片
                    log.warn("{}/screen-{}.jpg出现纯色现象 开始重新截图", video.getvId(), i);
                    SystemUtils.deleteLocalFiles(new File(screenshotPath.toString()));

                    // 利用随机数来错开截图时间 防止出现同一个时间段内视频内容都为纯色
                    int randomTime = new Double(Math.random() * 10).intValue() + 1;
                    captureTime = captureTime / randomTime + randomTime;
                    // 对纯色照片重新截图
                    new CaptureScreenshotsCommand().execute(captureTime, path + video.getvSuffix(), screenshotPath.toString());
                }
            }
        } else {
            throw new CaptureException("截图任务未成功执行");
        }
    }

    // 存在截图不输出的bug
    private void capture(Video video) throws IOException {
        String path = videoConfig.getLocalPath() + video.getvId(); // 每个方法里面都单独定义 保证多线程同时工作时 不会出现数据紊乱的情况
        List<File> fileList = new ArrayList<>(Arrays.asList(new File(path).listFiles()));
        fileList.removeIf(file -> { // 删除集合中非ts文件
            String filename = file.getName();
            if (".ts".equals(filename.substring(filename.lastIndexOf(".")))) {
                return false;
            }
            return true;
        });
        Collections.shuffle(fileList);
        for (int i = 1; i <= 10; i++) {
            int captureTime = 1;
            StringBuilder screenshotPath = new StringBuilder(path);
            screenshotPath.append("/screen-");
            screenshotPath.append(i);
            screenshotPath.append(".jpg");
            log.info("{}截图中...", screenshotPath);
            new CaptureScreenshotsCommand().execute(captureTime, fileList.get(i), screenshotPath.toString());
            while (checkImageColor(screenshotPath.toString(), 0.9f)) {
                log.warn("{}/screen-{}.jpg出现纯色现象 开始重新截图...", video.getvId(), i);
                SystemUtils.deleteLocalFiles(new File(screenshotPath.toString()));
                captureTime++;
                new CaptureScreenshotsCommand().execute(captureTime, fileList.get(i), screenshotPath.toString());
            }
        }
    }

    // 删除不完整文件
    private void deleteFailedVideoFiles(Video video) {
        String path = videoConfig.getLocalPath() + video.getvId(); // 每个方法里面都单独定义 保证多线程同时工作时 不会出现数据紊乱的情况
        video.setvStatus(VideoStatus.TRANSCODING_FAILED.toString());
        videoService.update(video);
        SystemUtils.deleteLocalFiles(new File(path + video.getvId()));
    }

    // 创建目录
    private void mkdirPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            SystemUtils.deleteLocalFiles(file);
        }
        file.mkdirs();
    }

    //格式:"00:00:10.68" // 固定格式输出总秒数
    private int getSeconds(String duration) {
        int min = 0;
        String strings[] = duration.split(":");
        if (strings[0].compareTo("0") > 0) {
            min += Integer.valueOf(strings[0]) * 60 * 60;//秒
        }
        if (strings[1].compareTo("0") > 0) {
            min += Integer.valueOf(strings[1]) * 60;
        }
        if (strings[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strings[2]));
        }
        return min;
    }

    // 纯色图片检验
    private boolean checkImageColor(String imagePath, float percent) throws IOException {
        File file = new File(imagePath);
        if (!file.exists()) {
            log.error("{}文件不存在", imagePath);
            return true;
        }
        BufferedImage src = ImageIO.read(new File(imagePath));
        int height = src.getHeight();
        int width = src.getWidth();
        int count = 0, pixTemp = 0, pixel = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixel = src.getRGB(i, j);
                if (pixel == pixTemp) {// 如果上一个像素点和这个像素点颜色一样的话，就判定为同一种颜色
                    count++;
                }
                if ((float) count / (height * width) >= percent) { // 如果同一张照片中同一个像素点颜色超过百分比 判定为纯色图片
                    return true;
                }
                pixTemp = pixel;
            }
        }
        return false;
    }

}
