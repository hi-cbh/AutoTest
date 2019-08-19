package com.demo.server;

import com.demo.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value="/", description = "这是我全部的POST请求")
@RequestMapping("/v1")
public class MyPostMethod {


    private static Cookie cookie;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    @ApiOperation(value="登录接口，成功获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response,
                        @RequestParam(value="userName", required = true) String userName
                        ,@RequestParam(value="passWord",required = true) String passWord){

        if(userName.equals("chen") && passWord.equals("123456")){
            cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功";
        }

        return "用户名或密码错误";

    }

    @RequestMapping(value="/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                            @RequestBody User u){

        Cookie[] cookies = request.getCookies();

        for(Cookie c : cookies){
            if(c.getName().equals("login")
                    && c.getValue().equals("true")
                    && u.getUserName().equals("chen")
                    && u.getPassWord().equals("123456")){
                User user = new User();
                user.setName("lici");
                user.setAge("12");
                user.setSex("man");
                return user.toString();
            }
        }
        return "参数不合法";
    }
}
