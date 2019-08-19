package com.demo.testng;

import org.testng.annotations.Test;

public class ExpectedException {

    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExpectedException(){
        System.out.println("这是一个异常测试 fail");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExpectedSuccess(){
        System.out.println("这是一个异常测试 success");
        throw new RuntimeException();

    }

}
