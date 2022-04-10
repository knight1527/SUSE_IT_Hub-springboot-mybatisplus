package icu.duanqihang.suse_it.controller.user;

import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
public class RequestController {

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;


    @RequestMapping(value = "/rating",method = RequestMethod.GET)
    public String rating(
            HttpSession session,
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "score") int score
    ){
        List<Long> ratinglist = (List<Long>)session.getAttribute("ratinglist");
        if(ratinglist==null){
            ratinglist=new ArrayList<>();
        }
        if(ratinglist.isEmpty()||!ratinglist.contains(id)){
            ratinglist.add(id);
            Blog blog = blogService.getSampleBlogOrResource(id);
            blog.setScore((score+blog.getScore())/2);
            blogService.updateSampleBlogOrResource(blog);
        }else{
            return "只能评一次哦！！";
        }
        session.setAttribute("ratinglist",ratinglist);
        return "感谢您的评价！！";
    }
    @RequestMapping(value = "/unfavorite",method = RequestMethod.GET)
    public String unfavorite(
            @RequestParam(value = "id")Long id,
            @RequestParam(value = "blogId")Long blogId
    ){
        blogService.deleteUserCollect(id,blogId);
        return "取消成功！";
    }
    @RequestMapping(value = "/unsubscribe",method = RequestMethod.GET)
    public String ubsubscribe(
            @RequestParam(value = "id")Long id,
            @RequestParam(value = "targetUserId")Long targetUserId
    ){
        userService.deleteUserFocus(id,targetUserId);
        return "取消成功！";
    }
    @RequestMapping(value = "/focus",method = RequestMethod.GET)
    public String focus(
            @RequestParam(value = "flag")boolean flag,
            @RequestParam(value = "id")Long id,
            @RequestParam(value = "targetId")Long targetId
    ){
        if(flag){
            userService.insertFocus(id,targetId);
        }else{
            userService.deleteUserFocus(id,targetId);
        }
        return "操作成功!";
    }

    @RequestMapping(value = "/clearRegisterMessage",method = RequestMethod.GET)
    public String clearRegisterMessage(
            HttpSession session
    ){
        session.removeAttribute("registerMessage");
        return "";
    }
}
