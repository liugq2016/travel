package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 通过用户uid及旅游线路的cid查找用户收藏信息
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public boolean findOneByUidAndCid(int uid, int cid) {
        boolean flag = false;
        Favorite favorite = null;
        try {
            String sql = "select date from tab_favorite where uid = ? and rid = ? ";
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, cid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        if (favorite != null){
            flag = true;
        }
        return flag;
    }
    /**
     * 通过用户的真实姓名查找用户对应的uid
     * @param name
     * @return
     */
    @Override
    public int findUidByName(String name) {
        Integer uid = null;
        try {
            String sql = "select uid from tab_user where name = ?";
             uid = jdbcTemplate.queryForList(sql, Integer.class, name).get(0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return uid;
    }
    /**
     * 通过rid查找收藏数
     * @param rid
     * @return
     */
    @Override
    public int findCountById(int rid) {
        int count = 0;
        try {
            String sql = "select count(*) from tab_favorite where rid = ?";
            count = jdbcTemplate.queryForObject(sql, Integer.class, rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return count;
    }
}
