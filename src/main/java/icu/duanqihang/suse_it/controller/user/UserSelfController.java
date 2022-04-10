package icu.duanqihang.suse_it.controller.user;

import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Tag;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.*;
import icu.duanqihang.suse_it.utils.OtherUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
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
@RequestMapping("/self")
public class UserSelfController {

    @Autowired
    TagService tagService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    UserService userService;

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping()
    public String UserSelf(
            HttpSession session,
            RedirectAttributes attributes,
            Model model
    ){
        User user = (User)session.getAttribute("user");
        if(user == null){
            attributes.addFlashAttribute("message","请登录！！");
            return "redirect:/log";
        }
        User tep = userService.getUserDetails(user.getId());
        session.setAttribute("user",tep);
        List<Tag> tags = userService.getUserDetails(tep.getId()).getTags();
        if(tags!=null){
            tep.setTags(tags);
            tep.setTagIds(OtherUtils.getTagIds(tags));
        }
        List<User> focusUsers = userService.getUserDetails(tep.getId()).getFocusUsers();
        if(focusUsers!=null){
            tep.setFocusUsers(focusUsers);
        }
        model.addAttribute("user",tep);
        model.addAttribute("userBlogs",blogService.getUserBlog(tep.getId(),1,4));
        model.addAttribute("userResources",blogService.getUserResource(tep.getId(),1,4));
        model.addAttribute("userBlogCollect",blogService.getUserBlogCollect(tep.getId(),1,4));
        model.addAttribute("userResourcesCollect",blogService.getUserResourceCollect(tep.getId(),1,4));
        model.addAttribute("types",typeService.getAllTypes());
        model.addAttribute("defaultTagType",typeService.getType(1L));
        return "self";
    }

    /**
     * 以下个人信息分页处理ajax
     * @param session
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping(value = "/rCPage",method = RequestMethod.GET)
    public String rCPage(
            HttpSession session,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            Model model
    ){
        System.out.println("===============================pageNum:"+pageNum);
        User user = (User)session.getAttribute("user");
        model.addAttribute("userResourcesCollect",
                blogService.getUserResourceCollect(user.getId(),pageNum, 4));
        return "fragment_::resourceCollectMenu";
    }

    @RequestMapping(value = "/bCPage",method = RequestMethod.GET)
    public String bCPage(
            HttpSession session,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            Model model
    ){
        System.out.println("===============================pageNum:"+pageNum);
        User user = (User)session.getAttribute("user");
        model.addAttribute("userBlogCollect",
                blogService.getUserBlogCollect(user.getId(),pageNum, 4));
        return "fragment_::blogCollectMenu";
    }

    @RequestMapping(value = "/selfBPage",method = RequestMethod.GET)
    public String selfBPage(
            HttpSession session,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            Model model
    ){
        System.out.println("===============================pageNum:"+pageNum);
        User user = (User)session.getAttribute("user");
        model.addAttribute("userBlogs",
                blogService.getUserBlog(user.getId(),pageNum, 4));
        return "fragment_::blogMenu";
    }

    @RequestMapping(value = "/selfRPage",method = RequestMethod.GET)
    public String selfRPage(
            HttpSession session,
            @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
            Model model
    ){
        System.out.println("===============================pageNum:"+pageNum);
        User user = (User)session.getAttribute("user");
        model.addAttribute("userResources",
                blogService.getUserResource(user.getId(),pageNum, 4));
        return "fragment_::resourceMenu";
    }
    /**
     * 标签分类ajax
     */
    @RequestMapping(value = "/tagTypes",method = RequestMethod.GET)
    public String tagTypes(
            @RequestParam(value = "value",defaultValue = "1")String value,
            Model model
    ){
        System.out.println("=================ajax=========");
        model.addAttribute("defaultTagType",typeService.getType(Long.parseLong(value)));
        return "fragment_::tagTypes";
    }

    /**
     * 用户信息更新
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String updateUser(
            @RequestPart("file") MultipartFile file,
            User user
    ){
        if(!file.isEmpty()){
            String avatar = fileUploadService.upload(file).getBody();
            user.setAvatar(avatar);
        }
        userService.updateUserById(user);
        String tagIds = user.getTagIds();
        String oldTagIds=OtherUtils.getTagIds(userService.getUserDetails(user.getId()).getTags());
        if(!tagIds.equals(oldTagIds)){
            tagService.deleteUserTags(user.getId());
            List<String> ids = new ArrayList<>(Arrays.asList(tagIds.split(",")));
            tagService.insertUser_Tag(user.getId(),ids);
        }
        return "redirect:/self";
    }
}
