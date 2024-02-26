package com.example.touristguide2;

import com.example.touristguide2.controller.TouristController;
import org.assertj.core.api.Assert;
import org.assertj.core.api.FactoryBasedNavigableListAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class TouristGuide2ApplicationTests {

    @Autowired
    private TouristController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }



}
