package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.duanqihang.suse_it.pojo.Blog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
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
public class TestBlogMapper {

    @Autowired
    BlogMapper blogMapper;

    @Test
    public void test(){
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id",1);
        System.out.println(blogMapper.selectOne(wrapper));
    }

    @Test
    public void testJava(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List<String> rel = list.stream().skip(2).limit(1).collect(Collectors.toList());
        System.out.print(rel);
    }
}
