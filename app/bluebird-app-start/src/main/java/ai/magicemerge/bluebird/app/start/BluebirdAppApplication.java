package ai.magicemerge.bluebird.app.start;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@EnableFeignClients(basePackages = "ai.magicemerge.bluebird.app.integration")
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "ai.magicemerge")
public class BluebirdAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BluebirdAppApplication.class, args);
        log.info("app start");
    }


}
