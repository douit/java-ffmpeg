package xyz.carbule8.video.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
public class SystemUtils { // 自定义系统工具类

    private static final String NOTIFICATION_CONFIG_PROPERTIES = "config/notificationConfig.properties";

    // 递归删除文件
    public static void deleteLocalFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                log.info("LOCAL: {}/{}删除中...", file.getName(), f.getName());
                f.delete();
            }
        }
        log.info("LOCAL: {}删除中...", file.getName());
        file.delete();
    }

    // 密码盐加密
    public static String encryptPassword(String pwd, String salt) {
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder(DigestUtils.md5DigestAsHex(
                    (URLEncoder.encode(pwd, "utf-8") + DigestUtils.md5DigestAsHex(
                            URLEncoder.encode(salt, "utf-8").getBytes()
                    )).getBytes()
            ));
        } catch (UnsupportedEncodingException e) {
            log.error("密码加密失败", e);
        }
        return stringBuilder.reverse().toString();
    }


    /**
     * 使用加密的方式,利用465端口进行传输邮件,开启ssl
     *
     * @param emailContent 邮件发送内容
     */
    public static void sendMail(String emailContent) {
        log.info("邮箱发送开始调用...");
        try {
            InputStream inputStream = SystemUtils.class.getClassLoader().getResourceAsStream(NOTIFICATION_CONFIG_PROPERTIES);
            Properties properties = new Properties();
            properties.load(inputStream);
            String emailSendAddress = properties.getProperty("email.address.send");
            String emailSendPassword = properties.getProperty("email.password.send");
            String emailSendServerAddress = properties.getProperty("email.server.address.send");
            String emailReceiveAddress = properties.getProperty("email.address.receive");

            // Security.addProvider(new Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", emailSendServerAddress);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailSendAddress, emailSendPassword);
                }
            });
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailSendAddress));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailReceiveAddress, false));
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailReceiveAddress, false));
            msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(emailReceiveAddress, false));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            msg.setSubject(sdf.format(new Date()));
            msg.setText(emailContent);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("邮箱发送调用结束...");
    }

    /**
     * 调用server酱接口发送微信 需微信关注公众号 并获取SCKEY
     */
    public static String sendWeChat(String title, String content) throws IOException {
        log.info("微信发送开始调用...");
        title = URLEncoder.encode(title, "utf-8");
        content = URLEncoder.encode(content, "utf-8");

        InputStream inputStream = SystemUtils.class.getClassLoader().getResourceAsStream(NOTIFICATION_CONFIG_PROPERTIES);
        Properties properties = new Properties();
        properties.load(inputStream);
        String stringUrl = properties.getProperty("wechat.sckey");

        /** 发送请求 */
        URL url = new URL(stringUrl + "?text=" + title + "&desp=" + content);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        httpURLConnection.setDoInput(true);
        String responseMessage = httpURLConnection.getResponseMessage();
        log.info("微信发送调用结束...");
        return responseMessage;
    }


    // oss已上传文件删除
    public static void deleteOSSFiles(OSS ossClient, ListObjectsRequest objectsRequest, String bucketName) {
        ObjectListing objectListing = null;
        do {
            objectListing = ossClient.listObjects(objectsRequest);
            List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            for (OSSObjectSummary objectSummary : objectSummaries) {
                String objectName = objectSummary.getKey();
                if (ossClient.doesObjectExist(bucketName, objectName)) {
                    log.info("OSS: {}删除中...", objectName);
                    ossClient.deleteObject(bucketName, objectName);
                }
            }
        } while (objectListing.isTruncated());
        /*for (OSSObjectSummary objectSummary : ossClient.listObjects(objectsRequest).getObjectSummaries()) {
            String objectName = objectSummary.getKey();
            if (ossClient.doesObjectExist(bucketName, objectName)) {
                log.info("OSS: {}删除中...", objectName);
                ossClient.deleteObject(bucketName, objectName);
            }
        }*/
    }

}
