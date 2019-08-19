package com.course.demo;

import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestDemo {

    @Test
    public void TestDemo() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);

        User user = session.selectOne("addUser",addUserCase);
        System.out.println("---------addUser------------");

        System.out.println("addUser: "+addUserCase);
        System.out.println("addUser: "+user);
        System.out.println("---------addUser------------");
    }
}
