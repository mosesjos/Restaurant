package com.restaurant.utils;

import com.restaurant.model.MenuDetails;
import com.restaurant.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MYM
 */
public class CommonUtils {

    public static ResponseEntity<?> getRestaurantFromMenu(Set<MenuDetails> menuDetails){

        Set<Restaurant> restaurants = new HashSet<>();

        if (!CollectionUtils.isEmpty(menuDetails)) {
            menuDetails.forEach(menu -> {
                restaurants.add(menu.getRestaurant());
            });
            return new ResponseEntity(restaurants, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }
}
