package cz.cvut.fit.niam1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class HelloWebApplication {

    @PostMapping(value = "/transformation", consumes = "text/plain")
    String transformation(@RequestBody String data) {
        return new TransformService(data).getTransformedResult();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWebApplication.class, args);
    }

}