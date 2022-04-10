package icu.duanqihang.suse_it.service;

import icu.duanqihang.suse_it.entity.Resp;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public interface FileUploadService {
    Resp<String> upload(MultipartFile file);
}
