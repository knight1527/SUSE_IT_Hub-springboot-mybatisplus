package icu.duanqihang.suse_it.service;

import icu.duanqihang.suse_it.pojo.Comment;
import icu.duanqihang.suse_it.pojo.User;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public interface CommentService {
    /**
     * 获取指定实体的全部评论
     */
    List<Comment> getCommentByBlogId(Long blogId);
    /**
     * 获得指定评论的作者信息
     */
    User getCommentUser(Long commentId);
    /**
     * 新增评论
     */
    void insertComment(Comment comment);
    /**
     * 获得评论简单信息
     */
    Comment getCommentDetail(Long commentId);
    /**
     * 更新评论信息
     */
    void updataComment(Comment comment);
    /**
     * 获得指定实体的评论数目
     */
    int countEntityComment(Long blogId);
}
