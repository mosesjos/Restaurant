package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author MYM
 */
@JsonIgnoreProperties
@Data
@Entity
@Table(name = "MENU")
public class MenuDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ApiModelProperty(notes = "Menu Name", example = "Coffee", required = true)
    @NotEmpty
    @Column(name="menuName")
    private String menuName;

    @ApiModelProperty(notes = "Menu Rating between 1 to 5", example = "1")
    private int menuRating;

    @ApiModelProperty(notes = "Menu price", example = "10")
    private long price;

    private boolean available = true;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID")
    private Restaurant restaurant;

}
