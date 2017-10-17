package com.lanou.test;

import com.lanou.dao.StudentDao;
import com.lanou.dao.impl.StudentDaoImpl;
import com.lanou.domain.Student;
import org.hibernate.Session;
import org.junit.Test;

/**
 * Created by dllo on 17/10/17.
 */
public class StudentDaoTest {

    @Test
    public void save(){
        // 创建一个Dao层对象
        StudentDao dao = new StudentDaoImpl();

        Student student = new Student("阿彬","男",22);

        System.out.println("保存前: "+ student);

        student = dao.save(student); //  保存对象

        System.out.println("保存后: "+student);
    }

    @Test
    public void login(){
        StudentDao dao = new StudentDaoImpl();
        boolean result = dao.login("阿彬666", "123");
        System.out.println("登录结果: "+ result);

    }

}
