package com.mammadli.weatherapp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Place {

    private String name;
    private String phenomenon;

    @JacksonXmlProperty(localName = "tempmin")
    private String tempMin;

    @JacksonXmlProperty(localName = "tempmax")
    private String tempMax;
}
