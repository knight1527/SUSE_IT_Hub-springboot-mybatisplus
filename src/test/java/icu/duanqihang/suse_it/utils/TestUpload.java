package icu.duanqihang.suse_it.utils;

import org.junit.jupiter.api.Test;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class TestUpload {
    @Test
    public void testUpload(){
        System.out.println(UploadUtils.getImgDirFile().getAbsolutePath());
    }
}
