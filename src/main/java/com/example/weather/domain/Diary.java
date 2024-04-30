package com.example.weather.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String weather;

    @Column(nullable = false, length = 50)
    private String icon;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false, length = 500)
    private String text;

    @Column(nullable = false)
    private LocalDate date;



    public static Diary from(DateWeather dateWeather,LocalDate date, String text){
        return Diary.builder()
                .icon(dateWeather.getIcon())
                .weather(dateWeather.getWeather())
                .temperature(dateWeather.getTemperature())
                .text(text)
                .date(date)
                .build();
    }
}
