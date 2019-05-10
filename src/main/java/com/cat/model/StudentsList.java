package com.cat.model;

import java.util.ArrayList;
import java.util.List;

public class StudentsList {
    private List<Student> studentList = new ArrayList<>();

    public StudentsList() {
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "StudentsList{" +
                "studentList=" + studentList +
                '}';
    }
}
