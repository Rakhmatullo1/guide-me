package uz.guideme.userservice.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfiguration {

    @Value("${mongo.url}")
    private String url;

    @Value("${mongo.db-name}")
    private String dbName;

    @Bean
    public MongoClient configureMongoClient() {
        ConnectionString connectionString = new ConnectionString(url);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(configureMongoClient(), dbName);
    }
}
