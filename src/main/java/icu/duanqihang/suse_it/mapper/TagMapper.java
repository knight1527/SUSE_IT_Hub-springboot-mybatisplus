package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> getDefaultTag();

    void insertUser_Tag(@Param(value = "userId")Long userId,@Param(value = "tagId")Long tagId);

    void deleteUserTags(@Param(value = "userId")Long userId);

    void deleteBlogTags(@Param(value = "blogId")Long blogId);

    /**
     * 获得指定blog实体标签集合
     * @param blogId
     * @return
     */
    List<Tag> getTags(@Param(value = "blogId")Long blogId);

    void insertBlog_Tag(@Param(value = "blogId")Long blogId, @Param(value = "tagId")Long tagId);

    /**
     * 获得指定标签的实体数目
     */
    int getBlogCount(@Param(value = "tagId")Long tagId);

    int getResourceCount(@Param(value = "reId")Long reId);

    /**
     * 获得指定标签的所有实体id
     */
    List<Long> getTagEntityIds(@Param(value = "tagId")Long tagId);
}
