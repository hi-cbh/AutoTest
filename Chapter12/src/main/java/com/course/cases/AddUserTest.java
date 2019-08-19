package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
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


    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口接口")
    public void addUser() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);

        Thread.sleep(2000);
        System.out.println("start ---------addUserCase------------");
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        System.out.println("end ---------addUserCase------------");


        //下边的代码为写完接口的测试代码
        String result = getResult(addUserCase);
        System.out.println(result);

        /**
         * 可以先讲
         */
        //查询用户看是否添加成功
        Thread.sleep(2000);
        User user = session.selectOne("addUser",addUserCase);
        System.out.println("---------addUser------------");

        System.out.println("addUser: "+addUserCase);
        System.out.println("addUser: "+user.toString());
        System.out.println("---------addUser------------");

        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(addUserCase.getExpected(),result);


    }


    private String getResult(AddUserCase addUserCase) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        /**
         *
         "userName":"chenone",
         "password":"123456",
         "sex":"0",
         "age":"35",
         "permission":"1",
         "isDelete":"0"
         */

        System.out.println("param String "+param.toString());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
//        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);


        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");


        System.out.println("-------getResult---------");
        System.out.println("getResult： "+response.getStatusLine());
        System.out.println("getResult： "+result);
        System.out.println("-------getResult---------");
        return result;
    }


    private String getResultRestAssured(AddUserCase addUserCase) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.addUserUrl);

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("userName", "chenone");
        jsonAsMap.put("password", "123456");

        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.store);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");


        System.out.println("----------------");
        System.out.println(result);
        System.out.println("----------------");
        return result;
    }





}
