package com.restaurant.controller;

import com.restaurant.model.MenuDetails;
import com.restaurant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MYM
 */

@RestController
@RequestMapping("/restaurant")
@Api(value="onlinerestaurant", description="Operations to restaurant in Online")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * This Rest API to add list of menu to the registered restaurant
     * @param restaurantId restaurant backend Identifier
     * @param menuDetails List of menu to be added
     * @return
     */
    @ApiOperation(value = "Add menu to restaurant",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  Menu added"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/{restaurantId}/addmenu", method = RequestMethod.POST)
    public ResponseEntity<?> addMenuToRestaurant(
            @PathVariable String restaurantId,
            @RequestBody List<MenuDetails> menuDetails) {
        return menuService.addMenuDetails(menuDetails, restaurantId);
    }

    /**
     * Based on the registered restaurant Identifier querying to get list of menu
     * @param restaurantId estaurant backend Identifier
     * @return list of Menu details
     */
    @ApiOperation(value = "Retrieve Menu details by restaurant",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/{restaurantId}/getmenu", method = RequestMethod.GET)
    public ResponseEntity<?> getMenuList(
            @PathVariable String restaurantId) {

        return menuService.getMenuDetails(restaurantId);
    }

    /**
     * To add rating to the menu in a specific restaurant
     * @param restaurantId restaurant Identifier
     * @param menuId  Menu identifier
     * @param rating rating givning to the menu
     * @return
     */
    @ApiOperation(value = "Add Rating to menu",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  Rating added to menu"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/{restaurantId}/menu/{menuId}/rating", method = RequestMethod.PUT)
    public ResponseEntity<?> addMenuRating(
            @PathVariable String restaurantId,
            @PathVariable String menuId,
            @RequestParam int rating) {

        if (rating > 5) {
            return new ResponseEntity<>("Please enter Rating between 1 to 5",
                    HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }

        return menuService.addMenuRating(restaurantId, menuId, rating);
    }


    /**
     * Querying by Menu name and rating between 1 to 5
     * @param menu menu name
     * @param rate rating value between 1 to 5
     * @return
     */
    @ApiOperation(value = "Retrieve menu details by rating",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get menu"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/menu/{menu}/rating/{rate}", method = RequestMethod.GET)
    public ResponseEntity<?> getMenuList(
            @PathVariable String menu,
            @PathVariable int rate
    ) {
        if (rate > 5) {
            return new ResponseEntity<>("Please enter Rating between 1 to 5",
                    HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        return menuService.getRestaurantByMenuRate(menu, rate);
    }

}
