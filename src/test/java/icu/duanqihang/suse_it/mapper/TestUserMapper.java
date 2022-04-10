package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Tag;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.service.TagService;
import icu.duanqihang.suse_it.service.UserService;
import icu.duanqihang.suse_it.utils.FileTypeUtils;
import icu.duanqihang.suse_it.utils.MD5Utils;
import icu.duanqihang.suse_it.utils.OtherUtils;
import icu.duanqihang.suse_it.utils.UploadUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
@SpringBootTest
public class TestUserMapper {
    @Autowired
    UserMapper userMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogService blogService;

    @Test
    public void testGetMapperBlogpages(){
        //blogService.getUserBlog(1L,1,5).getList().forEach(System.out::println);
        //blogMapper.getUserBlogCollects(1L).forEach(System.out::println);
        Blog blog = new Blog();
        blog.setTitle("testID2");
        blog.setDescription("testId2");
        blog.setUserId(1L);
        blogService.insertBlog(blog);
        System.out.println("+++++++++++++++++"+blog.getId()+"+++++++++++++++++++++");
    }
    @Test
    public void testBlogMapper(){
        /*PageHelper.startPage(1,5);
        List<Blog> defaultBlogPage = blogMapper.getDefaultBlogPage();
        PageInfo<Blog> blogs = new PageInfo<>(defaultBlogPage);
        System.out.println(blogs.getList());*/
        //Test用户全部实体
        //blogMapper.getUserAllBlog(1L,true).forEach(System.out::println);
        blogMapper.getTagEntity(1L,true).forEach(System.out::println);
    }
    @Test
    public void testPage(){
        Page<Blog> page = new Page<>(2,5);
        blogMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
    }
    @Test
    public void testResource(){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("type",false)
                .eq("published",true)
                .orderByDesc("score")
                .orderByDesc("update_time");
        PageHelper.startPage(1,5);
        List<Blog> blogs = blogMapper.selectList(wrapper);
        blogs.forEach(System.out::println);
    }
    /*测试资源分页*/
    @Test
    public void testResourcePages(){
        blogMapper.sampleResourcesPages().forEach(System.out::println);
    }

    //测试非实体对象查询
    @Autowired
    TableMapper tableMapper;
    @Test
    public void testOrder(){
        System.out.println(tableMapper.queryBlogTags("t_blog_tag","1"));
    }



    @Autowired
    UserService userService;

    @Test
    public void testUserMapper(){
        //System.out.println(blogMapper.getBlogDetailById(1L));
        //userMapper.deleteFocus(1L,3L);
        userMapper.getFocus(2L).forEach(System.out::println);
    }

    @Test
    public void TestUserService(){
        //userService.getAllUsers("1518607977@qq.com").forEach(System.out::println);
        System.out.println(userService.checkUser("1518607977@qq.com","1234"));
    }

    /*测试TagMapper*/
    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagService tagService;
    @Test
    public void testTagMapper(){
        //tagMapper.getDefaultTag().forEach(System.out::println);
        //tagMapper.deleteBlogTags(1466446911073538051L);
        //System.out.println(tagMapper.getTagEntityIds(1L));
        /*List<Integer> a = new ArrayList<>(Arrays.asList(new Integer[]{1,2,4}));
        List<Integer> b = new ArrayList<>(Arrays.asList(new Integer[]{5,32,2}));
        a.retainAll(b);
        System.out.println(a);*/
        /*List<List<Long>> list = new ArrayList<>();
        list.add(new ArrayList<>(Arrays.asList(new Long[]{1L,2L,4L})));
        list.add(new ArrayList<>(Arrays.asList(new Long[]{3L,2L,1L})));
        System.out.println(OtherUtils.getUnion(list));*/
    }

    @Test
    public void testGetTags(){
        tagService.getTags(4L).forEach(System.out::println);
    }
    /*测试typeMapper*/
    @Autowired
    TypeMapper typeMapper;
    @Test
    public void testTypeMapper(){
        typeMapper.getAllType().forEach(System.out::println);
    }
    @Test
    public void testType(){
        /*List<Tag> list = typeMapper.getTypeById(1L).getTags();
        System.out.println(OtherUtils.getTagIds(list));*/
        //tagMapper.insertUser_Tag(1L,3L);
        //System.out.println(System.getProperty("user.dir"));
        /*String searchIds = "";
        List<String> tagIds = Arrays.asList(searchIds.split(","));
        List<List<Long>> tagEntity = tagIds.stream()
                .map(t -> {
                    return tagService.getTagEntityIds(Long.parseLong(t));
                })
                .collect(Collectors.toList());
        System.out.println(tagEntity);*/
    }

    /*测试CommentMapper*/
    @Autowired
    CommentMapper commentMapper;

    @Test
    public void getComments(){
        commentMapper.getCommentByBlogId(1L).forEach(System.out::println);
        //System.out.println(commentMapper.getCommentUser(1L));
    }

    @Test
    public void testUtils() throws IOException {
        //System.out.println(OtherUtils.getUrl("https://www.bilibili.com"));
        //System.out.println(FileTypeUtils.getFileTypeByMagicNumber(new FileInputStream("D:\\c桌面\\Temp\\suse_it\\src" +
               // "\\main\\resources\\static\\img\\picture (2).jfif")));
        System.out.println(MD5Utils.code("dqhd12138"));
    }
}
