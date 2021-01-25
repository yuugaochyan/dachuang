package com.industrialplatform.beta;

import com.industrialplatform.beta.service.DashBoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BetaApplicationTests {

    @Autowired
    DashBoardService dashBoardService;

    @Test
    void contextLoads() {
        dashBoardService.getItemByID(1001);
    }

}
