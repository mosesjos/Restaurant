package com.restaurant.repository;

import com.restaurant.model.Restaurant;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * @author MYM
 */
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Restaurant findById(Long restaurantId);

    Set<Restaurant> findByNameStartsWithIgnoreCase(String name);

    Restaurant findByNameIgnoreCase(String name);

    Set<Restaurant> findByAddressContainingIgnoreCase(String address);

    Set<Restaurant> findByOverallRatingEquals(int rating);

    Set<Restaurant> findByStartTimeLessThanAndEndTimeGreaterThan(float startTime, float endTime);

    Set<Restaurant> findByLongitudeBetweenAndLatitudeBetween(Double longitueStart, Double longitueEnd,
                                                             Double latitudeStart, Double latitudeEnd);
}
