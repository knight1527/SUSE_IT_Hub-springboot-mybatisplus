package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.duanqihang.suse_it.pojo.Blog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 文章相关方法
     * @return
     */
    List<Blog> getDefaultBlogPage();

    /**
     * 获得文章详情
     */
    Blog getBlogDetailById(@Param(value = "id") Long id);

    /**
     * 资源相关方法
     * @return
     */
    List<Blog> sampleResourcesPages();

    List<Blog> getUserBlogCollects(@Param(value = "id")Long id);

    List<Blog> getUserResourceCollect(@Param(value = "id")Long id);

    /**
     * 删除收藏关系
     */
    void deleteUserCollect(@Param(value = "userId") Long userId,@Param(value = "blogId") Long blogId);

    /**
     * 删除指定对象的所有收藏关系
     */
    void deleteBlogCollect(@Param(value = "blogId") Long blogId);

    /**
     * 添加收藏关系
     */
    void insertUserCollect(@Param(value = "userId") Long userId,@Param(value = "blogId") Long blogId);
    /**
     * 获得指定用户的所有实体
     */
    List<Blog> getUserAllBlog(@Param(value = "userId") Long userId,@Param(value = "type")boolean type);

    /**
     * 获取指定标签的所有实体
     */
    List<Blog> getTagEntity(@Param(value = "tagId") Long tagId,@Param(value = "type")boolean type);
}
