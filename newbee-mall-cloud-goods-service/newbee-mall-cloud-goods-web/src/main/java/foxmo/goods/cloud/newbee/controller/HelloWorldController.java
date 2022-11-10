package foxmo.goods.cloud.newbee.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "helloworld类测试")
@RestController
public class HelloWorldController {
    @ApiOperation("测试方法")
    @GetMapping("/sayHello")
    public String sayHelloWorld(){
        return "Hello World";
    }
}
