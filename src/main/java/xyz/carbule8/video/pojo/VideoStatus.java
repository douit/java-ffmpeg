package xyz.carbule8.video.pojo;

public enum  VideoStatus {
    UPLOAD_SUCCESS, // 上传完成
    TRANSCODING, // 转码中
    TRANSCODING_SUCCESS, // 转码成功
    TRANSCODING_FAILED, // 转码失败
    UPLOAD_OSS, //  上传oss中
    UPLOAD_OSS_FAILED, // 上传oss失败
    COMPLETE; // 完成
}
