package com.demo.testng;

import org.testng.annotations.*;

public class BasicAnnotation {

    @Test
    public void testCase01(){
        System.out.println("testCase01");
    }

    @Test
    public void testCase02(){
        System.out.println("testCase02");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod");
    }

    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod");
    }
    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass");
    }
    @BeforeSuite
    public void beforeSuite(){
        System.out.println("beforeSuite");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("afterSuite");
    }


}
