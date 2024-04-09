package com.ms.user.service.controllers;

import com.ms.user.service.entities.User;
import com.ms.user.service.services.UserService;
import com.ms.user.service.services.impl.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    //create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    int retryCount = 1;

    // Get single user
    @GetMapping("/{userId}")
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallBack")
    //@Retry(name = "ratingHotelService",fallbackMethod = "ratingHotelfallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        logger.info("Get Single User Handler: UserController");
        logger.info("Retry count: {}",retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }


    //creating fallback method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex) {
        //logger.info("Fallback is executed because service is down: ", ex.getMessage());

        User user = User.builder()
                .email("adityasng31@gmail.com")
                .name("Aditya")
                .about("This user is created dummy because some service is down")
                .userId("2424812")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    //All User Get
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = userService.getAllUser();
        return ResponseEntity.ok(userList);
    }
}
