package com.course.restassureddemo;

import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.course.utils.RestAssuredUtil.getResultRestAssured;

public class GetUserInfoListTest {



    @Test(dependsOnGroups="loginTrueRest",description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();

        System.out.println("start -----getUserListCase--------");
        GetUserListCase getUserListCase = session.selectOne("getUserListCase",1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
        System.out.println(" end -----getUserListCase--------");

        Thread.sleep(2000);
        System.out.println(" start ----- getUserList --------");
        System.out.println("查找表名 - getUserListCase.getExpected()："+ getUserListCase.getExpected());
        System.out.println("查找数据："+getUserListCase);

        List<User> userList = session.selectList(getUserListCase.getExpected(),getUserListCase);
//        for(User u : userList){
//            System.out.println("数据库返回的数据list:"+u.toString());
//        }
        System.out.println(" end  ----- getUserList --------");

        //-----------------------
        Map<String, Object> jsonAsMap = new HashMap<String, Object>();
        jsonAsMap.put("sex", getUserListCase.getSex());

        //下边为写完接口的代码
        Response resultJson = getResultRestAssured(jsonAsMap,TestConfig.getUserListUrl,TestConfig.cookiess);
        //-----------------------

        //转为string类型
        String jsonStr = resultJson.asString();
        System.out.println("接口数据转换为String类型"+jsonStr);
        System.out.println("----------");


        JSONArray userListJson = new JSONArray(userList);
        JSONArray jsonStrArray = new JSONArray(jsonStr);


        Assert.assertEquals(userListJson.length()+"",
                JsonPath.read(jsonStr,"$.length()")+"");


        for(int i = 0;i<userListJson.length();i++){
            JSONObject expect = (JSONObject) jsonStrArray.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Assert.assertEquals(expect.toString(), actual.toString());
        }
        System.out.println(" end  ----- 获取数据对比 --------");

    }



}
