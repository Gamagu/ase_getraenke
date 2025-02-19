package com.example.valueobjects;

import java.time.LocalDateTime;

import com.example.util.EntityWrapper;

public final class Pfandwert extends EntityWrapper<Pfandwert> {
    final public double wert;
    final public String beschreibung;
    final public LocalDateTime creationDate;

    public Pfandwert(double wert, String beschreibung){
        super();
        this.wert = wert;
        this.beschreibung = beschreibung;
        creationDate =  LocalDateTime.now();
    }
}
