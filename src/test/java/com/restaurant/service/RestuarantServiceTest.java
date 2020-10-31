package com.restaurant.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.MenuDetails;
import com.restaurant.model.Restaurant;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MYM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@EnableJpaRepositories( basePackages = {"com.restaurant.*"})
@Transactional
public class RestuarantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Test
    public void testRestaurantResgistration() throws Exception{

        String request ="{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":10.30,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);

        ResponseEntity responseEntity =  restaurantService.restaurantRegistration(restaurant);
        Restaurant response = (Restaurant)responseEntity.getBody();
        Assert.assertTrue(response.getId() != 0);
    }


    @Test
    public void testRestaurantResgistrationWithoutOperationtime() throws Exception{

        String request ="{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        ResponseEntity responseEntity =  restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(417 ==responseEntity.getStatusCode().value());
    }

    @Test
    public void testRestaurantResgistrationWrongOperationtime() throws Exception{

        String request ="{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":9.30,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        ResponseEntity responseEntity =  restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(417 ==responseEntity.getStatusCode().value());
    }

    @Test
    public void testRestaurantResgistrationNameExist() throws Exception{

        String request ="{\n" +
                "    \"name\" : \"Moses\",\n" +
                "    \"address\": \"#08-196, Tampines\",\n" +
                "    \"contactName\":97271721,\n" +
                "    \"startTime\":10.00,\n" +
                "    \"endTime\":10.30,\n" +
                "    \"latitude\":11.178402,\n" +
                "    \"longitude\":25.219465\n" +
                "}";

        Restaurant restaurant = new ObjectMapper().readValue(request, Restaurant.class);
        restaurantService.restaurantRegistration(restaurant);

        ResponseEntity response =  restaurantService.restaurantRegistration(restaurant);
        Assert.assertTrue(417 ==response.getStatusCode().value());
    }


    @Test
    public void addMenu() throws Exception{

        String request = "\n" +
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
        List<MenuDetails> menus = new ObjectMapper().readValue(request,
                new TypeReference<List<MenuDetails>>(){});

        testRestaurantResgistration();

        ResponseEntity responseEntity =  menuService.addMenuDetails(menus, "1");
        List<MenuDetails> response = (List<MenuDetails>)responseEntity.getBody();
        Assert.assertTrue(200 ==responseEntity.getStatusCode().value());
        Assert.assertTrue(1 == response.get(0).getId());
    }


    @Transactional
    @Test
    public void addExistingMenu() throws Exception{
        addMenu();
        TestTransaction.flagForCommit();
        String request = "\n" +
                "        [{  \n" +
                "            \"menuName\": \"tea\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuName\": \"milk\"\n" +
                "        }]\n" +
                "   ";
        List<MenuDetails> menus = new ObjectMapper().readValue(request,
                new TypeReference<List<MenuDetails>>(){});
        ResponseEntity responseEntity =  menuService.addMenuDetails(menus, "1");
        List<MenuDetails> response = (List<MenuDetails>)responseEntity.getBody();
        Assert.assertTrue(null == response.get(0).getId());
    }
}
