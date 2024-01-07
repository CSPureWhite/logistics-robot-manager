package com.example.logistics_robot_manager.utils;


import org.mindrot.jbcrypt.BCrypt;

/**
 * 使用BCrypt加密算法对密码进行加密和校验
 */
public class PassWordUtil {

    /**
     * 加密密码
     */
    public static String encrypt(String pw){
        // 生成盐
        String salt= BCrypt.gensalt();
        return BCrypt.hashpw(pw,salt);
    }

    /**
     * 校验密码
     * @param target // 数据库中的目标密码
     * @param pw // 待校验的输入密码
     * @return 密码一致返回true
     */
    public static boolean check(String target,String pw){
        String hashed=encrypt(pw);
        return BCrypt.checkpw(target, hashed);
    }
}
