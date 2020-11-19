package homework.starter.service;

import homework.starter.entity.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    @Autowired
    private School school;

    public void printInfo() {
        school.ding();
        school.getClass1().dong();
    }
}
