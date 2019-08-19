package com.course.cases;

import com.course.model.InterfaceName;
import com.course.config.TestConfig;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class LoginTest {


    @BeforeTest(groups = "loginTrue",description = "测试准备工作,获取HttpClient对象")
    public void beforeTest(){
        TestConfig.defaultHttpClient = new DefaultHttpClient();
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);


        System.out.println("-----------------------");
        System.out.println(TestConfig.getUserInfoUrl);
        System.out.println(TestConfig.getUserListUrl);
        System.out.println(TestConfig.loginUrl);
        System.out.println(TestConfig.updateUserInfoUrl);
        System.out.println(TestConfig.addUserUrl);
        System.out.println("-----------------------");
    }




    @Test(groups = "loginTrue",description = "用户成功登陆接口")
    public void loginTrue() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);

        System.out.println("----------------");
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        System.out.println("----------------");

        //下边的代码为写完接口的测试代码
        String result = getResult(loginCase);
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(),result);




    }

//    @Test(groups = "loginTrue",description = "用户成功登陆接口")
    public void loginTrueRest() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",1);

        System.out.println("----------------");
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        System.out.println("----------------");


        Response response = getResultRestAssured(loginCase);

        Assert.assertEquals(response.getStatusCode(),200);


        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(),response.asString());

        System.out.println("-------loginTrueRest---------");
        System.out.println(response.detailedCookies());
        System.out.println("-------loginTrueRest---------");

    }





    @Test(description = "用户登陆失败接口")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase",2);
        System.out.println("start -------loginFalse 读取数据库---------");
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);
        System.out.println("end   -------loginFalse 读取数据库---------");


        //下边的代码为写完接口的测试代码
        String result = getResult(loginCase);



        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(),result);

    }




    private String getResult(LoginCase loginCase) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        System.out.println("start------Moco Server提交数据----------");
        System.out.println("param : " + param);
        System.out.println("uri   : " + TestConfig.loginUrl);
        System.out.println("method: POST" + TestConfig.loginUrl);
        System.out.println("end  ------Moco Server提交数据----------");
        String result;
        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("start------Moco Server返回数据----------");
        System.out.println(response.getStatusLine());

        if(!response.getStatusLine().toString().contains("200")){

            result = "false";
            System.out.println("返回错误");
            System.out.println("end  ------Moco Server返回数据----------");
        }
        else{

            System.out.println(result);
            System.out.println("end  ------Moco Server返回数据----------");
            TestConfig.store = TestConfig.defaultHttpClient.getCookieStore();
            System.out.println("-------cookise---------");
            System.out.println(TestConfig.store);
            System.out.println("-------cookise---------");
        }

        return result;
    }

    /**
     * 使用restAssured框架
     * @param loginCase
     * @return
     * @throws IOException
     */
    private Response getResultRestAssured(LoginCase loginCase) throws IOException {
        //下边的代码为写完接口的测试代码

        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("nameName", "chenone");
        jsonAsMap.put("password", "123456");

        Response response = given()
                .contentType("application/json;charset=UTF-8")
                .body(jsonAsMap)
                .then()
                .when().post(TestConfig.loginUrl);

        return response;
    }


}
