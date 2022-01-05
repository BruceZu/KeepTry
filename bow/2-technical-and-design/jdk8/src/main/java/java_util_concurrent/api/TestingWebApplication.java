package java_util_concurrent.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("java_util_concurrent.api")
public class TestingWebApplication {
  public static void main(String[] args) {
    SpringApplication.run(TestingWebApplication.class, args);
  }
}
