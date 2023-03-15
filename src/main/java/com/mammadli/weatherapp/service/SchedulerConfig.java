//package com.mammadli.weatherapp.service;
//
//import com.mammadli.weatherapp.controller.ForecastController;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.io.IOException;
//
//@Configuration
//@EnableScheduling
//@RequiredArgsConstructor
//public class SchedulerConfig {
//    private final ForecastController controller;
//
//    @Autowired
//    private ForecastService forecastService;
//
//    @Scheduled(fixedRate = 3600000)
//    public void runScheduledTask() throws IOException {
//        String current_date = controller.current_date;
//        forecastService.getAverageTemperature(current_date);
//    }
//}
