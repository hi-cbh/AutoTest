package com.demo.testng.groups;


import org.testng.annotations.Test;

@Test(groups="stu")
public class GroupOnClass1 {

    public void stu1(){
        System.out.println("GroupOnClass1 stu1");
    }


}
