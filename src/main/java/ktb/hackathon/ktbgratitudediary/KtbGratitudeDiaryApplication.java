package ktb.hackathon.ktbgratitudediary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@ConfigurationPropertiesScan
@SpringBootApplication
public class KtbGratitudeDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KtbGratitudeDiaryApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
