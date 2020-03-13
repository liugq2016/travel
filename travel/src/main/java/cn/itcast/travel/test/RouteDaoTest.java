package cn.itcast.travel.test;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import org.junit.Test;

public class RouteDaoTest {
    @Test
    public void queryCount(){
        RouteDao routeDao = new RouteDaoImpl();
//        System.out.println(routeDao.queryCount(1));
//        System.out.println(routeDao.pageQuery(5,0,5));
//        System.out.println(routeDao.queryCount(5,"长沙"));
//        System.out.println(routeDao.pageQuery(5,0,5,"长沙"));
        System.out.println(routeDao.productDetailsPage(1));
    }
    @Test
    public void favorite(){
        FavoriteDao favoriteDao = new FavoriteDaoImpl();
//        System.out.println(favoriteDao.findUidByName("刘金龙"));
        System.out.println(favoriteDao.findOneByUidAndCid(15,7));
    }
}
