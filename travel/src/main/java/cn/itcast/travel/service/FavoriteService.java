package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 通过用户真实姓名及旅游线路id查找收藏信息
     * @param name
     * @param id
     * @return
     */
    boolean findOneByNameAndId(String name,String id);

    /**
     * 通过rid查找收藏数
     * @param rid
     * @return
     */
    int findCountById(int rid);
}
