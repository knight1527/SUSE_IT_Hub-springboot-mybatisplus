package icu.duanqihang.suse_it.service.impl;

import icu.duanqihang.suse_it.entity.Resp;
import icu.duanqihang.suse_it.service.FileUploadService;
import icu.duanqihang.suse_it.utils.UploadUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Component
public class FileUploadServiceImpl implements FileUploadService {
    public static final String FILEPATH = "/springbootUpload/upload/imgs/";
    @Override
    public Resp<String> upload(MultipartFile file) {
        if(file.isEmpty()){
            return Resp.fail("400","文件为空!");
        }
        String originalFilename = file.getOriginalFilename();
        //以时间戳重命名文件并获得文件后缀
        String fileName =
                System.currentTimeMillis()+"."+originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        File dest = UploadUtils.getImgDirFile();
        System.out.println(dest.getAbsolutePath());
        try {
            // 构建真实的文件路径
            File newFile = new File(dest.getAbsolutePath() + File.separator + fileName);
            System.out.println(newFile.getAbsolutePath());
            file.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
            return Resp.fail("500","上传失败！");
        }
        return Resp.success(FILEPATH+fileName);
    }
}
