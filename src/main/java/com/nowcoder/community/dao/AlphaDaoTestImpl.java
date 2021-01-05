package com.nowcoder.community.dao;

import org.springframework.stereotype.Repository;

@Repository
public class AlphaDaoTestImpl implements AlphaDao {
    @Override
    public String select() {
        return "Test";
    }
}
