package icu.duanqihang.suse_it.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.mapper.TableMapper;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Tag;
import icu.duanqihang.suse_it.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@RestController
public class TestRequestController {

    @Autowired
    BlogService blogService;

    @Autowired
    TableMapper tableMapper;

    /*@GetMapping("/testTable")
    public PageInfo<Blog> getTest(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "1") int pageSize
                                  ){
         return blogService.getTagEntity(2L,true,pageNum,pageSize);
    }
    @GetMapping("/testFragment")
    public PageInfo<Blog> testFragment(
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum
    ){
                return blogService.getUserBlogCollect(1L,pageNum,
                        4);
    }*/
}
