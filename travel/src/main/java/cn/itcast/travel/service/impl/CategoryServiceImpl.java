package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
  private   CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
//        从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        List<Category> categorylist = null;
        Category categorybean = null;
//        判断查询的集合是否为空
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        if (categorys != null && categorys.size() != 0){
            //        如果不为空，直接返回
//            System.out.println("从redis中查询");
            categorylist = new ArrayList<Category>();
//            将set集合转化为list集合
            for (Tuple tuple:categorys) {
                categorybean = new Category();
                categorybean.setCname(tuple.getElement());
                categorybean.setCid((int)tuple.getScore());
                categorylist.add(categorybean);
            }
        }else {
            //        如果为空，需要从数据库查询，再将数据存入redis
//            System.out.println("从数据库查询");
            categorylist = categoryDao.findAll();
            for (Category category:categorylist) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }


        return categorylist;
    }
}
