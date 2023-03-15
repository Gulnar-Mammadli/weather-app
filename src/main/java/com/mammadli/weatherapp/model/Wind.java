package com.mammadli.weatherapp.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {

    private String name;
    private String direction;

    @JacksonXmlProperty(localName = "speedmin")
    private String speedMin;

    @JacksonXmlProperty(localName = "speedmax")
    private String speedMax;

    private String gust;
}
