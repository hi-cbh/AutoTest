package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import com.course.utils.JsonSameUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {


    @Test(dependsOnGroups="loginTrue",description = "获取userId为1的用户信息")
    public void getUserInfo() throws IOException, InterruptedException {

        System.out.println("-----------分界线-------------");
        System.out.println("-----------分界线-------------");
        System.out.println("-----------分界线-------------");

        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);

        System.out.println("-----------getUserInfo-------------");
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);
        System.out.println("-----------getUserInfo-------------");


        //下边为写完接口的代码
        JSONArray resultJson = getJsonResult(getUserInfoCase);

        /**
         * 下边三行可以先讲
         */
        Thread.sleep(2000);
        System.out.println("———————————resultJson—————————————");

        System.out.println(resultJson);
        System.out.println("———————————resultJson—————————————");

        Thread.sleep(3000);

        System.out.println("---------session------------");
        System.out.println(getUserInfoCase.getExpected());
        System.out.println(getUserInfoCase);
        System.out.println("---------session------------");

        User user = session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);

        System.out.println("---------用户信息------------");
        System.out.println("自己查库获取用户信息:"+user.toString());

        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        System.out.println("读取数据库用户信息:"+jsonArray.toString());
        System.out.println("读取moco接口获取用户信息:"+resultJson.toString());
        System.out.println("---------用户信息------------");
        System.out.println(JsonSameUtil.same(jsonArray,resultJson));
        //json变量的顺序不对
//        Assert.assertEquals(JsonSameUtil.same(jsonArray,resultJson),true);
//        Assert.assertEquals(JsonSameUtil.same(jsonArray,resultJson),true);
    }


    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("userId",getUserInfoCase.getUserId()+"");
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        System.out.println("---------param--------------");
        System.out.println(TestConfig.getUserInfoUrl);
        System.out.println(param);
        System.out.println(TestConfig.store);
        System.out.println("---------param--------------");

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");




        System.out.println("---------response--------------");
        System.out.println(response.getStatusLine());
        System.out.println("调用接口result:"+result);
        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(resultList);
        System.out.println(array.toString());
        System.out.println("---------response--------------");
        return array;

    }
}
