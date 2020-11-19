package homework.starter;


import homework.starter.entity.Klass;
import homework.starter.entity.School;
import homework.starter.entity.Student;
import homework.starter.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Starter {

    @Autowired
    private Student student111;

    @Autowired
    private Student student222;


    @Bean
    public Student student111() {
        Student student = new Student();
        student.setId(100);
        student.setName("SS111");
        return student;
    }

    @Bean
    public Student student222() {
        Student student = new Student();
        student.setId(123);
        student.setName("SS222");
        return student;
    }

    @Bean
    public Klass class1() {
        Klass klass = new Klass();
        List<Student> list = new ArrayList<>();
        list.add(student111);
        list.add(student222);
        klass.setStudents(list);
        return klass;
    }

    @Bean
    public School school() {
        return new School();
    }

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
