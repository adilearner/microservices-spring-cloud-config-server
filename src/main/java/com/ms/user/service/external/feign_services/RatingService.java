package com.ms.user.service.external.feign_services;

import com.ms.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //GET

    //POST
    @PostMapping("/ratings")
    public Rating createRating(Rating values);

    //PUT
    @PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable("ratingId") String ratingId, Rating rating);

    /*Suppose you want more information from HttpResponse then change the return type from Rating to
    ResponseEntity<Rating>  and then you can get more info */
    //public ResponseEntity<Rating> updateRating(@PathVariable("ratingId") String ratingId, Rating rating);

    //DELETE
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable("ratingId") String ratingId);
}
