package com.hqhop;

import com.hqhop.modules.system.service.UserService;
import com.hqhop.modules.system.service.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMain {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {

       UserDTO userDTO = userService.findByName("admin");

       System.out.println(userDTO);










    }
}
