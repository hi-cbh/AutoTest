package com.course.restassureddemo;

import com.course.config.TestConfig;
import com.course.model.UpdateUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.course.utils.RestAssuredUtil.getResultRestAssured;

public class UpdateUserInfoTest {

    @Test(dependsOnGroups = "loginTrueRest",description = "更改用户信息")
    public void updateUserInfo() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",1);


        System.out.println("[start] updateUserInfo---读取mysql数据");
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.updateUserInfoUrl);
        System.out.println("[end] updateUserInfo---读取mysql数据");

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userId", updateUserInfoCase.getUserId());
        jsonAsMap.put("userName", updateUserInfoCase.getUserName());


        //下边为写完接口的代码
        Response response =  getResultRestAssured(jsonAsMap,TestConfig.updateUserInfoUrl,TestConfig.cookiess);


        //获取更新后的结果
        Thread.sleep(2000);
        System.out.println("______debug________");
        System.out.println(updateUserInfoCase.getExpected());
        System.out.println(updateUserInfoCase);
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println("user: "+user.toString());
        System.out.println("response"+response.asString());
        System.out.println("______debug________");


        Assert.assertNotNull(user);
        Assert.assertNotNull(response.asString());

    }

    @Test(dependsOnGroups = "loginTrueRest",description = "删除用户")
    public void deleteUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        UpdateUserInfoCase updateUserInfoCase = session.selectOne("updateUserInfoCase",2);
        System.out.println(updateUserInfoCase.toString());
        System.out.println(TestConfig.deleteUserurl);

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userId", updateUserInfoCase.getUserId());
        //下边为写完接口的代码
        Response response = getResultRestAssured(jsonAsMap,TestConfig.deleteUserurl,TestConfig.cookiess);

        /**
         * 下边这两行跟着测试的课讲
         */
        Thread.sleep(2000);
        User user = session.selectOne(updateUserInfoCase.getExpected(),updateUserInfoCase);
        System.out.println(user.toString());


        Assert.assertNotNull(user);
        Assert.assertNotNull(response.asString());
    }


}
