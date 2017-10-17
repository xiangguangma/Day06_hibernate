package com.lanou.test;

import com.lanou.domain.Student;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.annotations.QueryBinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 针对Student的单元测试
 * Created by dllo on 17/10/17.
 */
public class StudentTest {
    /*
        session的工厂类对象, 是线程安全的, 一个数据库对应一个session工厂类对象
        通常在项目启动时初始化该对象, 用 configuration对象创建
     */
    private SessionFactory sessionFactory;

    //  真正执行CRUD操作的数据库连接对象, 代表了一次数据库连接, 是非线程安全的
    private Session session;

    /*
        事务对象, 与 JDBC 中的实物类相视,只不过hibernate中的事务是不自动提交的, 而JDBC中的事务
        是自动提交的,hibernate中的事务需要手动提交
     */
    private Transaction transaction;

    /**
     * 初始化操作
     * Before 注解: 在Test注解之前执行的方法, 通常该方法中做一些初始化的操作
     */
    @Before
    public void init(){
        System.out.println("*****init*****");

        // 创建hibernate配置对象
        Configuration configuration = new Configuration();
        // 加载指定配置文件
        configuration.configure("hibernate.cfg.xml");

        // 通过配置对象创建一个sessionFactory对象, 创建结束之后 configuration就与sessionFactory失去关联了
        sessionFactory = configuration.buildSessionFactory();

        // 打开一个数据库连接
        session = sessionFactory.openSession();

        // 开启事务
        transaction = session.beginTransaction();
    }

    /**
     *  销毁操作
     *  After注解: 在Test注解之后执行的方法, 通常该方法中做一些释放,关闭的操作
     */
    @After
    public void destroy(){
        System.out.println("*****destroy*****");

        // 提交本次事务
        transaction.commit();

        // 关闭本次连接
        session.close();
    }

    /**
     * 数据库插入操作
     */
    @Test
    public void  insert(){
        System.out.println("*****insert*****");

        Student student = new Student("张三", "男", 23);

        // 将实体类对象保存到数据库中
        session.save(student);
    }

    @Test
    public void query(){
        // 获得一个查询对象, 等同于 select * from Student
        Query query = session.createQuery("from Student where sname=?");

        // 条件语句中的占位符参数对应
        query.setString(0, "张三"); // 指定第一个问号所对应的值

        // 返回查询的结果集
        List<Student> students = query.list();

        // 遍历结果集
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void delete(){
        // 从数据库中查询要删除的对象
        Query query = session.createQuery("from Student where sname = ?");
        query.setString(0,"张三");  // 条件语句中的参数设置

        List<Student> students= query.list();
        if (students.size() > 0){
            // 如果能查询出张三这个用户, 则进行删除第一个叫张三的用户
            session.delete(students.get(0));
        }

    }

    @Test
    public void update(){
        Query query = session.createQuery("from Student where sname=?");
        query.setString(0, "张三");

        List<Student> students= query.list();
        if (students.size() > 0){
            // 取第一个叫张三的学生
            Student student = students.get(0);

            // 修改该学生的基础信息
            student.setSname("李四");
            student.setAge(20);
            student.setGender("女");

            // 更新学生信息
            session.update(student);

        }
    }

    @Test
    public void createCriteria(){
        //获得要进行查询的Student对应的Criteria接口对象. 它是比 HQL 更高级的查询方式
        Criteria criteria = session.createCriteria(Student.class);

        // 设置最多查询条数
        criteria.setMaxResults(2);

        // 获取查询的结果集
        List<Student> students = criteria.list();

        for (Student student : students) {
            System.out.println(student);
        }

    }

    /**
     *  多结果要加条件, 否则报错
     */
    @Test
    public void querySingle(){
        Query query = session.createQuery("from Student where id=2");

        // 返回单个结果
        Student student = (Student) query.uniqueResult();
        System.out.println(student);
    }
}
