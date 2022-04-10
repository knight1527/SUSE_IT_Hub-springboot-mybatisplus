package icu.duanqihang.suse_it.service;

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
public interface UserService {
    User checkUser(String email, String password);

    List<User> getAllUsers(String email);

    void addUser(User user);

    User getUserDetails(Long id);

    void updateUserById(User user);

    /**
     * 删除关注关系
     */
    void deleteUserFocus(Long id,Long targetUserId);
    /**
     * 插入关注关系
     */
    void insertFocus(Long id,Long targetUserId);
    /**
     * 查询关注关系
     */
    List<Long> getFocus(Long id);
}
