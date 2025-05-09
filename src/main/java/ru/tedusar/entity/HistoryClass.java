package ru.tedusar.entity;

import java.time.LocalDateTime;

public class HistoryClass {
    private Integer idRequest;
    private Integer idForecast;
    private Integer idUser;
    private String requestTime;

    public HistoryClass(int id_forecast, int id_user) {
        this.idForecast = id_forecast;
        this.idUser = id_user;
        this.requestTime = String.valueOf(LocalDateTime.now());
    }

    public HistoryClass(Integer id_request, Integer id_forecast, Integer id_user, String requestTime) {
        this.idRequest = id_request;
        this.idForecast = id_forecast;
        this.idUser = id_user;
        this.requestTime = requestTime;
    }

    public Integer getIdForecast() {return idForecast;}
    public Integer getIdUser() {return idUser;}
    public Integer getIdRequest() {return idRequest;}
    public String getRequestTime() {return requestTime;}

    public void setIdForecast(Integer idForecast) {this.idForecast = idForecast;}
    public void setIdUser(Integer idUser) { this.idUser = idUser;}
    public void setRequestTime(String requestTime) {this.requestTime = requestTime;}
    public void setIdRequest(Integer idRequest) {this.idRequest = idRequest;}

}
