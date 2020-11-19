package homework.starter.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Data
public class School {
    @Autowired
    Klass class1;

    @Autowired
    @Qualifier("student100")
    Student student100;
    public void ding(){
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);    }
}
