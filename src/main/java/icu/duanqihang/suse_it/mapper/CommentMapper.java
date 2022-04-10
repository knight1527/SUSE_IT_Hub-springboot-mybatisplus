package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.duanqihang.suse_it.pojo.Comment;
import icu.duanqihang.suse_it.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获得指定实体的所有评论信息
     * @param blogId
     * @return
     */
    List<Comment> getCommentByBlogId(@Param(value = "blogId")Long blogId);

    /**
     * 获得指定评论的作者详情
     */
    User getCommentUser(@Param(value = "commentId")Long commentId);
}
