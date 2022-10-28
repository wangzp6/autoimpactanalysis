package com.example.autoimpactanalysis.test;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
/**
 * @project_name: autoimpactanalysis
 * @ClassName: mongodbTest
 * @Author: kevin
 * @Date: 2022/10/28 16:34
 * @Description: TODO
 * @Version: V1.0
 */
public class mongodbTest {

    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb://127.0.0.1:27017/";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("developdatabase");
            MongoCollection<Document> collection = database.getCollection("developdatabase");
            Document doc = collection.find(eq("title", "Back to the Future")).first();
            System.out.println(doc.toJson());
        }
    }
}

