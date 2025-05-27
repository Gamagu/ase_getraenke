package de.nyg.domain.asegetraenke.valueobjects;

import java.time.LocalDateTime;

import de.nyg.domain.asegetraenke.util.EntityWrapper;


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

    @Override
    public String toString() {
        return String.format("%s %s %s",this.wert, this.beschreibung, this.creationDate);
    }
}
