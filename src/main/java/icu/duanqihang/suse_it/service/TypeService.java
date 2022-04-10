package icu.duanqihang.suse_it.service;

import icu.duanqihang.suse_it.pojo.Type;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public interface TypeService {
    /**
     * 获得所有标签分类
     * @return
     */
    List<Type> getAllTypes();

    /**
     * 获取指定id分类
     */
    Type getType(Long id);
}
