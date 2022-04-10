package icu.duanqihang.suse_it.controller;

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

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Controller
public class BlogController {
    private static final String INPUT = "blog_input";

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @Autowired
    TagService tagService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    TypeService typeService;

    @Autowired
    CommentService commentService;

    @GetMapping("/community")
    public String community(
            @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6") int pageSize,
            Model model
    ){
        List<Type> types=typeService.getAllTypes();
        types.forEach(t -> {
            List<Tag> tags = t.getTags();
            tags.forEach(d -> {
                d.setBlogCount(tagService.getTagsBlogCount(d.getId()));
            });
            t.setTags(tags);
        });
        model.addAttribute("types",types);
        model.addAttribute("bloglist",blogService.getPages(pageNum,pageSize));
        model.addAttribute("defaultTagType",typeService.getType(1L));
        return "community";
    }

    /**
     * blog 详情
     * @param id
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ){
        Blog blog = blogService.getBlogAndConvertById(id);
        blog.setCommentNumber(commentService.countEntityComment(id));
        blog.setLink(OtherUtils.getUrl(blog.getLink()));
        model.addAttribute("blog",blog);
        User user = (User)session.getAttribute("user");
        model.addAttribute("collectJudge",blogService.judgeUserCollected(1,user.getId(),id));
        model.addAttribute("focusFlag",userService.getFocus(user.getId()).contains(blog.getUser().getId()));
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
        return "blog_content";
    }

    /**
     * 详情页面的小功能ajax
     */
    @RequestMapping(value = "/dianzan",method = RequestMethod.GET)
    public String dianzan(
            HttpSession session,
            @RequestParam(value = "id") Long id,
            Model model
    ){
        List<Long> dianzanlist = (List<Long>)session.getAttribute("dianzanlist");
        if(dianzanlist==null){
            dianzanlist=new ArrayList<>();
        }
        if(dianzanlist.isEmpty()||!dianzanlist.contains(id)){
            dianzanlist.add(id);
            Blog blog = blogService.getSampleBlogOrResource(id);
            blog.setLikes(blog.getLikes()+1);
            blogService.updateSampleBlogOrResource(blog);
            model.addAttribute("blog",blogService.getBlogAndConvertById(blog.getId()));
        }else{
            model.addAttribute("blog",blogService.getBlogAndConvertById(id));
            return "fragment_::dianzaned";
        }
        session.setAttribute("dianzanlist",dianzanlist);
        return "fragment_::dianzan";
    }
    @RequestMapping(value = "/shoucang",method = RequestMethod.GET)
    public String shoucang(
            @RequestParam(value = "className")String className,
            @RequestParam(value = "id")Long id,
            HttpSession session
    ){
        List<String> list = new ArrayList<>(Arrays.asList(className.split(" ")));
        User user = (User)session.getAttribute("user");
        if(list.contains("m-color-jianghuang")||list.contains("m-color-background-jianghuang")){
            blogService.deleteUserCollect(user.getId(),id);
            Blog blog = blogService.getSampleBlogOrResource(id);
            blog.setCollect(blog.getCollect()-1);
            blogService.updateSampleBlogOrResource(blog);
            return "fragment_::shoucang";
        }else{
            blogService.insertUserCollect(user.getId(),id);
            Blog blog = blogService.getSampleBlogOrResource(id);
            blog.setCollect(blog.getCollect()+1);
            blogService.updateSampleBlogOrResource(blog);
            return "fragment_::shoucanged";
        }
    }

    /**
     * 新增修改
     */
    @GetMapping("/blog/input")
    public String blogInput(
            Model model
    ){
        Blog blog = new Blog();
        blog.setAvatar("/img/default_picture/dream.jpg");
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("defaultTagType",typeService.getType(1L));
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @GetMapping("/blog/{id}/input")
    public String updateBlog(
            Model model,
            @PathVariable(value = "id")Long id
    ){
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("defaultTagType",typeService.getType(1L));
        Blog blog = blogService.getSampleBlogOrResource(id);
        //设置博客多标签
        blog.setTagIds(OtherUtils.getTagIds(tagService.getTags(blog.getId())));
        model.addAttribute("blog",blog);
        return INPUT;
    }

    @PostMapping("/blog/contentInput")
    public String blogSave(
            @RequestPart("file") MultipartFile file,
            Blog blog,
            HttpSession session,
            RedirectAttributes attributes
    ){
        if(!file.isEmpty()){
            String avatar = fileUploadService.upload(file).getBody();
            blog.setAvatar(avatar);
        }

        int flag;
        if(blog.getId()==null){
            User user = (User)session.getAttribute("user");
            blog.setUserId(user.getId());
            blog.setLikes(0);
            blog.setCommentNumber(0);
            blog.setType(true);
            blog.setScore(1);
            blog.setPublished(false);
            blog.setCollect(0);
            flag = blogService.insertBlog(blog);
            //插入博客标签关系
            String tagIds = blog.getTagIds();
            if(tagIds!=null&&!"".equals(tagIds)){
                tagService.insertBlog_Tag(blog.getId(),new ArrayList<>(Arrays.asList(tagIds.split(","))));
            }
        }else{
            if(!(blog.getTagIds()==null||blog.getTagIds()=="")){
                tagService.deleteBlogTags(blog.getId());
                tagService.insertBlog_Tag(blog.getId(),new ArrayList<>(Arrays.asList(blog.getTagIds().split(","))));
            }
            blogService.updateSampleBlogOrResource(blog);
            flag=1;
        }
        if(flag == 1){
            attributes.addFlashAttribute("message", "操作成功!");
        }else{
            attributes.addFlashAttribute("message","数据已存在");
        }
        return "redirect:/self";
    }

    @GetMapping("/blog/{id}/delete")
    public String deleteBlog(
            @PathVariable(value = "id")Long id
    ){
        blogService.deleteBlog(id);
        return "redirect:/self";
    }
    @RequestMapping(value = "/tagItems",method = RequestMethod.GET)
    public String communityTagAjax(
            @RequestParam(value = "tagId")Long tagId,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            @RequestParam(value = "pageSize",defaultValue = "6")int pageSize,
            Model model
    ){
        model.addAttribute("tagId",tagId);
        if(tagId==-1){
            model.addAttribute("bloglist",blogService.getPages(pageNum,pageSize));
        }else{
            model.addAttribute("bloglist",blogService.getTagEntity(tagId,true,pageNum,pageSize));
        }
        return "fragment_::tagEntity";
    }

}
