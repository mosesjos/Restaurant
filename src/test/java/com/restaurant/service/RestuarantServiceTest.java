package com.restaurant.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.MenuDetails;
import com.restaurant.model.Restaurant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author MYM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.restaurant.*"})
public class RestuarantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Transactional
    @Before
    public void testRestaurantResgistration() throws Exception {

        String request = "{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":10.30,\n" +
                "    \"overallRating\": 5,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";
        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        restaurantService.restaurantRegistration(restaurant);

    }


    @Test
    public void testRestaurantResgistrationWithoutOperationtime() throws Exception {

        String request = "{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        ResponseEntity responseEntity = restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(400 == responseEntity.getStatusCode().value());
    }

    @Test
    public void testRestaurantResgistrationWrongOperationtime() throws Exception {

        String request = "{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":9.30,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        ResponseEntity responseEntity = restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(400 == responseEntity.getStatusCode().value());
    }

    @Test
    public void testRestaurantResgistrationNameExist() throws Exception {

        String request = "{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":10.30,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        ResponseEntity response = restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(400 == response.getStatusCode().value());
    }

    
    public void testRestaurantByRating() throws Exception {
        ResponseEntity response = restaurantService.getRestaurantByRating(5);
        Set<Restaurant> restaurant = (Set<Restaurant>) response.getBody();
        for (Restaurant restaurantDetails : restaurant) {
            Assert.assertTrue(5 == restaurantDetails.getOverallRating());
        }
    }

    @Test
    public void testRestaurantByName() throws Exception {

        ResponseEntity response = restaurantService.getRestaurantDetailsByName("Mo");
        Set<Restaurant> restaurant = (Set<Restaurant>) response.getBody();
        for (Restaurant restaurantDetails : restaurant) {
            Assert.assertTrue("Moses".equalsIgnoreCase(restaurantDetails.getName()));
        }
    }

    @Test
    public void testRestaurantByWrongName() throws Exception {

        ResponseEntity response = restaurantService.getRestaurantDetailsByName("Jo");
        Assert.assertTrue(204 == response.getStatusCodeValue());
    }

    @Test
    public void testRestaurantByAddress() throws Exception {

        ResponseEntity response = restaurantService.getRestaurantDetailsByAddress("TAM");
        Set<Restaurant> restaurant = (Set<Restaurant>) response.getBody();
        for (Restaurant restaurantDetails : restaurant) {
            Assert.assertTrue("Moses".equalsIgnoreCase(restaurantDetails.getName()));
        }
    }

    @Test
    public void testRestaurantByWrongAddress() {

        ResponseEntity response = restaurantService.getRestaurantDetailsByAddress("MAdh");
        Assert.assertTrue(204 == response.getStatusCodeValue());
    }

    @Test
    public void testRestaurantMenuAdd() throws Exception {
        String menuRequest = "\n" +
                "        [{  \n" +
                "            \"menuName\": \"tea\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuName\": \"water\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuName\": \"coffee\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuName\": \"milk\"\n" +
                "        }]\n" +
                "   ";
        List<MenuDetails> menus = new ObjectMapper().readValue(menuRequest,
                new TypeReference<List<MenuDetails>>() {
                });
        menuService.addMenuDetails(menus, "1");
    }


}
