package icu.duanqihang.suse_it.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
public class Blog {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Integer likes;
    private String avatar;
    private String description;
    private boolean published;
    private boolean state;
    private Long userId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private String link;
    private Integer commentNumber;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    private boolean type;
    private Integer score;
    private Integer collect;
    private String flag;

    /**
     * 表映射忽略以下字段
     */
    private transient boolean collectjudge;

    private transient User user;

    private transient  List<Tag> tags = new ArrayList<>();

    private transient  String tagIds;

    private transient List<Comment> comments = new ArrayList<>();
 }
