package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.userDao;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements userDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
/**
 * 根据用户名查找用户是否存在
 * @param username
 * @return
 * */

    @Override
    public User findUserByUserName(String username) {
        User user = null;
        try {
//        定义sql语句
            String sql = "select * from tab_user where username = ?";
//        执行sql语句
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
//        返回user对象
        } catch (DataAccessException e) {
            user = null;
        }
        return user;
    }
    /**
     * 存储用户信息
     * @param user
     * @return
     * */

    @Override
    public Boolean save(User user) {
        boolean flag = false;
//        定义sql语句
        String sql = "insert into " +
                "tab_user(username,password,name,birthday,sex,telephone,email,status,code) " +
                "values(?,?,?,?,?,?,?,?,?) ";
        int update = jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        if (update>0){
            flag = !flag;
        }
        return flag;
    }

    /**
     * 根据激活码查找用户
     * @param code 激活码
     * @return
     */
    @Override
    public User findUserByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            user = null;
        }
        return user;
    }
    /**
     * 更新用户激活状态
     * @param user 用户信息
     * @return
     */
    @Override
    public Boolean updateStatus(User user) {
        Boolean flag = false;
        String sql = "update tab_user set status = ? where uid = ?";
        int update = jdbcTemplate.update(sql, user.getStatus(), user.getUid());
        if (update != 0) {
            flag = true;
        }
        return flag;
    }
    /**
     * 通过账号密码登陆
     * @param user
     * @return
     */
    @Override
    public User findUserByAccountAndPassword(User user) {
        User user1 = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
             user1 = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user1 ;
    }
}
