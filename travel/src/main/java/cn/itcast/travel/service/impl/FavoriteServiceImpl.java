package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
   private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 通过用户真实姓名及旅游线路id查找收藏信息
     * @param name
     * @param id
     * @return
     */
    @Override
    public boolean findOneByNameAndId(String name, String id) {
        boolean flag = false;
        int cid = Integer.parseInt(id);
        int uid = 0;
        uid =  favoriteDao.findUidByName(name);
        flag = favoriteDao.findOneByUidAndCid(uid, cid);
        return flag;
    }
    /**
     * 通过rid查找收藏数
     * @param rid
     * @return
     */
    @Override
    public int findCountById(int rid) {
        int count = 0;
        count = favoriteDao.findCountById(rid);
        return count;
    }
}
