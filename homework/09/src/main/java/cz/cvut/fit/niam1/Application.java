package cz.cvut.fit.niam1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;

@RestController
@EnableAutoConfiguration
public class Application {
    private int index;

    List<String> urls;

    List<Integer> unhelthyUrlsIndexes;

    private final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() {
        this.index = 0;
        this.urls = new ArrayList<String>() {
            {
                add("http://147.32.233.18:8888/MI-MDW-LastMinute1/list");
                add("http://147.32.233.18:8888/MI-MDW-LastMinute2/list");
                add("http://147.32.233.18:8888/MI-MDW-LastMinute3/list");
            }
        };
        this.unhelthyUrlsIndexes = new ArrayList<>();

        // Timer for removing
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                checkUnhealthyUrls();
            }
        }, 0, 1000);
    }

    @Autowired
    RestTemplate restTemplate;

    void checkUnhealthyUrls() {
        List<Integer> urlIndexesToMakeHealthy = new ArrayList<>();

        for (Integer i : unhelthyUrlsIndexes) {
            try {
                ResponseEntity response = this.restTemplate.getForEntity(new URI(this.urls.get(i)), String.class);
                logger.info("status code for {} is {}", this.urls.get(i), response.getStatusCode().value());
                if (response.getStatusCode().value() == 200) {
                    urlIndexesToMakeHealthy.add(i);
                } else {
                    logger.info("Still not healthy: {}", this.urls.get(i));
                }
            } catch (Exception ignored) {
                logger.info("Still not healthy: {}", this.urls.get(i));
            }
        }

        for (Integer i : urlIndexesToMakeHealthy) {
            unhelthyUrlsIndexes.remove(i);
            logger.info("Url {} is now healthy", this.urls.get(i));
        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping(value = "/test")
    public ResponseEntity test(HttpServletRequest request) throws Exception {
        logger.info("----");

        // copy headers
        HttpHeaders headers = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(head -> headers.add(head, request.getHeader(head)));
        // create request entity
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        // HTTP

        ResponseEntity responseEntity;

        while (true) {
            try {
                if (this.unhelthyUrlsIndexes.contains(index)) {
                    continue;
                }

                responseEntity = restTemplate.exchange(new URI(urls.get(index)), HttpMethod.GET, requestEntity, String.class);
                int statusCode = responseEntity.getStatusCode().value();

                // OK
                if (statusCode == 200) {
                    logger.info("Using url {}", urls.get(index));
                    return responseEntity;
                }
                // BAD
                else if (statusCode == 500) {
                    this.unhelthyUrlsIndexes.add(index);
                    logger.error("Status code 500 for url {}", urls.get(index));
                }
            }
            // Also BAD
            catch (Exception e) {
                this.unhelthyUrlsIndexes.add(index);
                logger.error("Exception for url {}", urls.get(index));
            } finally {
                index = (index + 1) % urls.size();
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
