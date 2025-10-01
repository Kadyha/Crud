package com.example.crudapp.soap;

import com.example.crudapp.model.Student;
import java.util.List;

public class GetAllStudentsResponse {
    private List<Student> students;
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
