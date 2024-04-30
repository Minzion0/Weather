package com.example.weather.service;

import com.example.weather.WeatherApplication;
import com.example.weather.domain.DateWeather;
import com.example.weather.domain.Diary;
import com.example.weather.error.InvalidDate;
import com.example.weather.repository.DateWeatherRepository;
import com.example.weather.repository.DiaryRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;
//    private static final Logger logger= LoggerFactory.getLogger(WeatherApplication.class);
    @Value("${openweathermap.key}")
    private String apiKey;


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {
//        logger.info("started to created diary");
        DateWeather dateWeather = getDateWeather(date);
        //파싱 완료된 데이터 + 일기 값 저장하기
        Diary diary = Diary.from(dateWeather, date, text);

        diaryRepository.save(diary);
//        logger.info("end created diary");
    }

    private DateWeather getDateWeather(LocalDate date) {
        List<DateWeather> dateWeatherList = dateWeatherRepository.findAllByDate(date);
        if (dateWeatherList.isEmpty()) {
           return getWeatherFromApi();
        }
        return dateWeatherList.get(0);
    }

    private String getWeatherString() {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;
        //API 호출 하는 메소드
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //apiUrl 에 있는 주소로 Get 메소드를 이용해 데이터 요청을 보낸다.
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader br;
            //보낸 데이터 요청 응답이 200
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                //200 이외 코드가 올시
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            //while 을 이용하여 br에 담긴 모든 문자를 담을때까지 반복
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //string으로 반환
            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private DateWeather getWeatherFromApi() {
        String weatherData = getWeatherString();
        //받은 데어터를 JSON 파싱하기
        Map<String, Object> parseWeather = parseWeather(weatherData);

        return DateWeather.from(parseWeather, LocalDate.now());
    }

    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);

        } catch (ParseException e) {
            log.error("pares Exception");
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));

        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData;
        weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;

    }

    public List<Diary> readDiary(LocalDate date) {
        if (date.isAfter(LocalDate.ofYearDay(3050,1))){
            throw new InvalidDate();
        }
        return diaryRepository.findAllByDate(date);


    }

    public List<Diary> readDiarys(LocalDate startDate, LocalDate endDate) {
//        logger.debug("read Diary");
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    @Transactional(readOnly = false)
    public void updateDiary(LocalDate date, String text) {
        Diary diary = diaryRepository.findByDate(date);
        diary.setText(text);
    }

    @Transactional(readOnly = false)
    public void deleteDiary(LocalDate date) {
        diaryRepository.deleteAllByDate(date);
    }

    @Transactional(readOnly = false)
    //매일 새벽 1시에 동작하는 스케줄러
    @Scheduled(cron = " 0 0 1 * * *")
    public void saveWeatherDate() {

        DateWeather dateWeather = getWeatherFromApi();
        log.info("{} : save weather data", dateWeather.getDate());
        dateWeatherRepository.save(dateWeather);
    }
}
