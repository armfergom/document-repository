package com.esh.docrepository.dao;

import com.esh.docrepository.model.Student;

public interface StudentDao {

    /**
     * This method should persist a student into the database
     *
     * @param student
     */
    public void saveStudent(Student student);

    /**
     * Finds a student using the primarey key, the passport id
     * 
     * @param passportId
     * @return
     */
    public Student findStudent(String passportId);
}
