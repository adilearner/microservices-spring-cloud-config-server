package com.ms.user.service.services.impl;

import com.ms.user.service.entities.Hotel;
import com.ms.user.service.entities.Rating;
import com.ms.user.service.entities.User;
import com.ms.user.service.exceptions.ResourceNotFoundException;
import com.ms.user.service.external.feign_services.HotelService;
import com.ms.user.service.repositories.UserRepository;
import com.ms.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //Generates unique userID everytime new user is created
        String randomUserID = UUID.randomUUID().toString();
        user.setUserId(randomUserID);
        return userRepository.save(user);
    }

    //Get all the user
    @Override
    public List<User> getAllUser() {
        //Implement RATING SERVICE CALL : USING REST TEMPLATE
        return userRepository.findAll();
    }


    //Get Single User
    @Override
    public User getUser(String userId) {
        //Get User from database with the help of user repository
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException
                        ("User with given id is not found on the server !! :"+userId));
        //fetch rating of the above user from the RATING SERVICE
        //http://localhost:8083/ratings/users/ba1d4991-fe06-4538-9e16-f0359252c2a9

        /*Rating[] ratingsOfUser = restTemplate
                    .getForObject("http://localhost:8083/ratings/users/"+user.getUserId()
                            , Rating[].class);*/
        Rating[] ratingsOfUser = restTemplate
                .getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId()
                        , Rating[].class);


        logger.info("User-Ratings combined : {}",ratingsOfUser);

        //Converting Rating array to list of Rating using streams
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
        //api call to hotel service to fetch the hotel for every rating
        //http://localhost:8082/hotels/e2a446d9-db99-4c91-a028-d1d2840f69ff
             
        /*ResponseEntity<Hotel> forEntity =
                restTemplate.getForEntity("http://localhost:8082/hotels/"+rating.getHotelId()
                        , Hotel.class);*/

        /*ResponseEntity<Hotel> forEntity =
                restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId()
                            , Hotel.class);

        Hotel hotel = forEntity.getBody();*/

            //instead of using rest template as above implementing rest feign client call
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

        //logger.info("response status code: {}",forEntity.getStatusCode());
        //set the hotel to rating
        rating.setHotel(hotel);
        //return the rating
        return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public void deleteUser(String userId) {

    }
}
