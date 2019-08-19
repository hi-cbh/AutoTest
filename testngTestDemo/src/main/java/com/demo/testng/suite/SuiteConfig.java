package com.demo.testng.suite;

import org.testng.annotations.*;

public class SuiteConfig {

    @BeforeSuite
    public void beforeSuit(){
        System.out.println("SuiteConfig beforeSuit");
    }

    @AfterSuite
    public void afterSuit(){
        System.out.println("SuiteConfig afterSuit");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("SuiteConfig beforeTest");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("SuiteConfig afterTest");
    }

    @Test
    public void test(){
        System.out.println("SuiteConfig test");
    }
}
