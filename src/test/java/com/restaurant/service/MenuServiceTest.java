package com.restaurant.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.model.MenuDetails;
import com.restaurant.model.Restaurant;
import org.junit.After;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author MYM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.restaurant.*"})
@Transactional
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;

    @Transactional (propagation= Propagation.NESTED)
    @Before
    public void init() throws Exception {

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
        restaurantService.restaurantRegistration(restaurant);

        addMenu();
    }

    public void addMenu() throws Exception{
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

    @Test
    public void getMenu() {
        String restaurantId = "1";
        Assert.assertNotNull(menuService.getMenuDetails(restaurantId));
    }

    @Test
    public void getNotExistingMenu() {
        String restaurantId = "2";
        ResponseEntity responseEntity = menuService.getMenuDetails(restaurantId);
        Assert.assertTrue(responseEntity.getStatusCode().value() != 200);
    }

    @Test
    public void giveRating() throws Exception{
        addMenu();
        ResponseEntity response = menuService.addMenuRating("1", "3", 4);
        Assert.assertTrue((boolean) response.getBody() == true);
    }

}
