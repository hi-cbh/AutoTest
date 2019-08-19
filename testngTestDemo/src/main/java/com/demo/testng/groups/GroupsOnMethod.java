package com.demo.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {

    @Test(groups="server")
    public void test1(){
        System.out.println("这是服务测试1");
    }
    @Test(groups="server")
    public void test2(){
        System.out.println("这是服务测试2");
    }

    @Test(groups="client")
    public void test3(){
        System.out.println("这是客户端测试1");
    }
    @Test(groups="client")
    public void test4(){
        System.out.println("这是客户端测试1");
    }


    @BeforeGroups("server")
    public void beforeGroupOnServer(){
        System.out.println("这是服务器前运行");
    }

    @AfterGroups("server")
    public void afterGroupOnServer(){
        System.out.println("这是服务器后运行");
    }

    @BeforeGroups("client")
    public void beforeGroupOnClient(){
        System.out.println("这是客户端前运行");
    }

    @AfterGroups("client")
    public void afterGroupOnClient(){
        System.out.println("这是客户端后运行");
    }
}
