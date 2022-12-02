package foxmo.goods.cloud.newbee.openfeign;

import foxmo.common.cloud.newbee.dto.Result;
import foxmo.goods.cloud.newbee.dto.NewBeeMallGoodsDTO;
import foxmo.goods.cloud.newbee.dto.UpdateStockNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "newbee-mall-cloud-goods-service",path = "/goods")
public interface NewBeeMallCloudGoodsServiceFeign {
    @GetMapping("/admin/goodsDetail")
    Result getGoodsDetail(@RequestParam("goodsId") Long goodsId);

    @GetMapping(value = "/admin/listByGoodsIds")
    Result<List<NewBeeMallGoodsDTO>> listByGoodsIds(@RequestParam(value = "goodsIds") List<Long> goodsIds);

    @PutMapping(value = "/admin/updateStock")
    Result<Boolean> updateStock(@RequestBody UpdateStockNumDTO updateStockNumDTO);
}
