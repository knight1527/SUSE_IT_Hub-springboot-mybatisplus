package icu.duanqihang.suse_it.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.mapper.BlogMapper;
import icu.duanqihang.suse_it.mapper.TagMapper;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.service.BlogService;
import icu.duanqihang.suse_it.utils.MarkdownUtils;
import icu.duanqihang.suse_it.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogMapper blogMapper;

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getDefaultBlogPage();
    }

    @Override
    public PageInfo<Blog> getPages(int pageNum,int pageSize) {
        List<Blog> blogs = blogMapper.getDefaultBlogPage();
        return PageUtils.getPageInfo(blogs,pageNum,pageSize);
    }

    @Override
    public List<Blog> getSampleResources() {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("type",false)
                .eq("published",true)
                .orderByDesc("score")
                .orderByDesc("update_time");
        PageHelper.startPage(1,5);
        return blogMapper.selectList(wrapper);
    }

    /**
     * 资源页面资源分页
     *
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageInfo<Blog> getResourcePages(int pageNum, int pageSize) {
        List<Blog> blogs = blogMapper.sampleResourcesPages();
        return PageUtils.getPageInfo(blogs,pageNum,pageSize);
    }

    @Override
    public PageInfo<Blog> getUserBlog(Long id,int pageNum,int pageSize) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id)
                .eq("type",true);
        return PageUtils.getPageInfo(blogMapper.selectList(wrapper),pageNum,pageSize);
    }

    @Override
    public PageInfo<Blog> getUserResource(Long id,int pageNum,int pageSize) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",id)
                .eq("type",false);
        return PageUtils.getPageInfo(blogMapper.selectList(wrapper),pageNum,pageSize);
    }

    @Override
    public PageInfo<Blog> getUserBlogCollect(Long id,int pageNum,int pageSize) {
        List<Blog> userBlogCollects = blogMapper.getUserBlogCollects(id);
        return PageUtils.getPageInfo(userBlogCollects,pageNum,pageSize);
    }

    @Override
    public PageInfo<Blog> getUserResourceCollect(Long id,int pageNum,int pageSize) {
        List<Blog> userBlogCollects = blogMapper.getUserResourceCollect(id);
        return PageUtils.getPageInfo(userBlogCollects,pageNum,pageSize);
    }

    @Override
    public List<Blog> getUserResourceCollectList(Long id) {
        return blogMapper.getUserResourceCollect(id);
    }

    /**
     * 获得指定文章详情，内容markdown转html
     *
     * @param id
     */
    @Override
    public Blog getBlogAndConvertById(Long id) {
        Blog blog = blogMapper.getBlogDetailById(id);
        Blog rel = new Blog();
        BeanUtils.copyProperties(blog,rel);
        String content = rel.getContent();
        rel.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return rel;
    }

    /**
     * 获得文章或者资源基本信息，用于ajax实现小功能
     *
     * @param id
     */
    @Override
    public Blog getSampleBlogOrResource(Long id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        return blogMapper.selectOne(wrapper);
    }

    /**
     * 更新文章或者资源基本信息
     *
     * @param blog
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSampleBlogOrResource(Blog blog) {
        blogMapper.updateById(blog);
    }

    /**
     * @param judge 0表示判断资源，1表示判断博客
     * @param userId
     * @param blogId
     * @return
     */
    @Override
    public boolean judgeUserCollected(int judge,Long userId, Long blogId) {
        if(judge==0){
            List<Blog> userResourceCollect = blogMapper.getUserResourceCollect(userId);
            List<Long> collect = userResourceCollect.stream().map(Blog::getId).collect(Collectors.toList());
            if(collect.contains(blogId)){
                return true;
            }else{
                return false;
            }
        }else{
            List<Blog> userResourceCollect = blogMapper.getUserBlogCollects(userId);
            List<Long> collect = userResourceCollect.stream().map(Blog::getId).collect(Collectors.toList());
            if(collect.contains(blogId)){
                return true;
            }else{
                return false;
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserCollect(Long userId, Long blogId) {
        blogMapper.deleteUserCollect(userId, blogId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertUserCollect(Long userId, Long blogId) {
        blogMapper.insertUserCollect(userId,blogId);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertBlog(Blog blog) {
        return blogMapper.insert(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlog(Long blogId) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id",blogId);
        blogMapper.delete(wrapper);
        blogMapper.deleteBlogCollect(blogId);
        tagMapper.deleteBlogTags(blogId);
    }

    /**
     * 获取指定用户的实体
     *
     * @param userId
     */
    @Override
    public List<Blog> getUserAllEntity(Long userId,boolean type) {
        return blogMapper.getUserAllBlog(userId,type);
    }

    /**
     * 获取指定标签的所有实体
     *
     * @param tagId
     * @param type
     */
    @Override
    public PageInfo<Blog> getTagEntity(Long tagId, boolean type,int pageNum,int pageSize) {
        List<Blog> tagEntity = blogMapper.getTagEntity(tagId,type);
        return PageUtils.getPageInfo(tagEntity,pageNum,pageSize);
    }

    /**
     * 获得指定实体的作者id
     *
     * @param blogId
     */
    @Override
    public Long getEntityAuthorId(Long blogId) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id",blogId);
        return blogMapper.selectOne(wrapper).getUserId();
    }

    /**
     * 搜索
     *
     * @param query
     */
    @Override
    public List<Blog> getSearchResults(String query) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.like("content",query)
                .eq("published",true)
                .orderByDesc("score","collect","likes","update_time");
        return blogMapper.selectList(wrapper);
    }


}
