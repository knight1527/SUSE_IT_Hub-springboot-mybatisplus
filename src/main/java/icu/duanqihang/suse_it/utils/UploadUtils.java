package icu.duanqihang.suse_it.utils;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class UploadUtils {
    /** 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
     */

    public final static String IMG_PATH_PREFIX = "springbootUpload/upload/imgs";

    public static File getImgDirFile(){

        // 构建上传文件的存放 "文件夹" 路径
        String fileDirPath = new String("/" + IMG_PATH_PREFIX);

        File fileDir = new File(fileDirPath);
        if(!fileDir.exists()){
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }


    public static boolean judgeFileType(InputStream inputStream) throws IOException {

        String suffix = FileTypeUtils.getFileTypeByMagicNumber(inputStream);

        String[] suffixparts= {"jpg","png","gif"};

        for(String s:suffixparts){
            if(s.equals(suffix)){
                return true;
            }
        }
        return false;
    }
}
