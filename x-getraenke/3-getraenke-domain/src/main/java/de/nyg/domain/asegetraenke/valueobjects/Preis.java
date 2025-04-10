package de.nyg.domain.asegetraenke.valueobjects;

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

    public LocalDateTime getCreationDate(){
        return this.time;
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
