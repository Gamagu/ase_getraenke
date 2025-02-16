package com.example.valueobjects;

import java.time.LocalDateTime;

public final class Preis {
    public interface Priced{}
    Preis(double preis, Priced obj){
        this.preis = preis;
        this.obj = obj;
        time = LocalDateTime.now();
    }
    double preis;
    Priced obj;
    LocalDateTime time;
}
