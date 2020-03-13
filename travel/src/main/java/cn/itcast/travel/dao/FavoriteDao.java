package cn.itcast.travel.dao;

public interface FavoriteDao {
    /**
     * 通过用户uid及旅游线路的cid查找用户收藏信息
     * @param uid
     * @param cid
     * @return
     */
    boolean findOneByUidAndCid(int uid,int cid);

    /**
     * 通过用户的真实姓名查找用户对应的uid
     * @param name
     * @return
     */
    int findUidByName(String name);
    /**
     * 通过rid查找收藏数
     * @param rid
     * @return
     */
    int findCountById(int rid);
}
