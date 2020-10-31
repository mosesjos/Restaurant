package com.restaurant.repository;

import com.restaurant.model.MenuDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @author MYM
 */
public interface MenuDetailsRepository extends CrudRepository<MenuDetails, Long> {

    Set<MenuDetails> findByMenuNameIsStartingWithIgnoreCase(String name);

    Set<MenuDetails> findByMenuNameLikeIgnoreCaseAndAndMenuRatingEquals(String name, int rating);


}
