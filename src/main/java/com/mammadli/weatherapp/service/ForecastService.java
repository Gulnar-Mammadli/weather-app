package com.mammadli.weatherapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mammadli.weatherapp.model.Forecast;
import com.mammadli.weatherapp.model.Forecasts;
import com.mammadli.weatherapp.model.Place;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@EnableScheduling
public class ForecastService {

    private static final String Forecast_URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36";


    public Forecasts getAll() throws IOException {
        ObjectMapper mapper = new XmlMapper();
        URL url = new URL(Forecast_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);

        InputStream input = connection.getInputStream();
        return mapper.readValue(input, Forecasts.class);
    }

    private List<Place> getPlaces(String current_date) throws IOException {

        List<Forecast> list = getAll().getForecastList();
        return list.stream()
                .filter(forecast -> forecast.getDate().equals(current_date))
                .flatMap(forecast -> Stream.concat(
                        forecast.getDay().getPlaceList().stream(),
                        forecast.getNight().getPlaceList().stream()
                ))
                .collect(Collectors.toList());
    }

    private BigDecimal getAverageMinTemperature(String current_date) throws IOException {

        List<Place> places = getPlaces(current_date);
        OptionalDouble average = places.stream()
                .map(Place::getTempMin)
                .filter(Objects::nonNull)
                .mapToInt(Integer::parseInt)
                .average();

        if (average.isPresent()) {
            return BigDecimal.valueOf(average.getAsDouble()).setScale(2, RoundingMode.HALF_UP);
        }

        return null;

    }


    private BigDecimal getAverageMaxTemperature(String current_date) throws IOException {

        OptionalDouble average = getPlaces(current_date).stream()
                .map(Place::getTempMax)
                .filter(Objects::nonNull)
                .mapToInt(Integer::parseInt)
                .average();
        if (average.isPresent()) {
            return BigDecimal.valueOf(average.getAsDouble()).setScale(2, RoundingMode.HALF_UP);
        }

        return null;
    }

    @Scheduled(fixedRate = 3600000)
    public void getAverageTemperature() throws IOException {

        LocalDate current_date = LocalDate.now();
        String date = current_date.toString();
        BigDecimal averageMinTemperature = getAverageMinTemperature(date);
        BigDecimal averageMaxTemperature = getAverageMaxTemperature(date);

        String result = String.format("Average temperatures for %s: %.4f (min), %.4f (max)\n",
                date,
                averageMinTemperature,
                averageMaxTemperature);
        File file = new File("average_temperature.txt");
        Files.write(Paths.get(file.toURI()), result.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public Place getForecast(String date, String place) throws IOException {

        List<Forecast> list = getAll().getForecastList();
        Optional<Forecast.Day> day = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .map(Forecast::getDay)
                .findFirst();

        Optional<Place> result = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .flatMap(forecast ->
                        forecast.getDay().getPlaceList().stream())
                .filter(p -> p.getName().replaceAll("[^a-zA-Z]+", "").trim()
                        .equalsIgnoreCase(place.replaceAll("[^a-zA-Z]+", "").trim()))
                .findFirst();

        if (result.isPresent()) {
            return result.get();
        } else {

            Place place1 = new Place();
            place1.setPhenomenon(day.get().getPhenomenon());
            place1.setTempMin(day.get().getTempMin());
            place1.setTempMax(day.get().getTempMax());
            place1.setName("No info for this city. This is general info for day");
            return place1;
        }

    }


    public Place getNightForecast(String date, String place) throws IOException {

        List<Forecast> list = getAll().getForecastList();
        Optional<Place> result = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .flatMap(forecast ->
                        forecast.getNight().getPlaceList().stream())
                .filter(p -> p.getName().replaceAll("[^a-zA-Z]+", "").trim()
                        .equalsIgnoreCase(place.replaceAll("[^a-zA-Z]+", "").trim()))
                .findFirst();
        return result.orElse(null);
    }

    public List<String> getTextInfo(String date) throws IOException {
        List<Forecast> list = getAll().getForecastList();
        List<String> strings = new ArrayList<>();
        Optional<String> s1 = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .map(Forecast::getDay)
                .map(day -> day.getText())
                .findFirst();

        Optional<String> s2 = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .map(Forecast::getNight)
                .map(day -> day.getText())
                .findFirst();

        strings.add(0, s1.orElse(null));
        strings.add(1, s2.orElse(null));
        return strings;
    }


    //    TODO remove
    public String getNightInfo(String date) throws IOException {
        List<Forecast> list = getAll().getForecastList();
        Optional<String> s = list.stream()
                .filter(forecast -> forecast.getDate().equals(date))
                .map(Forecast::getNight)
                .map(day -> day.getText())
                .findFirst();
        return s.orElse(null);
    }

}
