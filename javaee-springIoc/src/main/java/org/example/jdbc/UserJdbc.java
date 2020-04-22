package org.example.jdbc;


import org.example.model.Student;
import org.example.model.Teacher;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbc {

    //获取所有学生信息
    public static List<Student> selectAllStudent() throws SQLException {

        Connection connection = DatabasePool.getHikarDataSource().getConnection();
        String sqlString = "SELECT * FROM student";

        List<Student> list = new ArrayList<>();
        try {
            connection.setAutoCommit(false);//不自动commit
            PreparedStatement pst = connection.prepareStatement(sqlString);
            ResultSet resultSet = pst.executeQuery();
            // 获取执行结果
            while (resultSet.next()) {
                Student s = new Student();
                s.setStudentId(resultSet.getInt("student_id"));
                s.setStudentName(resultSet.getString("student_name"));
                list.add(s);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return list;
    }

    //获取所有老师信息
    public static List<Teacher> selectAllTeacher() throws SQLException {

        Connection connection = DatabasePool.getHikarDataSource().getConnection();
        String sqlString = "SELECT * FROM teacher";

        List<Teacher> list = new ArrayList<>();
        try {
            connection.setAutoCommit(false);//不自动commit
            PreparedStatement pst = connection.prepareStatement(sqlString);
            ResultSet resultSet = pst.executeQuery();
            // 获取执行结果
            while (resultSet.next()) {
                Teacher s = new Teacher();
                s.setTeacherId(resultSet.getInt("teacher_id"));
                s.setPassword(resultSet.getString("password"));
                list.add(s);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return list;
    }

    //增加新的学生
    public  boolean addStudent(Student s) throws ClassNotFoundException, SQLException {
        Connection connection = DatabasePool.getHikarDataSource().getConnection();
        String sqlString = "insert into student(student_id,student_name,password)values(?,?,?)";

        //判断该学生是否已经存在
        boolean isStudent = true;

        List<Student> slist = UserJdbc.selectAllStudent();

        for (Student stud : slist) {
            if (stud.getStudentId() == s.getStudentId()) {
                isStudent = false;
                break;
            }
        }
        //如果存在返回FALSE
        if (isStudent == false) {
            return false;
        }

        //建立连接
        try  {
            connection.setAutoCommit(false);//不自动commit
            PreparedStatement pst = connection.prepareStatement(sqlString);
            pst.setLong(1, s.getStudentId());
            pst.setString(2, s.getStudentName());
            pst.setString(3, s.getPassword());
            pst.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return true;
    }

    //增加新的老师
    public  boolean addTeacher(Teacher t) throws ClassNotFoundException, SQLException {
        Connection connection = DatabasePool.getHikarDataSource().getConnection();
        String sqlString = "insert into teacher(teacher_id,password,teacher_name)values(?,?,?)";

        //判断该学生是否已经存在
        boolean isTeacher = true;

        List<Teacher> slist = UserJdbc.selectAllTeacher();

        for (Teacher stud : slist) {
            if (stud.getTeacherId() == t.getTeacherId()) {
                isTeacher = false;
                break;
            }
        }
        //如果存在返回FALSE
        if (isTeacher == false) {
            return false;
        }

        //建立连接
        try  {
            connection.setAutoCommit(false);//不自动commit
            PreparedStatement pst = connection.prepareStatement(sqlString);
            pst.setLong(1, t.getTeacherId());
            pst.setString(2, t.getPassword());
            pst.setString(3, t.getTeacherName());
            pst.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        }
        return true;
    }
}
