package com.restaurant.controller;

import com.restaurant.model.Restaurant;
import com.restaurant.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author MYM
 */
@RestController
@RequestMapping("/api/restaurant")
@Api(value="onlinerestaurant", description="Operations to restaurant in Online")
public class RestaurentController {

    @Autowired
    private RestaurantService restaurantService;

    /**
     * To add new restaurant to the backend
     *
     * @param restaurant
     * @return
     */
    @ApiOperation(value = "Registration of new Uer",response = Restaurant.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  Registered"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registerNewRestaurant(
            @RequestBody Restaurant restaurant) {
        return restaurantService.restaurantRegistration(restaurant);
    }


    /**
     * Adding rating to the registered restaurant
     *
     * @param restaurantId restaurant identifier
     * @param rating       rating value between 1 to 5
     * @return
     */
    @ApiOperation(value = "Rating the restaurant",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  Rated"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/{restaurantId}/rating/{rating}", method = RequestMethod.PUT)
    public ResponseEntity<?> restaurantRating(
            @PathVariable String restaurantId,
            @PathVariable int rating) {

        if (rating > 5) {
            return new ResponseEntity<>("Please enter Rating between 1 to 5",
                    HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
        return restaurantService.updateRestaurantRating(restaurantId, rating);
    }

    /**
     * Query for restaurant based on the restaurant name
     *
     * @param restaurantname restaurant name to query
     * @return List of restaurant
     */
    @ApiOperation(value = "Retrieve Restaurant details by name",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/{restaurantname}", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurantByName(
            @PathVariable String restaurantname) {
        return restaurantService.getRestaurantDetailsByName(restaurantname);

    }

    /**
     * Query restaurant based on the menu name
     *
     * @param menuname menu name
     * @return list of restaurant
     */
    @ApiOperation(value = "Retrieve Restaurant details by menu name",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/menu/{menuname}", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurantByMenu(
            @PathVariable String menuname) {
        return restaurantService.getRestaurantByMenuName(menuname);
    }

    /**
     * To fetch list of restaurant basedon the address
     *
     * @param address
     * @return list of restaurant
     */
    @ApiOperation(value = "Retrieve Restaurant details by address",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/address/{address}", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurantByAddress(
            @PathVariable String address) {
        return restaurantService.getRestaurantDetailsByAddress(address);
    }

    /**
     * fetch restaurant based on the rating of the restaurant
     *
     * @param rate rate between 1 to 5
     * @return list of restaurant
     */
    @ApiOperation(value = "Retrieve Restaurant details by Restaurant rate",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/rate/{rate}", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurantByRating(
            @PathVariable int rate) {

        if (rate > 5) {
            return new ResponseEntity<>("Please enter Rating between 1 to 5",
                    HttpStatus.EXPECTATION_FAILED);
        }
        return restaurantService.getRestaurantByRating(rate);
    }

    /**
     * List of restaurant which we are operational in the given time
     *
     * @param time
     * @return
     */
    @ApiOperation(value = "Retrieve Restaurant details by Operation time",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully  get restaurant"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/operationtime", method = RequestMethod.GET)
    public ResponseEntity<?> getRestaurantByRating(
            @RequestParam float time) {

        return restaurantService.getRestaurantByOperationaltime(time);
    }

}
