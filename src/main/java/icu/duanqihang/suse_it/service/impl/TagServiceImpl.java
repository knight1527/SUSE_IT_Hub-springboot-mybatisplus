package icu.duanqihang.suse_it.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import icu.duanqihang.suse_it.mapper.TagMapper;
import icu.duanqihang.suse_it.pojo.Tag;
import icu.duanqihang.suse_it.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void insertUser_Tag(Long userId, List<String> tagId) {
        for(String i:tagId){
            Long relTagId = Long.parseLong(i);
            tagMapper.insertUser_Tag(userId,relTagId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserTags(Long userId) {
        tagMapper.deleteUserTags(userId);
    }

    @Override
    public List<Tag> getTags(Long blogId) {
        return tagMapper.getTags(blogId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBlog_Tag(Long blogId, List<String> tagId) {
        for(String i:tagId){
            tagMapper.insertBlog_Tag(blogId,Long.parseLong(i));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBlogTags(Long blogId) {
        tagMapper.deleteBlogTags(blogId);
    }

    @Override
    public int getTagsBlogCount(Long tagId) {
        return tagMapper.getBlogCount(tagId);
    }

    @Override
    public int getTagsReCount(Long tagId) {
        return tagMapper.getResourceCount(tagId);
    }

    @Override
    public List<Long> getTagEntityIds(Long tagId) {
        return tagMapper.getTagEntityIds(tagId);
    }
}
