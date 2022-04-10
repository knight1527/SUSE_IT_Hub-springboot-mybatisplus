package icu.duanqihang.suse_it.config;

import icu.duanqihang.suse_it.interceptor.FileUploadInterrupter;
import icu.duanqihang.suse_it.interceptor.LogInInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInInterceptor())
                .excludePathPatterns("/springbootUpload/**")
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/lib/**")
                .excludePathPatterns("/upload/**")
                .excludePathPatterns("/log/**")
                .excludePathPatterns("/")
                .addPathPatterns("/**");

        registry.addInterceptor(new FileUploadInterrupter())
                .addPathPatterns("/upload")
                .addPathPatterns("/update")
                .addPathPatterns("/blog/contentInput")
                .addPathPatterns("/resource/contentInput");
    }
    /*赋值文件读写权限*/
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\";
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+path);
    }*/
    /**
     * 静态资源处理
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File file = new File("/springbootUpload/");
        if (!file.exists()) {
            file.mkdirs();
        }
        registry.addResourceHandler("/springbootUpload/**").addResourceLocations("file:/springbootUpload/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
