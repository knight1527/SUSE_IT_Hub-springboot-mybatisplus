package icu.duanqihang.suse_it.mapper;

import icu.duanqihang.suse_it.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface TableMapper {
    List<String> queryBlogTags(String tableName, String blogId);
}
