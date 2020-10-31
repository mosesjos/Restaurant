package com.restaurant.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ApplicationProperties {

    @Value("${security.technical.user}")
    private String technicalUser;

    @Value("${security.technical.password}")
    private String technicalPassword;

}
