package foxmo.goods.cloud.newbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class NewBeeMallCloudGoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewBeeMallCloudGoodsServiceApplication.class,args);
    }
}
