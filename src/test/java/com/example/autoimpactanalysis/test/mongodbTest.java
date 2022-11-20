package com.example.autoimpactanalysis.test;

import com.example.autoimpactanalysis.AutoimpactanalysisApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: mongodbTest
 * @Author: kevin
 * @Date: 2022/10/28 16:34
 * @Description: TODO
 * @Version: V1.0
 */
public class mongodbTest  extends AutoimpactanalysisApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;
    /**
     * 创建集合
     */
    @Test
    public void testCreateCollection(){
        boolean emp = mongoTemplate.collectionExists("emp");
        if(emp){
            mongoTemplate.dropCollection("emp");
        }
        mongoTemplate.createCollection("emp");
    }
}

