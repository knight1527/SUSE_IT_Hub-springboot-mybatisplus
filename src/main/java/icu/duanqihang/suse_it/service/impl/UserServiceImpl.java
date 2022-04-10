package icu.duanqihang.suse_it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import icu.duanqihang.suse_it.mapper.UserMapper;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.UserService;
import icu.duanqihang.suse_it.utils.MD5Utils;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String email, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email)
                .eq("password", MD5Utils.code(password));
        return userMapper.selectOne(wrapper);
    }

    @Override
    public List<User> getAllUsers(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id","avatar","nickname").eq("email",email);
        return userMapper.selectList(wrapper);
    }

    @Override
    public void addUser(User user) {
        int insert = userMapper.insert(user);
        System.out.println("++++++++++++");
        System.out.println("新增用户"+insert);
        System.out.println("++++++++++++");
    }

    @Override
    public User getUserDetails(Long id) {
        return userMapper.getUserDetail(id);
    }

    @Transactional(rollbackFor=Exception.class)
    @Override
    public void updateUserById(User user) {
        userMapper.updateById(user);
    }

    /**
     * 删除关注关系
     *
     * @param id
     * @param targetUserId
     */
    @Override
    public void deleteUserFocus(Long id, Long targetUserId) {
        userMapper.deleteFocus(id,targetUserId);
    }

    /**
     * 插入关注关系
     *
     * @param id
     * @param targetUserId
     */
    @Override
    public void insertFocus(Long id, Long targetUserId) {
        userMapper.insertFocus(id,targetUserId);
    }

    /**
     * 查询关注关系
     *
     * @param id
     */
    @Override
    public List<Long> getFocus(Long id) {
        return userMapper.getFocus(id);
    }
}
