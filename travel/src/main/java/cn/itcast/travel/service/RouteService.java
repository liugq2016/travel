package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 线路
 */

public interface RouteService {
    /**
     * 线路类别分页查询
     * @param cid
     * @param currentpage
     * @param pagesize
     * @return
     */
    PageBean<Route> pageQuery(String cid, String currentpage, String pagesize,String rname);

    /**
     * 通过rid查找商品详情页的线路详情、图片信息、卖家信息
     * @param rid
     * @return
     */
    Route productDetailsPage(String rid);
}
