package com.tester.extend.demo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestMethodDemo {

    @Test
    public void testcase01(){
        System.out.println("testcase01");
        Assert.assertEquals(1,2);

    }

    @Test
    public void testcase02(){
        System.out.println("testcase01");
        Assert.assertEquals("12321","43434");

    }
    @Test
    public void testcase03(){
        System.out.println("testcase01");
        Assert.assertEquals(1,2);

    }
    @Test
    public void testcase04(){
        System.out.println("testcase01");
        Assert.assertEquals(1,1);

    }

    public void logDemo(){
        Reporter.log("这是自定义日志");
        throw new RuntimeException("这是运行时错误日志");
    }


}
