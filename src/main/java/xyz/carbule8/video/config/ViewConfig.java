package xyz.carbule8.video.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.carbule8.video.interceptor.AdminInterceptor;

@Configuration
public class ViewConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/page", "/");
        registry.addRedirectViewController("/page/", "/");
        registry.addRedirectViewController("/watch", "/");
        registry.addRedirectViewController("/watch/", "/");
        registry.addRedirectViewController("/list/*", "/list");
        registry.addRedirectViewController("/list/page/", "/list");
        registry.addRedirectViewController("/delete", "/list");
        registry.addRedirectViewController("/delete/", "/list");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/delete/**", "/list", "/list/**", "/list", "/upload");
    }
}
