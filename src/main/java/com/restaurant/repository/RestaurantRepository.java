package com.restaurant.repository;

import com.restaurant.model.Restaurant;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author MYM
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Restaurant findByIdAndActiveResturant(Long restaurantId, boolean isActive);

    Set<Restaurant> findByNameStartsWithIgnoreCaseAndActiveResturant(String name, boolean isActive);

    Restaurant findByNameIgnoreCaseAndActiveResturant(String name, boolean isActive);

    Set<Restaurant> findByAddressContainingIgnoreCaseAndActiveResturant(String address,  boolean isActive);

    Set<Restaurant> findByOverallRatingEqualsAndActiveResturant(int rating,   boolean isActive);

    Set<Restaurant> findByStartTimeLessThanAndEndTimeGreaterThanAndActiveResturant(float startTime,
                                                                                         float endTime,  boolean isActive);

    Set<Restaurant> findByLongitudeBetweenAndLatitudeBetween(Double longitueStart, Double longitueEnd,
                                                             Double latitudeStart, Double latitudeEnd);
}
