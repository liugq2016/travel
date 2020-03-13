package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface userDao {

    /**
     * 根据用户名查找用户是否存在
     * @param username
     * @return
     * */
    User findUserByUserName( String  username);
    /**
     * 存储用户信息
     * @param user
     * @return
     * */
    Boolean save( User user);

    /**
     * 根据激活码查询用户
     * @return
     */
    User findUserByCode(String code);

    /**
     * 更新用户激活状态
     * @param user 用户信息
     * @return
     */
    Boolean updateStatus(User user);

    /**
     * 通过账号密码登陆
     * @param user
     * @return
     */
    User findUserByAccountAndPassword(User user);
}
