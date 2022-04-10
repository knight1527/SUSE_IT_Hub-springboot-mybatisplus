package icu.duanqihang.suse_it.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
@Configuration
public class PageHelperConfig {
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        // 这样 startpage 就是 page 而不是 start 了
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        // 不会跳转到 超过上限 或者 <0 的页数
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
