package com.course.restassureddemo;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import com.course.utils.RestAssuredUtil;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AddUserTest {


    @Test(dependsOnGroups = "loginTrueRest",description = "添加用户接口接口")
    public void addUser() throws IOException, InterruptedException {

        /**
         * 读取数据库的信息，获取信息，
         * 传给RestAssured 返回 response中的数据，
         * 对比 接口返回值与期望值对比。
         */
        SqlSession session = DatabaseUtil.getSqlSession();


        System.out.println("start ---------读取addUserCase表------------");
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        Thread.sleep(2000);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
        System.out.println("end   ---------读取addUserCase表------------");



        //查询用户看是否添加成功
        Thread.sleep(2000);
        System.out.println("---------读取addUser表------------");
        User user = session.selectOne("addUser",addUserCase);
        System.out.println("addUser: "+addUserCase);
        System.out.println("addUser: "+user.toString());
        System.out.println("---------读取addUser表------------");



        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userName", addUserCase.getUserName());
        jsonAsMap.put("password", addUserCase.getPassword());
        jsonAsMap.put("sex", addUserCase.getSex());
        jsonAsMap.put("age", addUserCase.getAge());
        jsonAsMap.put("permission", addUserCase.getPermission());
        jsonAsMap.put("isDelete", addUserCase.getIsDelete());


        //下边的代码为写完接口的测试代码
        Response response = RestAssuredUtil.
                getResultRestAssured(jsonAsMap,TestConfig.addUserUrl,TestConfig.cookiess);

        System.out.println("response.asString: "+response.asString());


//        处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(addUserCase.getExpected(),response.asString());

    }

}
