package foxmo.gateway.cloud.newbee.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import foxmo.common.cloud.newbee.dto.Result;
import foxmo.common.cloud.newbee.dto.ResultGenerator;
import foxmo.common.cloud.newbee.pojo.MallUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class ValidMallUserTokenGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 登录注册接口，直接放行
        if (exchange.getRequest().getURI().getPath().equals("/users/mall/login") || exchange.getRequest().getURI().getPath().equals("/users/mall/register")) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        if (headers == null || headers.isEmpty()) {
            // 返回错误提示
            return wrapErrorResponse(exchange, chain);
        }

        String token = headers.getFirst("token");

        if (StringUtils.isEmpty(token)) {
            // 返回错误提示
            return wrapErrorResponse(exchange, chain);
        }
        ValueOperations<String, MallUserToken> opsForMallUserToken = redisTemplate.opsForValue();
        MallUserToken tokenObject = opsForMallUserToken.get(token);
        if (tokenObject == null) {
            // 返回错误提示
            return wrapErrorResponse(exchange, chain);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    Mono<Void> wrapErrorResponse(ServerWebExchange exchange, GatewayFilterChain chain) {
        Result result = ResultGenerator.genErrorResult(416, "无权限访问");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode resultNode = mapper.valueToTree(result);
        byte[] bytes = resultNode.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(bytes);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        return exchange.getResponse().writeWith(Flux.just(dataBuffer));
    }

}