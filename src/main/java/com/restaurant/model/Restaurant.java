package com.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    private String address;

    private Long contactName;

    private Double latitude;

    private Double longitude;

    private float startTime;

    private float endTime;

    private int overallRating;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant")
    private Set<MenuDetails> menus;


}
