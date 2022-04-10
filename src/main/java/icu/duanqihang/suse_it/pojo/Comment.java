package icu.duanqihang.suse_it.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private String content;
    private Long parentCommentId;
    private Integer likes;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Long userId;
    private Long blogId;

    private transient User user;

    private transient List<Comment> replyComments;
}
