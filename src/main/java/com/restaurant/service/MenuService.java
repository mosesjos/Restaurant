package com.restaurant.service;

import com.restaurant.model.MenuDetails;
import com.restaurant.model.Restaurant;
import com.restaurant.repository.MenuDetailsRepository;
import com.restaurant.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author MYM
 */
@Component
public class MenuService {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuDetailsRepository menuDetailsRepository;

    public ResponseEntity<?> addMenuDetails(List<MenuDetails> menuDetails, String restaurantId) {

        Restaurant restaurant = restaurantService.getRestaurantDetails(restaurantId);

        if (restaurant == null) {
            return new ResponseEntity("Invalid Restaurant", HttpStatus.NO_CONTENT);
        }

        menuDetails.forEach(menu -> {
            menu.setRestaurant(restaurant);
            if (restaurant != null &&
                    CollectionUtils.isEmpty(restaurant.getMenus())) {
                menuDetailsRepository.save(menu);
            } else {

                List<String> menuList = new ArrayList<>();

                if (!CollectionUtils.isEmpty(restaurant.getMenus())) {
                    menuList =
                            restaurant.getMenus().stream().map(exmenu -> exmenu.getMenuName()).
                                    collect(Collectors.toList());
                }

                if (!menuList.contains(menu.getMenuName())) {
                    menuDetailsRepository.save(menu);
                }
            }
        });

        return new ResponseEntity(menuDetails, HttpStatus.OK);
    }

    public ResponseEntity<?> getMenuDetails(String restaurantId) {

        Restaurant restaurant = restaurantService.getRestaurantDetails(restaurantId);

        if (restaurant == null || CollectionUtils.isEmpty(restaurant.getMenus())) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(restaurant.getMenus(), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addMenuRating(String restaurantId, String menuId, int rating) {

        Restaurant restaurant = restaurantService.getRestaurantDetails(restaurantId);

        if (restaurant != null &&
                !CollectionUtils.isEmpty(restaurant.getMenus())) {

            restaurant.getMenus().stream().forEach(menu -> {
                if (String.valueOf(menu.getId()).equalsIgnoreCase(menuId)) {
                    menu.setMenuRating(rating);
                    menuDetailsRepository.save(menu);
                }
            });

            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.NO_CONTENT);
        }
    }

    public Set<MenuDetails> getMenuByName(String name) {
        return menuDetailsRepository.findByMenuNameIsStartingWithIgnoreCaseAndAvailable(name, true);
    }

    public ResponseEntity<?> getRestaurantByMenuRate(String menuName, int rate) {

        Set<MenuDetails> menuDetails =
                menuDetailsRepository.findByMenuNameLikeIgnoreCaseAndAndMenuRatingEqualsAndAvailable(
                        menuName, rate, true);


        return CommonUtils.getRestaurantFromMenu(menuDetails);
    }

    public void updateMenuToUnavailable(Restaurant restaurant) {

        Set<MenuDetails> menuDetails = menuDetailsRepository.findByRestaurantId(
                restaurant.getId());
        menuDetails.stream().forEach(menu -> {
            menu.setAvailable(false);
            menuDetailsRepository.save(menu);

        });
    }
}
