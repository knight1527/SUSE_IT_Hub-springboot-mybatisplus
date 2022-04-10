package icu.duanqihang.suse_it.utils;

import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.pojo.Blog;
import icu.duanqihang.suse_it.pojo.Tag;
import icu.duanqihang.suse_it.pojo.User;
import icu.duanqihang.suse_it.service.BlogService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class OtherUtils {
    public static String getTagIds(List<Tag> list){

        if(list.isEmpty()){
            return "";
        }
        StringBuilder str = new StringBuilder("");
        for(Tag i : list){
            str.append(String.valueOf(i.getId()));
            str.append(",");
        }
        return str.substring(0,str.length()-1);
    }

    /**
     * 封装每个实体是否杯登录用户收藏
     * @param pageInfo
     * @param user
     * @param blogService
     * @return
     */
    public static PageInfo<Blog> judgeResourceCollectTool(PageInfo<Blog> pageInfo, User user, BlogService blogService){
        List<Blog> userCollect = blogService.getUserResourceCollectList(user.getId());
        List<Long> idList = userCollect.stream().map(Blog::getId).collect(Collectors.toList());
        pageInfo.getList().forEach(t ->
                    t.setCollectjudge(idList.contains(t.getId()))
                );
        return pageInfo;
    }
    /**
     *
     * 获取id集合并集
     */
    public static List<Long> getUnion(List<List<Long>> list){
        if(list==null||list.isEmpty()){
            return null;
        }
        List<Long> tep = list.get(0);
        list.forEach(
                tep::retainAll
        );
        return tep;
    }
    /**
     * 拼接Url
     */
    public static String getUrl(String str){
        StringBuilder tep = new StringBuilder(str);
        if(tep.indexOf("http://")==-1&&tep.indexOf("https://")==-1){
            tep=new StringBuilder("http://").append(tep);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return tep.toString();
    }

}
