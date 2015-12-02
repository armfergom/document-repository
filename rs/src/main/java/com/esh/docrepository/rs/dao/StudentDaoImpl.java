package com.esh.docrepository.rs.dao;

import com.esh.docrepository.rs.model.Student;

public class StudentDaoImpl extends AbstractDao<Student>implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }

    @Override
    public void saveStudent(Student student) {
        persist(student);
    }

    @Override
    public Student findStudent(String passportId) {
        return find(passportId);
    }

}