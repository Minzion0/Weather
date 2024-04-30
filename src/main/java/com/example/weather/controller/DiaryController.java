package com.example.weather.controller;

import com.example.weather.domain.Diary;
import com.example.weather.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@Tag(name = "WeatherDiary")
@Slf4j
@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;
    @Operation(summary = "일기 생성")
    @PostMapping("/create/diary")
    public void createDiary(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text) {

        diaryService.createDiary(date,text);
    }

    @GetMapping("/read/diary")
    public List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
      return diaryService.readDiary(date);
    }

    @GetMapping("/read/diarys")
    public List<Diary> readDiarys(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return diaryService.readDiarys(startDate,endDate);
    }

    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                    @RequestBody String text){
        diaryService.updateDiary(date,text);
    }
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        diaryService.deleteDiary(date);
    }
}
