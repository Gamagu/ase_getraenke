package com.example.valueobjects;

import java.time.LocalDate;
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
    public Preis(double preis, Priced obj, LocalDateTime date){
        this.preis = preis;
        this.obj = obj;
        time = date;
    }
    
    public Priced getParentObject(){
        return obj;
    }

    public double getPrice(){
        return preis;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Preis){
            return ((Preis)obj).obj == this.obj &&  ((Preis)obj).preis == this.preis && ((Preis)obj).time.equals(this.time);
        }
        else{
            return false;
        }
    }
}
