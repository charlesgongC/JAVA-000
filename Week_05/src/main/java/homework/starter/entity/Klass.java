package homework.starter.entity;

import lombok.Data;

import java.util.List;

@Data
public class Klass {

    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }


    public void dong(){
        System.out.println(this.getStudents());
    }

}
