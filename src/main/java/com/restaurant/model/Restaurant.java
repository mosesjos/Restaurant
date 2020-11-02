package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author MYM
 */

@JsonIgnoreProperties
@Getter
@Setter
@Entity
@Table(name = "RESTAURANT")
public class Restaurant implements Serializable {


    @ApiModelProperty(notes = "Restaurant Id", example = "12345")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(notes = "Restaurant Name", example = "XX Restaurant",
            required = true)
    @NotNull
    @NotEmpty
    private String name;

    @ApiModelProperty(notes = "Restaurant Address - whole Address with pincode",
            example = "Whole Address", required = true)
    private String address;

    @ApiModelProperty(notes = "Restaurant contact Number",
            example = "1234567890", required = false)
    @Digits(integer = 10, fraction = 0)
    private Long contactName;

    @ApiModelProperty(notes = "Restaurant latitude",
            example = "10.17348", required = false)
    private Double latitude;

    @ApiModelProperty(notes = "Restaurant longtitude",
            example = "10.17348", required = false)
    private Double longitude;

    @ApiModelProperty(notes = "Restaurant operational Start time - 24 hours",
            example = "9.30", required = true)
    @Digits(integer = 2, fraction = 2)
    private float startTime;

    @ApiModelProperty(notes = "Restaurant operational end time - 24 hours",
            example = "18.30", required = true)
    @Digits(integer = 2, fraction = 2)
    private float endTime;

    @ApiModelProperty(notes = "Restaurant Rating between  1 to 5",
            example = "1", required = false)
    private int overallRating;

    @ApiModelProperty(notes = "Restaurant is acive or not",
            example = "1", required = false)
    private boolean activeResturant = true;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private Set<MenuDetails> menus;

}
