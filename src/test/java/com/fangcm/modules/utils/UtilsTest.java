package com.fangcm.modules.utils;

import com.fangcm.common.utils.JWTUtil;
import com.fangcm.common.utils.UserUtil;
import org.junit.Test;


/**
 * Created by FangCM on 2018/5/29.
 */

public class UtilsTest {

    @Test
    public void token() throws Exception {
        String mobile = "";
        String password = "test1234";
        String jwt = JWTUtil.sign("13701014141", password);
        System.out.println("============================================");
        System.out.println("token=" + jwt);
        System.out.println("password=" + UserUtil.encrypt(password));
        System.out.println("============================================");
    }

}
