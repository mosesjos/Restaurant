package com.restaurant.repository;

import com.restaurant.model.MenuDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author MYM
 */
public interface MenuDetailsRepository extends CrudRepository<MenuDetails, Long> {

    Set<MenuDetails> findByMenuNameIsStartingWithIgnoreCaseAndAvailable(String name, boolean isAvailable);

    Set<MenuDetails> findByMenuNameLikeIgnoreCaseAndAndMenuRatingEqualsAndAvailable(String name,
                                                                                    int rating, boolean isAvailable);

    Set<MenuDetails> findByRestaurantId(Long restaurantId);


}
