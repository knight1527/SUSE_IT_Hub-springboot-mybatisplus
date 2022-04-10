package icu.duanqihang.suse_it.service;

import icu.duanqihang.suse_it.pojo.Tag;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public interface TagService {
    void insertUser_Tag(Long userId, List<String> tagId);

    void deleteUserTags(Long userId);

    List<Tag> getTags(Long blogId);

    void insertBlog_Tag(Long blogId,List<String> tagId);

    void deleteBlogTags(Long blogId);

    int getTagsBlogCount(Long tagId);

    int getTagsReCount(Long tagId);

    List<Long> getTagEntityIds(Long tagId);
}
