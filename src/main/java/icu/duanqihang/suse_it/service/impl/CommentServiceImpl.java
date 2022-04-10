package icu.duanqihang.suse_it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.duanqihang.suse_it.mapper.CommentMapper;
import icu.duanqihang.suse_it.pojo.Comment;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    /**
     * 获取指定实体的全部评论
     *
     * @param blogId
     */
    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        return commentMapper.getCommentByBlogId(blogId);
    }

    /**
     * 获得指定评论的作者信息
     *
     * @param commentId
     */
    @Override
    public User getCommentUser(Long commentId) {
        return commentMapper.getCommentUser(commentId);
    }

    /**
     * 新增评论
     *
     * @param comment
     */
    @Transactional
    @Override
    public void insertComment(Comment comment) {
        commentMapper.insert(comment);
    }

    /**
     * 获得评论简单信息
     *
     * @param commentId
     */
    @Override
    public Comment getCommentDetail(Long commentId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("id",commentId);
        return commentMapper.selectOne(wrapper);
    }

    /**
     * 更新评论信息
     *
     * @param comment
     */
    @Transactional
    @Override
    public void updataComment(Comment comment) {
        commentMapper.updateById(comment);
    }

    /**
     * 获得指定实体的评论数目
     *
     * @param blogId
     */
    @Override
    public int countEntityComment(Long blogId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId);
        return commentMapper.selectCount(wrapper);
    }
}
