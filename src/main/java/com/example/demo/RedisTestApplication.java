package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RedisTestApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTestApplication.class);

    @Autowired
    private StringRedisTemplate template;
    
	public static void main(String[] args) {
		SpringApplication.run(RedisTestApplication.class, args);
	}

	
	 // Endpoint to store a key in Redis
    @PostMapping("/storeKey")
    public void storeKey(@RequestBody String value, @RequestParam String key) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value);
        LOGGER.info("Key '{}' with value '{}' stored in Redis.", key, value);
    }

    // Endpoint to retrieve a value from Redis using a key
    @GetMapping("/getKey")
    public String getKey(@RequestParam String key) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String value = ops.get(key);
        if (value != null) {
            LOGGER.info("Retrieved value '{}' from Redis for key '{}'.", value, key);
            return value;
        } else {
            LOGGER.info("No value found in Redis for key '{}'.", key);
            return "Key not found in Redis.";
        }
    }

}
