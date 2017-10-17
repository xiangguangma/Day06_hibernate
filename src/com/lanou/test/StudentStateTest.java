package com.lanou.test;

import com.lanou.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by dllo on 17/10/17.
 */
public class StudentStateTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){
        // 初始化配置文件
        Configuration configuration = new Configuration();
        configuration.configure();  // 加载src目录下的hibernate.cfg.xml文件

        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy(){
        transaction.commit(); // 事务提交
        session.close(); // 关闭本次连接
    }

    /**
     *  将一个临时状态的对象转化为一个持久化对象
     *  new : 临时状态
     *  save: 临时状态转化为持久化状态
     */
    @Test
    public void saveState(){
        // 临时状态
        Student student = new Student("王五", "男", 28);

        session.save(student);  // 持久化状态
    }


    /**
     *  从数据库中通过get, load 等方法获取的对象就持久化状态的对象
     */
    @Test
    public void getState(){
        // 从数据库中查询主键 id 为2 的实体类对象, 此时返回的student对象就是持久化状态的对象
        Student student = session.get(Student.class, 2);

        System.out.println("修改前: " + student);

        student.setSname("王二麻子");
    }


    /**
     *  从游离状态恢复成持久化状态
     *  close, evict, clear: 将一个持久化状态的对象变成一个游离状态的对象
     *  update: 将一个游离状态的对象恢复成持久化状态的对象
     */
    @Test
    public void updateState(){
        // 获得一个持久化状态的对象
        Student student = session.get(Student.class, 2);

        // 将持久化对象变成游离状态的对象
        session.evict(student);

        // 游离状态的对象修改不会提交到数据库中
        student.setSname("二狗子");

        // 将游离状态的对象恢复成持久化的对象
        session.update(student);


    }

}
