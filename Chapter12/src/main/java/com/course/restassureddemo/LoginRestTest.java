package com.course.restassureddemo;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.course.utils.RestAssuredUtil.getResultRestAssured;

public class LoginRestTest {


    @BeforeTest(groups = "loginTrueRest",description = "测试准备工作,使用RestAssured")
    public void beforeTest(){
        TestConfig.defaultHttpClient = new DefaultHttpClient();
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.deleteUserurl = ConfigFile.getUrl(InterfaceName.DELETEUSER);


        System.out.println("---------初始化连接--------------");
        System.out.println(TestConfig.getUserInfoUrl);
        System.out.println(TestConfig.getUserListUrl);
        System.out.println(TestConfig.loginUrl);
        System.out.println(TestConfig.updateUserInfoUrl);
        System.out.println(TestConfig.addUserUrl);
        System.out.println(TestConfig.deleteUserurl);
        System.out.println("----------初始化连接-------------");
    }



    @Test(groups = "loginTrueRest",description = "用户成功登陆接口")
    public void loginTrueRest() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();

        System.out.println("------读取loginCase----------");
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        System.out.println("-------读取loginCase---------");


        /**
         * 提交数据
         */
        System.out.println("start ---------向接口提交的数据---------");

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userName", loginCase.getUserName());
        jsonAsMap.put("password", loginCase.getPassword());
        System.out.println("end   ---------向接口提交的数据---------");


        Response response = getResultRestAssured(jsonAsMap,TestConfig.loginUrl);

        // cookiess赋值
        TestConfig.cookiess =  response.detailedCookies();
        Assert.assertEquals(response.getStatusCode(),200);

        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(),response.asString());

    }





    @Test(description = "用户登陆失败接口")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();

        System.out.println("start -------loginCase 读取数据库---------");
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        System.out.println("end   -------loginCase 读取数据库---------");




        System.out.println("end   -------获取接口数据---------");
        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userName", loginCase.getUserName());
        jsonAsMap.put("password", loginCase.getPassword());
        Response response = getResultRestAssured(jsonAsMap,TestConfig.loginUrl);

        System.out.println("end   -------获取接口数据---------");

        System.out.println("response.getStatusCode(): "+response.getStatusCode());

        String result;
        if(response.getStatusCode() != 200){
            result = "false";
        }
        else{
            result = "true";
        }

        Assert.assertEquals(loginCase.getExpected(),result);

    }




}
