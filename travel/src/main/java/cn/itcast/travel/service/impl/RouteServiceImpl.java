package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    RouteDao routeDao = new RouteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(String cid, String currentpage, String pagesize,String rname) {
        int id = 0;//cid类别号
        int current = 0;//当前页码数
        int size = 0;//每页显示的记录数
        PageBean<Route> pageBean = new PageBean<Route>();
//        如果参数存在则转换类型为int , 如果参数不存在 则令初始值分别为：1,1,5
        if (cid != null && cid.length() > 0 && !"null".equalsIgnoreCase(cid)){
            id = Integer.parseInt(cid);
        }else {
            id =5;
        }
        if (currentpage != null && currentpage.length() > 0 ){
            current = Integer.parseInt(currentpage);
        }else {
            current =1;
        }
        if (pagesize != null && pagesize.length() > 0 ){
            size = Integer.parseInt(pagesize);
        }else {
            size =5;
        }
//        分页查询
        int start = (current-1)*size;//分页查询开始位置
        List<Route> list = null;//查询到的分页数据
        int count = 0;//总记录数
        int countpage = 0;//总页数
        if (rname != null && rname.length() > 0 && !"".equalsIgnoreCase(rname) && !"null".equalsIgnoreCase(rname)){
//            rname的值不为空
             list = routeDao.pageQuery(id,start,size,rname);
            //        查询总记录数
            count = routeDao.queryCount(id,rname);
            countpage = count%size == 0 ? (count/size):(count/size+1);
        }else {
             list = routeDao.pageQuery(id,start,size);
//        查询总记录数
            count = routeDao.queryCount(id);
            countpage =  count%size == 0 ?(count/size):(count/size+1);
        }

//        封装数据
        pageBean.setCurrentpage(current);
        pageBean.setList(list);
        pageBean.setPagesize(size);
        pageBean.setTotalcount(count);
        pageBean.setTotalpage(countpage);
        return pageBean;
    }

    /**
     * 通过rid查找商品详情页的线路详情、图片信息、卖家信息
     * @param rid
     * @return
     */
    @Override
    public Route productDetailsPage(String rid) {
        Route route = null;
        int int_rid = 0;

        if (rid != null && !"".equalsIgnoreCase(rid) && !"null".equalsIgnoreCase(rid)){
            int_rid = Integer.parseInt(rid);
            route= routeDao.productDetailsPage(int_rid);
        }
        return route;

    }
}
