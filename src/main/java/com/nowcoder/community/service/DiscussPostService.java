package com.nowcoder.community.service;


import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    //一定不要跨层调用  必须service调dao
    //在查询过程中会封装成DiscussPost，但是会显示userId，不需要显示userId
    //两种做法：1.关联查询，在查询该表的时候同时查询用户表
    //2.单独的查一下user再与DiscussPost合并一起封装，在后面与redis缓存数据的时候方便，性能更高
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }


}
