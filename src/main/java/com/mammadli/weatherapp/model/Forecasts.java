package com.mammadli.weatherapp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Forecasts {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "forecast")
    private List<Forecast> forecastList;

    Forecasts(){
        forecastList = new ArrayList<>();
    }
}
