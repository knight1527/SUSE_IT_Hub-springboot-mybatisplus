package icu.duanqihang.suse_it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import icu.duanqihang.suse_it.pojo.Type;
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
public interface TypeMapper extends BaseMapper<Type> {
    List<Type> getAllType();

    Type getTypeById(@Param(value = "id")Long id);
}
