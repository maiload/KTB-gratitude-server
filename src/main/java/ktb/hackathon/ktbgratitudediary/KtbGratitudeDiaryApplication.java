package ktb.hackathon.ktbgratitudediary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class KtbGratitudeDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KtbGratitudeDiaryApplication.class, args);
    }

}
