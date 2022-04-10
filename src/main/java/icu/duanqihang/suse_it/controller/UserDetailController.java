package icu.duanqihang.suse_it.controller;

import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Controller
public class UserDetailController {

    @Autowired
    UserService userService;

    @Autowired
    BlogService blogService;

    @GetMapping("/userdetail/{id}")
    public String detail(
            @PathVariable(value = "id")Long id,
            Model model,
            HttpSession session
    ){
        User targetUser = userService.getUserDetails(id);
        model.addAttribute("blogList",blogService.getUserAllEntity(id,true));
        model.addAttribute("reList",blogService.getUserAllEntity(id,false));
        User user = (User)session.getAttribute("user");
        model.addAttribute("focusFlag",userService.getFocus(user.getId()).contains(id));
        model.addAttribute("targetUser",targetUser);
        return "user_detail";
    }
}
