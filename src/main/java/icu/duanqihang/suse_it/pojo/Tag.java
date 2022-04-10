package icu.duanqihang.suse_it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Tag {
    private Long id;
    private String tagName;
    private Long typeId;

    private transient int blogCount;

    private transient int resourceCount;
}
