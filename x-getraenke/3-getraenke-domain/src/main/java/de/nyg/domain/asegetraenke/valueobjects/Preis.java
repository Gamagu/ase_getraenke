package de.nyg.domain.asegetraenke.valueobjects;

import java.time.LocalDateTime;
import java.util.Objects;

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
        if (obj instanceof Preis) {
            Preis other = (Preis) obj;
            return Objects.equals(this.obj, other.obj) &&
                   Double.compare(this.preis, other.preis) == 0 &&
                   this.time != null && other.time != null && this.time.isEqual(other.time);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return String.format("%s %s",this.getPrice(), this.getCreationDate());
    }
}
