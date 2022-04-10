package icu.duanqihang.suse_it.controller;

import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.pojo.*;
import icu.duanqihang.suse_it.service.*;
import icu.duanqihang.suse_it.utils.OtherUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Controller
public class ResourceController {

    private static final String INPUT = "resource_input";

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    CommentService commentService;

    @Autowired
    TagService tagService;

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @GetMapping("/resource")
    public String resource(
            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6") int pageSize,
            Model model,
            HttpSession session
    ){
        List<Type> types = typeService.getAllTypes();
        types.forEach(t -> {
            List<Tag> tags = t.getTags();
            tags.forEach(d -> {
                d.setResourceCount(tagService.getTagsReCount(d.getId()));
            });
            t.setTags(tags);
        });
        model.addAttribute("types",types);
        PageInfo<Blog> pages = blogService.getResourcePages(pageNum,pageSize);
        User user = (User)session.getAttribute("user");
        model.addAttribute("pages",OtherUtils.judgeResourceCollectTool(pages,user,blogService));
        model.addAttribute("defaultTagType",typeService.getType(1L));
      return "resource";
    }

    /**
     * resource 详情
     */
    @GetMapping("/resource/{id}")
    public String resource(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ){
        Blog resource = blogService.getBlogAndConvertById(id);
        resource.setCommentNumber(commentService.countEntityComment(id));
        resource.setLink(OtherUtils.getUrl(resource.getLink()));
        model.addAttribute("resource",resource);
        User user = (User)session.getAttribute("user");
        model.addAttribute("collectJudge",blogService.judgeUserCollected(0,user.getId(),id));
        model.addAttribute("focusFlag",userService.getFocus(user.getId()).contains(resource.getUser().getId()));
        List<Blog> sampleResources = blogService.getSampleResources();
        for(Blog i:sampleResources){
            i.setTags(tagService.getTags(i.getId()));
        }
        model.addAttribute("recommend",sampleResources);
        model.addAttribute("AuthorId",blogService.getEntityAuthorId(id));
        List<Comment> replyComments = commentService.getCommentByBlogId(id);
        //为子评论添加作者信息
        replyComments.forEach(t -> {
            List<Comment> tep = t.getReplyComments();
            tep.forEach(d -> {
                d.setUser(commentService.getCommentUser(d.getId()));
            });
            t.setReplyComments(tep);
        });
        model.addAttribute("replyComments",replyComments);
        return "resource_content";
    }

    @GetMapping("/resource/input")
    public String resourceInput(
            Model model
    ){
        Blog blog = new Blog();
        blog.setAvatar("/img/default_avatar/java.png");
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("defaultTagType",typeService.getType(1L));
        model.addAttribute("resource",blog);
        return INPUT;
    }
    @GetMapping("/resource/{id}/input")
    public String updataResource(
            Model model,
            @PathVariable(value = "id")Long id
    ){
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("defaultTagType",typeService.getType(1L));
        Blog resource = blogService.getSampleBlogOrResource(id);
        //设置博客多标签
        resource.setTagIds(OtherUtils.getTagIds(tagService.getTags(resource.getId())));
        model.addAttribute("resource",resource);
        return INPUT;
    }
    @PostMapping("/resource/contentInput")
    public String input(
            @RequestPart("file") MultipartFile file,
            Blog resource,
            HttpSession session,
            RedirectAttributes attributes
    ){
        if(!file.isEmpty()){
            String avatar = fileUploadService.upload(file).getBody();
            resource.setAvatar(avatar);
        }
        int flag;
        if(resource.getId()==null){
            User user = (User)session.getAttribute("user");
            resource.setUserId(user.getId());
            resource.setLikes(0);
            resource.setPublished(false);
            resource.setCommentNumber(0);
            resource.setType(false);
            resource.setScore(1);
            resource.setCollect(0);
            flag = blogService.insertBlog(resource);
            //插入博客标签关系
            String tagIds = resource.getTagIds();
            if(tagIds!=null&&!"".equals(tagIds)){
                tagService.insertBlog_Tag(resource.getId(),new ArrayList<>(Arrays.asList(tagIds.split(","))));
            }
        }else{
            if(!(resource.getTagIds()==null||resource.getTagIds()=="")){
                tagService.deleteBlogTags(resource.getId());
                tagService.insertBlog_Tag(resource.getId(),new ArrayList<>(Arrays.asList(resource.getTagIds().split(","))));
            }
            blogService.updateSampleBlogOrResource(resource);
            flag=1;
        }
        if(flag == 1){
            attributes.addFlashAttribute("message", "操作成功!");
        }else{
            attributes.addFlashAttribute("message","数据已存在");
        }
        return "redirect:/self";
    }


    @GetMapping("/resource/{id}/delete")
    public String deleteResource(
            @PathVariable(value = "id")Long id
    ){
        blogService.deleteBlog(id);
        return "redirect:/self";
    }

    @RequestMapping(value = "/reTagItems",method = RequestMethod.GET)
    public String communityTagAjax(
            @RequestParam(value = "tagId")Long tagId,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6")int pageSize,
            Model model
    ){
        model.addAttribute("tagId",tagId);
        if(tagId==-1){
            model.addAttribute("pages",blogService.getResourcePages(pageNum,pageSize));
        }else{
            model.addAttribute("pages",blogService.getTagEntity(tagId,false,pageNum,pageSize));
        }
        return "fragment_::tagEntityRe";
    }

    /**
     * 社区页面内直接收藏
     */
    @RequestMapping(value = "/reFavorite",method = RequestMethod.GET)
    @ResponseBody
    public String reFavorite(
            @RequestParam(value = "flag")boolean flag,
            @RequestParam(value = "id")Long id,
            HttpSession session
    ){
        User user = (User)session.getAttribute("user");
        if(flag){
            blogService.insertUserCollect(user.getId(),id);
        }else{
            blogService.deleteUserCollect(user.getId(),id);
        }
        return "操作成功!!";
    }
}
