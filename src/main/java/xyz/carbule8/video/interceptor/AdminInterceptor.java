package xyz.carbule8.video.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object loginSessionId = session.getAttribute("login_session_id");
        if (session.getId().equals(loginSessionId)) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
