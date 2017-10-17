package com.lanou.domain;

/**
 * Created by dllo on 17/10/17.
 */
public class Student {
    private int id;  // 主键id
    private String sname; //姓名
    private String gender; // 性别
    private int age; // 年龄

    // 无参
    public Student() {
    }

    // 全参
    public Student(int id, String sname, String gender, int age) {
        this.id = id;
        this.sname = sname;
        this.gender = gender;
        this.age = age;
    }

    // 没有主键id的
    public Student(String sname, String gender, int age) {
        this.sname = sname;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
