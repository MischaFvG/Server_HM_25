package com.cat.dao;

import com.cat.model.Student;
import com.cat.model.StudentsList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MariaDBDao {
    private Connection connection;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MariaDBDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3307/student", "root", "1234");
    }

    private List<Student> allStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");
                String birthDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(resultSet.getTimestamp("birth_date"));
                studentList.add(new Student(id, age, name, birthDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public String getAllStudents() {
        String studentsJsonString;
        StudentsList studentsList = new StudentsList();
        studentsList.setStudentList(allStudents());
        studentsJsonString = gson.toJson(studentsList);
        return studentsJsonString;
    }

    public String getStudentById(int identification) {
        String studentString = "";
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM students WHERE id='%d'", identification);
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");
                String birthDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(resultSet.getTimestamp("birth_date"));
                Student student = new Student(id, age, name, birthDate);
                studentString = gson.toJson(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentString;
    }

    public String getStudentByName(String studentName) {
        String studentString;
        List<Student> studentList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM students WHERE name='%s'", studentName);
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");
                String birthDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(resultSet.getTimestamp("birth_date"));
                studentList.add(new Student(id, age, name, birthDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StudentsList studentsList = new StudentsList();
        studentsList.setStudentList(studentList);
        studentString = gson.toJson(studentsList);
        return studentString;
    }

    public void deleteStudentById(int identification) {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("DELETE FROM students WHERE id='%d'", identification);
            statement.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(int identification, int studentAge, String studentName, String studentBirthDate) {
        Timestamp birthDate = Timestamp.valueOf(studentBirthDate);
        try (Statement statement = connection.createStatement()) {
            String request = String.format("UPDATE students SET age='%d', name='%s', birth_date='%s' WHERE id='%d'",
                    studentAge,
                    studentName,
                    birthDate,
                    identification);
            statement.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStudent(int studentAge, String studentName, String studentBirthDate) {
        List<Student> studentList = allStudents();
        int identification = studentList.get(studentList.size() - 1).getId();
        Timestamp birthDate = Timestamp.valueOf(studentBirthDate);
        try (Statement statement = connection.createStatement()) {
            String request = String.format("INSERT INTO students VALUES('%d','%d','%s','%s')",
                    identification + 1,
                    studentAge,
                    studentName,
                    birthDate);
            statement.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAllStudentsByAge(String firstDate, Timestamp lastDate) {
        List<Student> studentList = new ArrayList<>();
        String studentsString;
        Timestamp firstBirthDate = Timestamp.valueOf(firstDate);
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM students WHERE birth_date>='%s' AND birth_date<='%s'",
                    firstBirthDate,
                    lastDate);
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");
                String birthDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(resultSet.getTimestamp("birth_date"));
                studentList.add(new Student(id, age, name, birthDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StudentsList studentsList = new StudentsList();
        studentsList.setStudentList(studentList);
        studentsString = gson.toJson(studentsList);
        return studentsString;
    }
}
