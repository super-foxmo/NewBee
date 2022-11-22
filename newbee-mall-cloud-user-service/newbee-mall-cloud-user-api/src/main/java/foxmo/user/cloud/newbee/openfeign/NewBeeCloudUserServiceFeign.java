package foxmo.user.cloud.newbee.openfeign;

import foxmo.common.cloud.newbee.dto.Result;
import foxmo.common.cloud.newbee.pojo.LoginAdminUser;
import foxmo.user.cloud.newbee.dto.MallUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "newbee-mall-cloud-user-service",path = "/users")
public interface NewBeeCloudUserServiceFeign {
    @GetMapping("/admin/{token}")
    Result<LoginAdminUser> getAdminUserByToken(@PathVariable(value = "token") String token);

    @RequestMapping(value = "/mall/getDetailByToken", method = RequestMethod.GET)
    Result<MallUserDTO> getMallUserByToken(@RequestParam("token") String token);
}
