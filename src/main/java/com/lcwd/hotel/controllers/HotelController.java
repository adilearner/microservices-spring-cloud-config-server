package com.lcwd.hotel.controllers;

import com.lcwd.hotel.entities.Hotel;
import com.lcwd.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    //create
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel newHotel = hotelService.create(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHotel);
    }

    //getSingle
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getSingleHotel(@PathVariable String hotelId){
        Hotel singleHotel = hotelService.getHotel(hotelId);
        return ResponseEntity.ok(singleHotel);
    }

    //getAll
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotels(){
        List<Hotel> hotelList = hotelService.getAllHotel();
        return ResponseEntity.ok(hotelList);
    }
}
