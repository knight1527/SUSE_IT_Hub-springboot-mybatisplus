package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.duanqihang.suse_it.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
public interface UserMapper extends BaseMapper<User> {
        User getUserDetail(@Param(value = "id") Long id);

        /**
         * 取消关注关系
         */
        void deleteFocus(@Param(value = "id") Long id,@Param(value = "targetUserId")Long targetUserId);

        /**
         * 建立关注关系
         * @param id
         * @param targetUserId
         */
        void insertFocus(@Param(value = "id") Long id,@Param(value = "targetUserId")Long targetUserId);

        /**
         * 查询关注关系
         * @return
         */
        List<Long> getFocus(@Param(value = "id")Long id);
}
