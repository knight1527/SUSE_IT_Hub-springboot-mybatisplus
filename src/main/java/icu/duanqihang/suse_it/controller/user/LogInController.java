package icu.duanqihang.suse_it.controller.user;

import com.google.code.kaptcha.Producer;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.UserService;
import icu.duanqihang.suse_it.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
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
@RequestMapping("/log")
public class LogInController {
    @Autowired
    UserService userService;

    @Autowired
    Producer kaptchaProducer;

    @GetMapping()
    public String log(){
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String kaptchaCode,
            HttpSession session,
            RedirectAttributes attributes
    ){
        String kaptcha = (String)session.getAttribute("kaptcha");
        if(!kaptchaCode.equals(kaptcha)){
            attributes.addFlashAttribute("message","验证码错误，请点击验证码刷新重试！！");
            return "redirect:/log";
        }
        List<User> users = userService.getAllUsers(email);
        if(users.isEmpty()){
            attributes.addFlashAttribute("message","用户不存在请注册！！");
            return "redirect:/log";
        }else{
            User user = userService.checkUser(email, password);
            if(user == null){
                attributes.addFlashAttribute("message","密码错误！！");
                return "redirect:/log";
            }else{
                user.setPassword(null);
                session.setAttribute("user",user);
                return "redirect:/";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:../";
    }

    @PostMapping("/register")
    public String addUser(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String kaptchaCode,
            HttpSession session,
            RedirectAttributes attributes
    ){
        String kaptcha = (String)session.getAttribute("kaptcha");
        if(!kaptchaCode.equals(kaptcha)){
            attributes.addFlashAttribute("message","验证码错误，请点击验证码刷新重试！！");
            return "redirect:/log";
        }
        List<User> list = userService.getAllUsers(email);
        if(!list.isEmpty()){
            attributes.addFlashAttribute("message","email 已经存在请登录！");
            return "redirect:/log";
        }
        User user = new User();
        user.setEmail(email);
        user.setNickname(email);
        password = password.split(",")[0];
        user.setPassword(MD5Utils.code(password));
        user.setAvatar("/img/doge2.jpg");
        userService.addUser(user);
        User reluser = userService.checkUser(email,password);
        session.setAttribute("user",reluser);
        session.setAttribute("registerMessage","记得去个人中心完善个人资料哦！！");
        return "redirect:/";
    }
    /**
     * 验证码
     */
    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void getKaptcha(
            HttpSession session,
            HttpServletResponse response
    ){
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha", text);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            System.out.println("响应验证码失败:" + e.getMessage());
        }
    }
}
