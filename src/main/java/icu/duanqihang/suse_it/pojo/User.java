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
public class User {
    private Long id;
    private String email;
    private String password;
    private String avatar;
    private String major;
    private String description;
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private transient List<Tag> tags;

    private transient List<Blog> collectBlogs;

    private transient List<Blog> selfBlogs;

    private transient List<Blog> collectResources;

    private transient List<Blog> selfResources;

    private transient List<User> focusUsers;

    private transient String tagIds;
}
