package ru.tedusar.classes;

import java.time.LocalDateTime;

public class HistoryClass {
    private Integer id_request;
    private Integer id_forecast;
    private Integer id_user;
    private String requestTime;

    public HistoryClass(int id_forecast, int id_user) {
        this.id_forecast = id_forecast;
        this.id_user = id_user;
        this.requestTime = String.valueOf(LocalDateTime.now());
    }

    public HistoryClass(Integer id_request, Integer id_forecast, Integer id_user, String requestTime) {
        this.id_request = id_request;
        this.id_forecast = id_forecast;
        this.id_user = id_user;
        this.requestTime = requestTime;
    }

    public Integer getId_forecast() {return id_forecast;}
    public Integer getId_user() {return id_user;}
    public Integer getId_request() {return id_request;}
    public String getRequestTime() {return requestTime;}

    public void setId_forecast(Integer id_forecast) {this.id_forecast = id_forecast;}
    public void setId_user(Integer id_user) { this.id_user = id_user;}
    public void setRequestTime(String requestTime) {this.requestTime = requestTime;}
    public void setId_request(Integer id_request) {this.id_request = id_request;}

}
