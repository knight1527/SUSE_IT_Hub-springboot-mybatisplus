package icu.duanqihang.suse_it.controller;

import icu.duanqihang.suse_it.entity.Resp;
import icu.duanqihang.suse_it.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@RestController
@RequestMapping(value = "/upload")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping(method = RequestMethod.POST)
    private Resp<String> upload(@RequestParam("file")MultipartFile file){
        return fileUploadService.upload(file);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    private String test(){
        return "TEst";
    }
}
