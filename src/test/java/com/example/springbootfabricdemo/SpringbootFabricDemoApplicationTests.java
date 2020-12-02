package com.example.springbootfabricdemo;

import com.example.springbootfabricdemo.dto.fabric.req.FinLocQuery;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@SpringBootTest
class SpringbootFabricDemoApplicationTests {

    @Test
    void contextLoads() throws Exception {
        Map<String,String> emm = BeanUtils.describe(new FinLocQuery("asd",new Date(),new Date()));
        String[] emm2 = emm.values().toArray(new String[0]);
        System.out.println(emm2);
    }

}
