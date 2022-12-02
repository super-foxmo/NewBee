package foxmo.goods.cloud.newbee;

import foxmo.user.cloud.newbee.openfeign.NewBeeCloudUserServiceFeign;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
@EnableFeignClients(basePackageClasses = {NewBeeCloudUserServiceFeign.class})
public class NewBeeMallCloudGoodsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewBeeMallCloudGoodsServiceApplication.class,args);
    }
}
