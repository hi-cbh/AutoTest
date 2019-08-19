package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
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
import java.util.List;

public class GetUserInfoListTest {



    @Test(dependsOnGroups="loginTrue",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);

        System.out.println("start -----向数据库获取数据库--------");
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        System.out.println(" end -----向数据库获取数据库--------");




        //下边为写完接口的代码
        JSONArray resultJson = getJsonResult(getUserListCase);
        /**
         * 可以
         */
        Thread.sleep(2000);
        System.out.println(" start ----- 获取数据对比 --------");
        System.out.println("查找表名 - getUserListCase.getExpected()："+ getUserListCase.getExpected());
        System.out.println("查找数据："+getUserListCase);

        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
        for(User u : userList){
            System.out.println("数据库返回的数据list:"+u.toString());
        }
        System.out.println("转为Json格式");
        JSONArray userListJson = new JSONArray(userList);

        System.out.println("数据库读取的数据长度：" + userListJson.length());
        System.out.println("接口 读取的数据长度：" + resultJson.length());
        Assert.assertEquals(userListJson.length(),resultJson.length());
        for(int i = 0;i<resultJson.length();i++){
            JSONObject expect = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }
        System.out.println(" end  ----- 获取数据对比 --------");

    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
        param.put("age",getUserListCase.getAge());
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
        System.out.println("start -----向moco Server 请求数据--------");
        System.out.println("param: " + param);
        System.out.println("TestConfig.getUserListUrl: " + TestConfig.getUserListUrl);

        System.out.println("end  -----向moco Server 请求数据--------");
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        JSONArray jsonArray = new JSONArray(result);
        System.out.println("start -----moco Server 返回的数据 response --------");
        System.out.println("response:"+response.getStatusLine());
        System.out.println("result:"+result);
        System.out.println("jsonArray:"+jsonArray);
        System.out.println("end -----moco Server 返回的数据 response --------");
        return jsonArray;

    }

}
