package icu.duanqihang.suse_it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private Long id;
    private String name;

    private transient List<Tag> tags;
}
