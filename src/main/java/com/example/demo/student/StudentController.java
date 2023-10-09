package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")

//Contains resources for studentAPI
public class StudentController {

    private final StudentService studentService;

    //Instantiates StudentService to perform business logic on API calls
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //Reads all students from DBMS
    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    //Adds a new student to DBMS
    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }

    //Deletes a student from DBMS by taking studentID from path
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    //Updates a student from DBMS by taking studentID from path
    @PutMapping(path= "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
                studentService.updateStudent(studentId, name, email);
    }
}
