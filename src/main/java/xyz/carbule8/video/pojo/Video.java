package xyz.carbule8.video.pojo;

import java.util.Date;

public class Video {
    private String vId;

    private String vName;

    private Date uploadTime;

    private String vStatus;

    private String vSuffix;

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId == null ? null : vId.trim();
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName == null ? null : vName.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getvStatus() {
        return vStatus;
    }

    public void setvStatus(String vStatus) {
        this.vStatus = vStatus == null ? null : vStatus.trim();
    }

    public String getvSuffix() {
        return vSuffix;
    }

    public void setvSuffix(String vSuffix) {
        this.vSuffix = vSuffix == null ? null : vSuffix.trim();
    }
}