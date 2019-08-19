package com.course.restassureddemo;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class GetUserInfoTest {


    @Test(dependsOnGroups="loginTrueRest",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();

        System.out.println("-----------getUserInfoCase-------------");
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        System.out.println("-----------getUserInfoCase-------------");


        Thread.sleep(3000);
        System.out.println("---------getUserInfo------------");
        System.out.println(getUserInfoCase.getExpected());
        System.out.println(getUserInfoCase);
        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        System.out.println("自己查库获取用户信息:"+user.toString());
        System.out.println("---------getUserInfo------------");



        Thread.sleep(2000);
        System.out.println("———————————resultJson—————————————");
        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userId", getUserInfoCase.getUserId() + "");
        //下边为写完接口的代码

        given()
            .log().all()
            .cookies(TestConfig.cookiess)
            .contentType("application/json;charset=UTF-8")
            .body(jsonAsMap)
        .then()
            .log().all()
            .body(
                    "age", equalTo(user.getAge()),
                    "sex",equalTo(user.getSex()),
                    "userName",equalTo(user.getUserName()),
                    "password",equalTo(user.getPassword()),
                    "permission",equalTo(user.getPermission()),
                    "isDelete",equalTo(user.getIsDelete())
            )
        .when().post(TestConfig.getUserInfoUrl);

    }

}
