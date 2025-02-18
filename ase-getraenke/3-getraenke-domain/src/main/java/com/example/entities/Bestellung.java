package com.example.entities;

import java.time.LocalDate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.example.util.EntityWrapper;
import com.example.valueobjects.BestellungProdukt;

public class Bestellung extends EntityWrapper<Bestellung>{
    Kunde kunde;
    LocalDate timestamp;
    Iterable<BestellungProdukt> produkte;

    public Stream<BestellungProdukt> getProdukte(){
        return StreamSupport.stream(produkte.spliterator(), false);
    }

}
