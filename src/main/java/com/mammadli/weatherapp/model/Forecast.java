package com.mammadli.weatherapp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Forecast {

    @JacksonXmlProperty(isAttribute = true)
    private String date;

    private Night night;

    private Day day;
    @Getter
    @Setter
    private static class DayNight {

        String phenomenon;

        @JacksonXmlProperty(localName = "tempmin")
        String tempMin;

        @JacksonXmlProperty(localName = "tempmax")
        String tempMax;

        String text;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "place")
        List<Place> placeList;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "wind")
        List<Wind> windList;

        String sea;
        String peipsi;

        DayNight(){
            placeList = new ArrayList<>();
            windList = new ArrayList<>();
        }

        public List<Place> getPlaceList() {
            return placeList;
        }
    }

    public static class Day extends DayNight{
        Day(){
            super();
        }
    }

    public static class Night extends DayNight{
        Night(){
            super();
        }
    }


}
