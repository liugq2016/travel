package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.dao.userDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private userDao userDao = new UserDaoImpl();
    /**
     * 注册用户
     * @param user
     * @return Boolean
     * */
    @Override
    public Boolean regist(User user) {
        //根据flag来决定是否保存当前用户信息
        boolean flag = false;
        boolean sava_flag = false;
        //根据用户名查询用户
        User finduser = userDao.findUserByUserName(user.getUsername());
        if (finduser == null){
            flag = true;
        }
        if (flag){
            //保存用户数据
            //1.设置唯一激活码
            user.setCode(UuidUtil.getUuid());
            //2. 设置激活状态
            user.setStatus("N");
             sava_flag = userDao.save(user);
             //3. 发送邮件
           String content = "<a href = 'http://localhost/travel/user/active?code=" +user.getCode()+"'>家里蹲大学[点击激活]</a>";
            MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        }
        return flag && sava_flag;
    }

    @Override
    public boolean active(String code) {
        //根据code查询用户
        Boolean flag = false;
        User user = userDao.findUserByCode(code);
        if (user != null){
            //修改用户的激活状态
            user.setStatus("Y");
           flag = userDao.updateStatus(user);
        }
        return flag;
    }
    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        User user1 = userDao.findUserByAccountAndPassword(user);
        return user1;
    }
}
