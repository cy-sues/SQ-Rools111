package com.cy.sqrools;

import com.cy.sqrools.drools.Drools;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SqRoolsApplicationTests {

    @Test
    public void test1(){
        Drools drool=new Drools();
        List<String> read = drool.read("Y:\\\\text\\\\2022-04-12-13-36-34-408-0757f506-1ea6-4e00-b34e-653097ee0f3c.nt");
        System.out.println(read);
    }

}
