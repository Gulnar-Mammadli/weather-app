package com.mammadli.weatherapp.controller;


import com.mammadli.weatherapp.model.Forecast;
import com.mammadli.weatherapp.model.Place;
import com.mammadli.weatherapp.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081/")
public class ForecastController {

    private final ForecastService forecastService;

    @GetMapping("/getForecasts")
    public List<Forecast> getForecasts() throws IOException {
        return forecastService.getAll().getForecastList();
    }

    @GetMapping("/min/{current_date}")
    public BigDecimal getAverageMinTemperature(@PathVariable String current_date) throws IOException {
        return forecastService.getAverageMinTemperature(current_date);
    }

    @GetMapping("/max/{current_date}")
    public BigDecimal getAverageMaxTemperature(@PathVariable String current_date) throws IOException {
        return forecastService.getAverageMaxTemperature(current_date);
    }


    @GetMapping("/date/{date}/place/{place}")
    public Place getForecast(@PathVariable String date, @PathVariable String place) throws IOException {
        return forecastService.getForecast(date, place);
    }

    @GetMapping("/night/date/{date}/place/{place}")
    public Place getNightForecast(@PathVariable String date, @PathVariable String place) throws IOException {
        return forecastService.getNightForecast(date, place);
    }

    @GetMapping("/average/date/{date}")
    public String getAverage(@PathVariable String date) throws IOException{
        return forecastService.readFile(date);
    }

}


