package icu.duanqihang.suse_it.service;

import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.pojo.Blog;

import java.util.Date;
import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public interface BlogService {
    List<Blog> getAllBlog();

    /**
     * 文章分页方法
    */
    PageInfo<Blog> getPages(int pageNum,int pageSize);

    /**
     * 获得首页资源实体
     * @return
     */
    List<Blog> getSampleResources();

    /**
     * 资源页面资源分页
     */
    PageInfo<Blog> getResourcePages(int pageNum,int pageSize);

    /**
     * 获得指定用户资源项
     * @param id
     * @return
     */
    PageInfo<Blog> getUserBlog(Long id,int pageNum,int pageSize);

    PageInfo<Blog> getUserResource(Long id,int pageNum,int pageSize);

    PageInfo<Blog> getUserBlogCollect(Long id,int pageNum,int pageSize);

    PageInfo<Blog> getUserResourceCollect(Long id,int pageNum,int pageSize);

    List<Blog> getUserResourceCollectList(Long id);
    /**
     * 获得指定文章详情，内容markdown转html
     */
    Blog getBlogAndConvertById(Long id);

    /**
     * 获得文章或者资源基本信息，用于ajax实现小功能
     */
    Blog getSampleBlogOrResource(Long id);
    /**
     * 更新文章或者资源基本信息
     */
    void updateSampleBlogOrResource(Blog blog);

    /**
     *
     * @param judge 0表示判断资源，1表示判断博客
     * @param userId
     * @param blogId
     * @return
     */
    boolean judgeUserCollected(int judge,Long userId,Long blogId);

    void deleteUserCollect(Long userId,Long blogId);

    void insertUserCollect(Long userId,Long blogId);

    int insertBlog(Blog blog);

    void deleteBlog(Long blogId);

    /**
     * 获取指定用户的实体
     */
    List<Blog> getUserAllEntity(Long userId,boolean type);

    /**
     * 获取指定标签的所有实体
     */
    PageInfo<Blog> getTagEntity(Long tagId,boolean type,int pageNum,int pageSize);

    /**
     * 获得指定实体的作者id
     */
    Long getEntityAuthorId(Long blogId);
    /**
     * 搜索
     */
    List<Blog> getSearchResults(String query);
}
