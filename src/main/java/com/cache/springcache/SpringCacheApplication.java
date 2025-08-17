package com.cache.springcache;

import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.UnifiedJedis;

@SpringBootApplication
public class SpringCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCacheApplication.class, args);
    }

    @PreDestroy
    public void destroyCache(){
        System.out.println("destroy cache");
       try(UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379")){ // connecting to the redis and destroying my cache
           jedis.flushAll();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
    }

}
