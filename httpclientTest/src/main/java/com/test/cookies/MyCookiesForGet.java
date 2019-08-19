package com.test.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieIdentityComparator;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    CookieStore store;
    private ResourceBundle bundle;

    @BeforeTest
    public void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINESE);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        System.out.println("");
        String result;
        String uri=bundle.getString("getCookies.uri");
        String testUrl = this.url+uri;
        HttpGet get = new HttpGet(testUrl);

        DefaultHttpClient client = new DefaultHttpClient();
//        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");

        System.out.println("testGetCookies: "+result);

        store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList){
            String name =cookie.getName();
            String value = cookie.getValue();
            System.out.println("访问/getcookies接口成功，cookie name = "+name+", cookie value = "+value);
        }

    }


    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        System.out.println("");
        String uri = bundle.getString("test.get.with.cookies");

        String testUrl = this.url+uri;

        HttpGet get =new HttpGet(testUrl);

        DefaultHttpClient client = new DefaultHttpClient();

        client.setCookieStore(this.store);

        HttpResponse response = client.execute(get);

        int statusCode = response.getStatusLine().getStatusCode();

        System.out.println("statusCode "+ statusCode);

        if(statusCode == 200){
            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println("testGetWithCookies  " + result);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostWithCookies() throws IOException {
        System.out.println("");
        String uri = bundle.getString("test.post.with.cookies");
        //拼接最终的测试地址
        String testUrl = this.url + uri;

        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();

        //声明一个方法，这个方法就是post方法
        HttpPost post = new HttpPost(testUrl);

        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","chen");
        param.put("age","20");

        client.setCookieStore(this.store);
        //设置请求头信息 设置header
        post.setHeader("content-type","application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("test: "+result);
        //处理结果，就是判断返回结果是否符合预期
        //将返回的响应结果字符串转化成为json对象
//        JSONObject resultJson = new JSONObject(result);

//        JSONObject resultJon = new JSONObject(result);
//
//        String status = (String)resultJon.get("status");
//        System.out.println("status:" + status);
//        Assert.assertEquals("200",status);


    }



}
