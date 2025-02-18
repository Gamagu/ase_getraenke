package com.example.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.example.util.EntityWrapper;
import com.example.valueobjects.BestellungProdukt;

public class Bestellung extends EntityWrapper<Bestellung>{
    static final AtomicInteger counter = new AtomicInteger();
    Kunde kunde;
    LocalDateTime timestamp;
    Iterable<BestellungProdukt> produkte;
    int number;

    public Bestellung(Kunde kunde, LocalDateTime timestamp, Iterable<BestellungProdukt> produkte)
    {
        this.kunde = kunde;
        this.timestamp = timestamp;
        this.produkte = produkte;
        number = counter.incrementAndGet();
    }

    public Stream<BestellungProdukt> getProdukte(){
        return StreamSupport.stream(produkte.spliterator(), false);
    }

}
