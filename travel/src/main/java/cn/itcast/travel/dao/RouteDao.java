package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     *  cid对应的分页查询的数据
     * @param id
     * @param start
     * @param size
     * @return
     */
    List<Route> pageQuery(int id, int start, int size);

    /**
     * cid对应的路线的 总数
     * @param id
     * @return
     */
    int queryCount(int id);

    /**
     * cid及rname对应的分页查询的数据
     * @param id
     * @param start
     * @param size
     * @param rname
     * @return
     */
    List<Route> pageQuery(int id, int start, int size, String rname);

    /**
     * cid及rname对应的记录总数
     * @param id
     * @param rname
     * @return
     */
    int queryCount(int id, String rname);

    /**
     * 通过rid查找商品详情页的线路详情、图片信息、卖家信息
     * @param int_rid
     * @return
     */
    Route productDetailsPage(int int_rid);
}
