package com.demo.testng;

import org.testng.annotations.Test;

public class IgnoreTest {

    @Test
    public void ignore1(){
        System.out.println("IgnoreTest ignore1");
    }
    @Test(enabled=false)
    public void ignore2(){
        System.out.println("IgnoreTest ignore2");
    }
    @Test(enabled=true)
    public void ignore3(){
        System.out.println("IgnoreTest ignore2");
    }
}
