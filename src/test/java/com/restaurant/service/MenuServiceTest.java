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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author MYM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.restaurant.*"})
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RestaurantService restaurantService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
    public void addMenu() {

        String restaurantId = "1";
        ResponseEntity responseEntity = menuService.getMenuDetails(restaurantId);
        Set<MenuDetails> response = (Set<MenuDetails>)responseEntity.getBody();
        Assert.assertTrue(200 ==responseEntity.getStatusCode().value());
    }


    @Test
    public void addExistingMenu() throws Exception {
        String request = "\n" +
                "        [{  \n" +
                "            \"menuName\": \"tea\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"menuName\": \"milk\"\n" +
                "        }]\n" +
                "   ";
        List<MenuDetails> menus = new ObjectMapper().readValue(request,
                new TypeReference<List<MenuDetails>>() {
                });
        ResponseEntity responseEntity = menuService.addMenuDetails(menus, "1");
        List<MenuDetails> response = (List<MenuDetails>) responseEntity.getBody();

        for(MenuDetails menuDetail : response){
            Assert.assertTrue(null == menuDetail.getId());
        }

    }

    @Test
    public void getMenu() {
        String restaurantId = "1";
        ResponseEntity responseEntity = menuService.getMenuDetails(restaurantId);
        Set<MenuDetails> response = (Set<MenuDetails>) responseEntity.getBody();
        Assert.assertTrue(!CollectionUtils.isEmpty(response));
    }

    @Test
    public void getNotExistingMenu() {
        String restaurantId = "2";
        ResponseEntity responseEntity = menuService.getMenuDetails(restaurantId);
        Assert.assertTrue(responseEntity.getStatusCode().value() == 204);
    }

    @Test
    public void giveRating() {
        ResponseEntity response = menuService.addMenuRating("1", "3", 4);
        Assert.assertTrue((boolean) response.getBody() == true);
    }


    @Test
    public void testRestaurantByMenu() throws Exception{


        ResponseEntity response =  restaurantService.getRestaurantByMenuName( "milk");
        Set<Restaurant> restaurant = (Set<Restaurant>) response.getBody();
        for(Restaurant restaurantDetails :restaurant) {
            Assert.assertTrue("Moses".equalsIgnoreCase(restaurantDetails.getName()));
        }
    }

}
