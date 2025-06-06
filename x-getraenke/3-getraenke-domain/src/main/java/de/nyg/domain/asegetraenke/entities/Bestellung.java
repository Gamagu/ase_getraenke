package de.nyg.domain.asegetraenke.entities;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import de.nyg.domain.asegetraenke.util.EntityWrapper;
import de.nyg.domain.asegetraenke.valueobjects.BestellungProdukt;

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

    public int getNumber(){
        return number;
    }

    public double getSumOfOrder(){
        double sum = 0;
        for (BestellungProdukt b : produkte){
            sum += b.getMenge() * b.getPreis().getPrice() + b.getProdukt().getPfandSum();
        }
        return sum;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bestellung Nr. ").append(number).append("\n");
        builder.append("Kunde: ").append(kunde.getName()).append(" ").append(kunde.getNachname()).append("\n");
        builder.append("Datum: ").append(timestamp).append("\n");
        builder.append("Produkte:\n");

        for (BestellungProdukt bp : produkte) {
            builder.append(" - ")
                .append(bp.getProdukt().getName())
                .append(", Menge: ").append(bp.getMenge())
                .append(", Einzelpreis: ").append(bp.getPreis().getPrice())
                .append(", Pfand: ").append(bp.getProdukt().getPfandSum())
                .append("\n");
        }

        builder.append("Gesamtsumme: ").append(String.format("%.2f", getSumOfOrder())).append(" EUR");
        return builder.toString();
    }

}
