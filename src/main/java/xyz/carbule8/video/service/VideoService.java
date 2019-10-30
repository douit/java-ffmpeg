package xyz.carbule8.video.service;

import com.github.pagehelper.Page;
import xyz.carbule8.video.exception.TranscodingException;
import xyz.carbule8.video.exception.UploadException;
import xyz.carbule8.video.exception.UploadOSSException;
import xyz.carbule8.video.exception.VideoNotFoundException;
import xyz.carbule8.video.pojo.Video;
import xyz.carbule8.video.pojo.VideoExample;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    List<Video> findAll(VideoExample example);

    Page<Video> findAll(xyz.carbule8.video.pojo.Page page);

    Page<Video> findAllComplete(xyz.carbule8.video.pojo.Page page);

    Page<Video> findAll(String name, xyz.carbule8.video.pojo.Page page);

    List<Video> findAllNotCompleteVideos() throws InterruptedException, UploadException, TranscodingException, IOException, UploadOSSException;

    Video findById(String id) throws VideoNotFoundException;

    void insert(Video video);

    void update(Video video);

    void delete(String id) throws VideoNotFoundException;
}
