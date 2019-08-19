package com.course.utils;

import io.restassured.http.Cookies;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredUtil {

    /**
     * 使用restAssured框架
     * @return
     * @throws IOException
     */
    public static Response getResultRestAssured(Map<String, Object> jsonAsMap, String uri) throws IOException {
        //下边的代码为写完接口的测试代码

        System.out.println("start ---------先MOCO Server提交的数据-------------");
        System.out.println("data: "+jsonAsMap.toString());
        System.out.println("uri:  "+ uri);
        System.out.println("end   ---------先MOCO Server提交的数据-------------");


        Response response = given()
                .log().all()
                .contentType("application/json;charset=UTF-8")
                .body(jsonAsMap)
                .then()
                .log().all()
                .when().post(uri);


        System.out.println("接口返回数据");
        System.out.println("返回的状态码："+response.getStatusCode());
        System.out.println("返回的值： "+response.asString());
        System.out.println("接口返回数据");

        return response;
    }
    /**
     * 使用restAssured框架
     * @return
     * @throws IOException
     */
    public static Response getResultRestAssured(Map<String, Object> jsonAsMap, String uri, Cookies cookies) throws IOException {
        //下边的代码为写完接口的测试代码

        System.out.println("start #########先MOCO Server提交的数据#############");
        System.out.println("data: "+jsonAsMap.toString());
        System.out.println("uri:  "+ uri);
        System.out.println("end   #########先MOCO Server提交的数据#############");


        Response response = given()
                .log().all()
                .cookies(cookies)
                .contentType("application/json;charset=UTF-8")
                .body(jsonAsMap)
                .then()
                .log().all()
                .when().post(uri);


        System.out.println("接口返回数据");
        System.out.println("返回的状态码："+response.getStatusCode());
        System.out.println("返回的值： "+response.asString());
        System.out.println("接口返回数据");

        return response;
    }
}
