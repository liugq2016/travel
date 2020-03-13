package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询category表的所有数据
     * @return
     */
    public List<Category> findAll();
 }
