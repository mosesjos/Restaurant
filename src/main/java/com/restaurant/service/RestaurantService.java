package com.restaurant.service;

import com.restaurant.model.Restaurant;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.utils.CommonUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * @author MYM
 */
@Component
public class RestaurantService {

    @Setter
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuService menuService;

    public ResponseEntity<?> restaurantRegistration(Restaurant restaurant) {

        if (checkOperationalTiming(restaurant)) {
            Restaurant exisitngName = restaurantRepository.
                    findByNameIgnoreCaseAndActiveResturant(restaurant.getName(), true);
            if (exisitngName != null && restaurant.getId() == 0) {
                return new ResponseEntity("Restaurant Name already exist",
                        HttpStatus.BAD_REQUEST);
            }
            restaurant = restaurantRepository.save(restaurant);

            if(!restaurant.isActiveResturant()){
                menuService.updateMenuToUnavailable(restaurant);
            }

            return new ResponseEntity(restaurant, HttpStatus.CREATED);
        } else {
            return new ResponseEntity("Invalid/Not Available Start and End Operational Time",
                    HttpStatus.BAD_REQUEST);
        }
    }

    public boolean checkOperationalTiming(Restaurant restaurant) {

        if (restaurant.getStartTime() < restaurant.getEndTime()) {
            return true;
        }
        return false;
    }

    public ResponseEntity updateRestaurantRating(String restaurantId, int rating) {

        Restaurant restaurant = getRestaurantDetails(restaurantId);
        if (restaurant != null) {
            restaurant.setOverallRating(rating);
            restaurantRepository.save(restaurant);
            return new ResponseEntity(restaurant, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<?> getRestaurantDetailsByName(String resturantName) {
        Set<Restaurant> restaurants = restaurantRepository.
                findByNameStartsWithIgnoreCaseAndActiveResturant(resturantName, true);

        if (CollectionUtils.isEmpty(restaurants)) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(restaurants
                , HttpStatus.OK);
    }

    public ResponseEntity<?> getRestaurantDetailsByAddress(String resturantName) {

        Set<Restaurant> restaurants = restaurantRepository.
                findByAddressContainingIgnoreCaseAndActiveResturant(resturantName, true);

        if (CollectionUtils.isEmpty(restaurants)) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(restaurants
                , HttpStatus.OK);
    }

    public Restaurant getRestaurantDetails(String restaurantId) {
        return restaurantRepository.findByIdAndActiveResturant(Long.valueOf(restaurantId), true);
    }

    public ResponseEntity<?> getRestaurantByMenuName(String menuName) {

        return CommonUtils.getRestaurantFromMenu(menuService.getMenuByName(menuName));

    }

    public ResponseEntity<?> getRestaurantByRating(int rate) {

        Set<Restaurant> restaurants = restaurantRepository.
                findByOverallRatingEqualsAndActiveResturant(rate, true);

        if (!CollectionUtils.isEmpty(restaurants)) {
            return new ResponseEntity(restaurants, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<?> getRestaurantByOperationaltime(float time) {

        Set<Restaurant> restaurants = restaurantRepository.
                findByStartTimeLessThanAndEndTimeGreaterThanAndActiveResturant(time, time, true);

        if (!CollectionUtils.isEmpty(restaurants)) {
            return new ResponseEntity(restaurants, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }


}
