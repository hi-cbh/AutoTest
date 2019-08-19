package com.demo.testng.groups;

import org.testng.annotations.Test;

@Test(groups="teacher")
public class GroupOnClass3 {

    public void teacher1(){
        System.out.println("GroupOnClass3 teacher1");
    }

    public void teacher2(){
        System.out.println("GroupOnClass3 teacher2");
    }

}
