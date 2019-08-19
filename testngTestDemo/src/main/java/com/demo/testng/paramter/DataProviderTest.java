package com.demo.testng.paramter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    @Test(dataProvider = "data")
    public void testDataProvider(String name, int age){
        System.out.println("name = "+name +"; age= " + age);
    }

    @DataProvider(name="data")
    public Object [][] providerData(){
        Object [][] o = new Object[][]{
                {"chen",12},
                {"bo",32}
        };
        return o;
    }

    @Test(dataProvider = "methodData")
    public void test1(String name, int age){
        System.out.println("test1 name = "+name +"; age= " + age);
    }

    @Test(dataProvider = "methodData")
    public void test2(String name, int age){
        System.out.println("test2 name = "+name +"; age= " + age);
    }

    @DataProvider(name="methodData")
    public Object [][] methodData(Method method){
        Object [][] result = null;

        if(method.getName().equals("test1")){
            result = new Object[][]{
                    {"chen",32},
                    {"bo",54}
            };
        }

        else if(method.getName().equals("test2")){
            result = new Object[][]{
                    {"hight",32},
                    {"niubi",54}
            };
        }

        return result;
    }

}
