package homework.beanassembly.JavaConfig;

import homework.beanassembly.bean.Student;
import homework.beanassembly.bean.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Student getStudent(){
        Student student = new Student();
        student.setName("byJavaStudent");
        return student;
    }

    @Bean
    public Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setStudent(getStudent());
        teacher.setName("byJavaTeacher");
        return teacher;
    }
}
