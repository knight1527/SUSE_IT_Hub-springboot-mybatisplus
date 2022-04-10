package icu.duanqihang.suse_it.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.service.TagService;
import icu.duanqihang.suse_it.utils.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Controller
public class IndexController {

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @GetMapping("/")
    public String index(
            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6") int pageSize,
            Model model
    ){
        model.addAttribute("bloglist",blogService.getPages(pageNum,pageSize));
        model.addAttribute("resourcelist",blogService.getSampleResources());
        return "index";
    }
    @GetMapping("/search")
    public String search(
            @RequestParam(value = "query",defaultValue = "")String query,
            @RequestParam(value = "searchIds",defaultValue = "-1")String searchIds,
            Model model
    ){
        List<List<Long>> tagEntity;
        if(!searchIds.equals("-1")){
            List<String> tagIds = Arrays.asList(searchIds.split(","));
            tagEntity = tagIds.stream()
                    .map(t -> {
                        return tagService.getTagEntityIds(Long.parseLong(t));
                    })
                    .collect(Collectors.toList());
        }else{
            tagEntity = null;
        }
        List<Blog> bloglist;
        List<Blog> relist;
        List<Blog> list = blogService.getSearchResults(query);
        List<Long> ids;
        if(tagEntity==null||!tagEntity.isEmpty()){
            ids = OtherUtils.getUnion(tagEntity);
            if(ids!=null){
                list = list.stream().filter(t->ids.contains(t.getId())).collect(Collectors.toList());
            }
        }
        bloglist = list.stream().filter(Blog::isType).collect(Collectors.toList());
        relist = list.stream().filter(t -> !t.isType()).collect(Collectors.toList());
        model.addAttribute("bloglist",bloglist);
        model.addAttribute("relist",relist);
        return "search";
    }
}
