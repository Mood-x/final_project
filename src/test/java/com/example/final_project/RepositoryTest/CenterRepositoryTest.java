package com.example.final_project.RepositoryTest;

import com.example.final_project.Model.Center;
import com.example.final_project.Model.User;
import com.example.final_project.Repository.AuthRepository;
import com.example.final_project.Repository.CenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CenterRepositoryTest {

    @Autowired
    CenterRepository centerRepository;

    @Autowired
    AuthRepository authRepository;

    User user;

    Center center1, center2, center3;

    List<Center> centers;

    Center center;

    @BeforeEach
    void setUp(){
        user=new User(1,"abdulaziz","abdulaziz@gmail.com",
                "Asdf12345","0554666691","abdulaziz",null
                ,false,null,null,null
                ,null,null,null);
//        center1 new Center(1,"jeddah","lisence",null,"Sport",null
//        ,null,null,null,null,null);
//        center2 = new Center(2,"jeddah","lisence",null,"Sport",null
//                ,null,null,null,null,null);
//        center3 = new Center(3,"Alnamas","trainimg")

    }
}
