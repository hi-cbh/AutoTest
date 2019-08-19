package com.demo.testng;

import org.testng.annotations.Test;

public class DependTest {

    @Test
    public void test1() throws Exception {
        System.out.println("test1");
        throw new Exception();
    }

    @Test(dependsOnMethods = "test1")
    public void test2(){
        //依赖的方法不执行
        System.out.println("test2");
    }

    @Test(dependsOnMethods = "test1")
    public void test3(){
        System.out.println("test3");
    }

}
