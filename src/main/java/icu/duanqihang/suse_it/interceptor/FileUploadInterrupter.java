package icu.duanqihang.suse_it.interceptor;

import icu.duanqihang.suse_it.utils.UploadUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class FileUploadInterrupter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        MultipartHttpServletRequest multipartfile = (MultipartHttpServletRequest)request;

        MultipartFile file = multipartfile.getFile("file");

        assert file != null;
        if(!file.isEmpty()){
            if(UploadUtils.judgeFileType(file.getInputStream())){
                return true;
            }
            response.setStatus(403);
            response.setHeader("message","file type error！！");
            return false;
        }
        return true;
    }
}
