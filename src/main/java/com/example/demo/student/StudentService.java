package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//Contains business logic for service layer
@Service
public class StudentService {

    // Instantiates studentRepository using Autowired
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private final StudentRepository studentRepository;

    //Uses a findAll() method from JPA to get all students from repository (no need for query?)
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    //Posts new student to DBMS with save(student) method from JPA
    public void addNewStudent(@NotNull Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()){
            throw new IllegalStateException("Email Taken");
        }
        studentRepository.save(student);
    }

    //Deletes a student from DBMS using deleteById(studentId) method from JPA
    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if (!exists) {
            throw new IllegalStateException("student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    //Puts a new email for student using @Transactional
    //@Transactional updates the DBMS in managed state with changes to 'student' due to @Entity tag
    //@Transactional methods must complete for a transaction of data to be completed and saved to DB
    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));
        if (name != null &&
                name.length() > 0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null &&
                email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {
            Optional <Student> studentOptional = studentRepository
                    .findStudentByEmail(email);
            if (studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}