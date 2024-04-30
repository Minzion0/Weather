package com.example.weather;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WeatherApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void equalTest(){
        //given
        assertEquals("a","a");
        //when

        //then
    }

    @Test
    void nullTest(){
        //given
        assertNull(null);
        //when

        //then
    }
    @Test
    void trueTest (){
        //given
        assertTrue(true);
        //when

        //then
    }


}
