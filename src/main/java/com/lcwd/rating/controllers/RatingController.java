package com.lcwd.rating.controllers;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //create  rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        Rating newRating = ratingService.create(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRating);
    }

    //get all ratings
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRating(){
        List<Rating> ratingList = ratingService.getRatings();
        return ResponseEntity.ok(ratingList);
    }

    //getRatingByUserId
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId){
        List<Rating> userListRating = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(userListRating);
    }

    //getRatingByHotelId
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId){
        List<Rating> hotelListRating = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.ok(hotelListRating);
    }
}
