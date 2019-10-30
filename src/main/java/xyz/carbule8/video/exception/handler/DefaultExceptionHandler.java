package xyz.carbule8.video.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import xyz.carbule8.video.exception.*;
import xyz.carbule8.video.pojo.HttpResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler { // 统一异常处理

    @ExceptionHandler(FormatNotSupportedException.class) // 返回json // 上传视频格式不正确
    public HttpResult formatNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, FormatNotSupportedException ex) {
        log.warn(ex.getMessage(), ex);
        return new HttpResult(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "");
    }

    @ExceptionHandler(NullUploadFileException.class) // 返回json // 上传内容为空
    public HttpResult NullUploadFileExceptionHandler(HttpServletRequest request, HttpServletResponse response, NullUploadFileException ex) {
        log.warn(ex.getMessage(), ex);
        return new HttpResult(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), "");
    }

    // 异步异常 无法捕获
    /*@ExceptionHandler(TranscodingException.class) // 返回json // 视频转码出现异常
    public HttpResult transcodingExceptionHandler(HttpServletRequest request, HttpServletResponse response, TranscodingException ex) {
        log.error(ex.getMessage(), ex);
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
    }*/

    @ExceptionHandler(VideoNotFoundException.class) // 返回视图 // 用户访问的视频不存在
    public ModelAndView videoNotFoundExceptionHandler(HttpServletRequest request, HttpServletResponse response, VideoNotFoundException ex) {
        log.warn(ex.getMessage(), ex);
        response.setStatus(HttpStatus.NOT_FOUND.value());
        return getModelAndView("error", ex.getMessage());
    }

    @ExceptionHandler(UploadException.class) // 返回json // 用户视频上传出现异常
    public HttpResult uploadExceptionHandler(HttpServletRequest request, HttpServletResponse response, UploadException ex) {
        log.error(ex.getMessage(), ex);
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
    }

    // 异步异常 无法捕获
    /*@ExceptionHandler(UploadOSSException.class) // 返回json // 后台上传视频到oss出现异常
    public HttpResult uploadOSSExceptionHandler(HttpServletRequest request, HttpServletResponse response, UploadOSSException ex) {
        log.error(ex.getMessage(), ex);
        return new HttpResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
    }*/

    @ExceptionHandler(VideoNotCompleteException.class) // 返回视图 // 用户访问的视频还没有进入完成状态
    public ModelAndView videoNotCompleteExceptionHandler(HttpServletRequest request, HttpServletResponse response, VideoNotCompleteException ex) {
        log.warn(ex.getMessage());
        return getModelAndView("error", ex.getMessage());
    }

    @ExceptionHandler(MultipartException.class) // 用户上传文件中如果关闭或者刷新了页面会报这个异常
    public void MultipartExceptionHandler(HttpServletRequest request, HttpServletResponse response, MultipartException ex) {
        log.error("用户取消了上传操作");
        log.error(ex.getMessage());
    }

    @ExceptionHandler // 返回视图 // 其他异常
    public ModelAndView defaultHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return getModelAndView("error", "系统出现异常 请联系管理员");
    }

    private ModelAndView getModelAndView(String viewName, String message) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("message", message);
        return modelAndView;
    }

}
