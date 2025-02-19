package com.example.entities;

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
        super();
        this.kunde = kunde;
        this.timestamp = timestamp;
        this.produkte = produkte;
        number = counter.incrementAndGet();
    }

    public Stream<BestellungProdukt> getProdukte(){
        return StreamSupport.stream(produkte.spliterator(), false);
    }

    public Kunde getKunde(){
        return kunde;
    }

    public double getSumOfOrder(){
        double sum = 0;
        for (BestellungProdukt b : produkte){
            sum += b.getMenge() * b.getPreis().getPrice() + b.getProdukt().getPfandSum();
        }
        return sum;
    }

}
