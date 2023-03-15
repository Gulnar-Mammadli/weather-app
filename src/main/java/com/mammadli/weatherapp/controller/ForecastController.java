package com.mammadli.weatherapp.controller;


import com.mammadli.weatherapp.model.Forecast;
import com.mammadli.weatherapp.model.Place;
import com.mammadli.weatherapp.service.ForecastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081/")
public class ForecastController {

    public String current_date;
    private final ForecastService forecastService;

    @GetMapping("/getForecasts")
    public List<Forecast> getForecasts() throws IOException {
        return forecastService.getAll().getForecastList();
    }

    @GetMapping("/date")
    public String getDate(@RequestParam String current_date) {
        this.current_date = current_date;
        return current_date;
    }

    @GetMapping("/min/{current_date}")
    public Double getAverageMinTemperature(@PathVariable String current_date) throws IOException {
        return forecastService.getAverageMinTemperature(current_date);
    }

    @GetMapping("/max/{current_date}")
    public Double getAverageMaxTemperature(@PathVariable String current_date) throws IOException {
        return forecastService.getAverageMaxTemperature(current_date);
    }


    @GetMapping("/date/{date}/place/{place}")
    public Place getForecast(@PathVariable String date, @PathVariable String place) throws IOException {
        return forecastService.getForecast(date, place);
    }

//    @GetMapping("/read")
//    public void readAverageTemp() throws IOException{
//         forecastService.readAverageTemperature();
//    }


}


