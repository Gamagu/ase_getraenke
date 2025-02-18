package com.example.valueobjects;

import java.time.LocalDateTime;

public final class Preis {
    final double preis;
    final Priced obj;
    final LocalDateTime time;

    public interface Priced{}

    public Preis(double preis, Priced obj){
        this.preis = preis;
        this.obj = obj;
        time = LocalDateTime.now();
    }
    
    public Priced getParentObject(){
        return obj;
    }

    public double getPrice(){
        return preis;
    }
}
