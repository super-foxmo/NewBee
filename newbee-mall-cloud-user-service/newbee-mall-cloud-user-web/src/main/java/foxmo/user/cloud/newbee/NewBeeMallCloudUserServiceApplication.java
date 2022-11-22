package foxmo.user.cloud.newbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@EnableDiscoveryClient
@SpringBootApplication
public class NewBeeMallCloudUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewBeeMallCloudUserServiceApplication.class,args);
    }
}
