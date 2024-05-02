package com.example.weather.controller;

import com.example.weather.domain.Diary;
import com.example.weather.service.DiaryService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장", description = "노드")
    @PostMapping("/create/diary")
    public void createDiary(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "날짜형식 : yyyy-MM-dd",example = "2024-04-30") LocalDate date,
            @RequestBody  String text) {

        diaryService.createDiary(date,text);
    }
    @Operation(summary = "선택한 날짜의 모든 일기 데이터를 가져 옴니다")
    @GetMapping("/read/diary")
    public List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE ) @Parameter(description = "날짜형식 : yyyy-MM-dd",example = "2024-04-30") LocalDate date){
      return diaryService.readDiary(date);
    }
    @Operation(summary = "선택한 범위의 모든 일기 데이터를 가져 옵니다")
    @GetMapping("/read/diarys")
    public List<Diary> readDiarys(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "검색 범위 시작일 : yyyy-MM-dd",example = "2024-04-30") LocalDate startDate,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "검색 범위 종료일 : yyyy-MM-dd",example = "2024-04-30") LocalDate endDate){
        return diaryService.readDiarys(startDate,endDate);
    }
    @Operation(summary = "선택한 날자의 일기를 텍스트를 이용해 수정합니다 수정합니다")
    @PutMapping("/update/diary")
    void updateDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "날짜형식 : yyyy-MM-dd",example = "2024-04-30") LocalDate date,
                    @RequestBody  String text){
        diaryService.updateDiary(date,text);
    }
    @Operation(summary = "선택한 날짜의 일기를 제거합니다.")
    @DeleteMapping("/delete/diary")
    void deleteDiary(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Parameter(description = "날짜형식 : yyyy-MM-dd",example = "2024-04-30") LocalDate date){
        diaryService.deleteDiary(date);
    }
}
