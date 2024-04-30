package com.example.weather.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DateWeather {
    @Id
    private LocalDate date;
    @Column(nullable = false,length = 50)
    private String weather;
    @Column(nullable = false,length = 50)
    private String icon;
    @Column(nullable = false)
    private double temperature;

    public static DateWeather from(Map<String,Object> parseWeather, LocalDate date){
        return DateWeather.builder()
                .icon(parseWeather.get("icon").toString())
                .weather(parseWeather.get("main").toString())
                .temperature((double)parseWeather.get("temp"))
                .date(date)
                .build();
    }

}
