package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public class RouteDaoImpl implements RouteDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 分页查询的数据
     * @param id
     * @param start
     * @param size
     * @return
     */
    @Override
    public List<Route> pageQuery(int id, int start, int size) {
        List<Route> list = null;
        try {
            String sql = " select * from tab_route where cid = ? limit ? , ?";
            list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), id, start, size);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * id对应的路线的 总数
     * @param id
     * @return
     */
    @Override
    public int queryCount(int id) {
        Integer count = -1;
        try {
            String sql = "select count(*) from tab_route where cid = ?";
            count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }
    /**
     * id及rname对应的分页查询的数据
     * @param id
     * @param start
     * @param size
     * @param rname
     * @return
     */
    @Override
    public List<Route> pageQuery(int id, int start, int size, String rname) {
        List<Route> list = null;
        try {
            String sql = "select * from tab_route where cid = ? and rname like ? limit ?,?";
            rname = "%"+rname+"%";
            list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), id, rname, start, size);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * cid及rname对应的记录总数
     * @param id
     * @param rname
     * @return
     */
    @Override
    public int queryCount(int id, String rname) {
        String sql = "select count(*) from tab_route where cid = ? and rname like ? ";
        int count = 0;
        try {
            rname = "%"+rname+"%";
            count = jdbcTemplate.queryForObject(sql,Integer.class,id,rname);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 通过rid查找某一商品详情页的线路详情、图片信息、卖家信息
     * @param int_rid
     * @return
     */
    @Override
    public Route productDetailsPage(int int_rid) {
//        通过rid查询商品详情页的商品信息
        String findroutebyridsql = "select * from tab_route where rid = ?";
//        通过rid查询某一商品的图片信息
        String findimagebyridsql = "select * from tab_route_img where rid = ? ";
//        通rid查询某一商品的卖家信息
        String findsellerinfomationbyidsql = "select * from tab_seller where sid = ?";
//        通过rid查询收藏数
        FavoriteService favoriteService = new FavoriteServiceImpl();
        int count = favoriteService.findCountById(int_rid);
//        创建封装类
        Route route = null;
//        查询查询商品详情页的信息
        try {
            route = jdbcTemplate.queryForObject(findroutebyridsql,new BeanPropertyRowMapper<Route>(Route.class),int_rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (route != null){
//            查询的商品存在
            List<RouteImg> imgs = jdbcTemplate.query(findimagebyridsql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), int_rid);
            route.setRouteImgList(imgs);
            Seller seller = jdbcTemplate.queryForObject(findsellerinfomationbyidsql, new BeanPropertyRowMapper<Seller>(Seller.class), route.getSid());
            route.setSeller(seller);
            route.setCount(count);
        }
//如果不存在则直接返回route = null
        return route;
    }
}
