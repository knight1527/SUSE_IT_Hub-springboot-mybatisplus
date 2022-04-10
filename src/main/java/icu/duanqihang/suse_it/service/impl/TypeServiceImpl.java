package icu.duanqihang.suse_it.service.impl;

import icu.duanqihang.suse_it.mapper.TypeMapper;
import icu.duanqihang.suse_it.pojo.Type;
import icu.duanqihang.suse_it.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    /**
     * 获得所有标签分类
     * @return
     */
    @Override
    public List<Type> getAllTypes() {
        return typeMapper.getAllType();
    }

    /**
     * 获取指定id分类
     *
     * @param id
     */
    @Override
    public Type getType(Long id) {
        return typeMapper.getTypeById(id);
    }
}
