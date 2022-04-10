package icu.duanqihang.suse_it.utils;

import com.github.pagehelper.PageInfo;
import icu.duanqihang.suse_it.pojo.Blog;

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
public class PageUtils {
    public static PageInfo<Blog> getPageInfo(List<Blog> blogs,int pageNum,int pageSize){
        PageInfo<Blog> blogList = new PageInfo<>(blogs);

        int tep = blogs.size()%pageSize;
        int total = tep==0?blogs.size()/pageSize:blogs.size()/pageSize+1;
        blogList.setPages(total);

        blogList.setPageNum(pageNum);

        blogList.setPageSize(pageSize);

        blogList.setLastPage(total);

        int prePages = pageNum-1==0?pageNum:pageNum-1;
        blogList.setPrePage(prePages);

        int nextPages = Math.min(pageNum + 1, total);
        blogList.setNextPage(nextPages);

        long beg = Math.max(pageNum - 1, 0);
        List<Blog> list = blogs.parallelStream()
                                .skip(beg*pageSize)
                                .limit(pageSize)
                                .collect(Collectors.toList());
        blogList.setList(list);

        return blogList;
    }
}
