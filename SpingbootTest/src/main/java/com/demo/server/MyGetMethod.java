package com.demo.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value="/",description = "这是我全部的get方法")
public class MyGetMethod {
    /**
     * 访问接口是，发送cookies
     * @param response
     * @return
     */
    @RequestMapping(value="/getCookies", method= RequestMethod.GET)
    @ApiOperation(value="通过这个方法可以获取到cookies")
    public String getCookies(HttpServletResponse response){
        //HttpServerletRequest 装请求信息类
        //HttpServerletResponse 装响应信息的类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "恭喜你获得cookies信息成功";
    }
    /**
     * 要求客户端携带cookies访问
     * 这需要一个携带cookies信息才能访问的get请求
     * @return
     */
    @RequestMapping(value="/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value="这需要一个携带cookies信息才能访问的get请求")
    public  String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(Objects.isNull(cookies)){
            return "你必须携带cookies信息";
        }
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("login")&&
                    cookie.getValue().equals("true")){
                return "这是一个需要携带cookies信息才能访问的get请求";
            }
        }
        return "你必须携带cookies信息来";
    }


    /**
     * 需要携带参数才能访问的get请求
     * /get/with/param?start=10&end=20
     */
    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value="需要携带参数才能访问的get请求")
    public Map<String, Integer> getList(@RequestParam Integer start,
                                        @RequestParam Integer end){
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋",400);
        myList.put("面",12);
        return myList;
    }

    /**
     * 需要携带参数才能访问的get请求
     * 第二种
     * /get/with/param/1/20
     */
    @RequestMapping(value = "/get/with/param2/{start}/{end}")
    public Map<String, Integer> getListParam(@PathVariable Integer start,
                                        @PathVariable Integer end){
        Map<String, Integer> myList = new HashMap<>();

        myList.put("鞋0",400);
        myList.put("面1",12);
        myList.put("面2",172);
        return myList;
    }
}
