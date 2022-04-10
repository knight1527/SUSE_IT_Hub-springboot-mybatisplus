package icu.duanqihang.suse_it.controller;

import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Comment;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.service.CommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    BlogService blogService;

    @RequestMapping(value = "/comments",method = RequestMethod.POST)
    public String getCommentByBlogId(
            @RequestParam(value = "parentCommentId")Long parentCommentId,
            @RequestParam(value = "blogId")Long blogId,
            @RequestParam(value = "content")String content,
            Model model,
            HttpSession session
    ) {
        User user = (User)session.getAttribute("user");
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setContent(content);
        comment.setUserId(user.getId());
        comment.setParentCommentId(parentCommentId);
        comment.setLikes(0);
        commentService.insertComment(comment);
        //更新评论信息
        model.addAttribute("AuthorId",blogService.getEntityAuthorId(blogId));
        List<Comment> replyComments = commentService.getCommentByBlogId(blogId);
        //为子评论添加作者信息
        replyComments.forEach(t -> {
            List<Comment> tep = t.getReplyComments();
            tep.forEach(d -> {
                d.setUser(commentService.getCommentUser(d.getId()));
            });
            t.setReplyComments(tep);
        });
        model.addAttribute("replyComments",replyComments);
        Blog blog = blogService.getSampleBlogOrResource(blogId);
        blog.setCommentNumber(blog.getCommentNumber()+1);
        blogService.updateSampleBlogOrResource(blog);
        model.addAttribute("commentNumber",commentService.countEntityComment(blogId));
        return "fragment_::commentList";
    }

    @RequestMapping(value = "commentlikes",method = RequestMethod.GET)
    public String coomentlikes(
            @Param(value = "id")Long id,
            HttpSession session,
            Model model
    ){
        List<Long> commentlikesList = (List<Long>)session.getAttribute("commentlikesList");
        if(commentlikesList==null){
            commentlikesList=new ArrayList<>();
        }
        model.addAttribute("replyId",id);
        if(commentlikesList.isEmpty()||!commentlikesList.contains(id)){
            commentlikesList.add(id);
            Comment comment=commentService.getCommentDetail(id);
            comment.setLikes(comment.getLikes()+1);
            commentService.updataComment(comment);
            model.addAttribute("replyLikes",comment.getLikes());
        }else{
            model.addAttribute("replyLikes",commentService.getCommentDetail(id).getLikes());
            return "fragment_::commentlikesed";
        }
        session.setAttribute("commentlikesList",commentlikesList);
        return "fragment_::commentlikes";
    }
}
