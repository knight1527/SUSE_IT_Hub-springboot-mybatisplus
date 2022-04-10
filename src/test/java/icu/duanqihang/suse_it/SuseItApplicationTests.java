package icu.duanqihang.suse_it;

import icu.duanqihang.suse_it.pojo.StudyUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SuseItApplicationTests {
    @Test
    public void sampleTest(){
        String[] str = "1".split(",");
        for(String i : str){
            System.out.println(i);
        }
    }
}
