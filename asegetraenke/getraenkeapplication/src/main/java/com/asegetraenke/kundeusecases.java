package com.asegetraenke;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Zahlungsvorgang;
import com.asegetraenke.repositories.CustomerRepository;
import com.asegetraenke.repositories.GetraenkeRepository;


public class kundeusecases {
    final private GetraenkeRepository grepo;
    final private CustomerRepository crepo;
    public kundeusecases(GetraenkeRepository grepo, CustomerRepository crepo){
        this.grepo = grepo;
        this.crepo = crepo;
    }

    public Kunde createKunde(String name, String nachname, String eMail){
        Kunde k = new Kunde(name, nachname, eMail);
        crepo.addKunde(k);
        return k;
    }

    public Iterable<Kunde> getAllKunden(){
        return crepo.getKunden();
    }
    public void setName(Kunde kunde, String name, String nachname){
        kunde.setName(name, nachname);
    }

    public Optional<Kunde> getKunde(String eMail){
        return crepo.getKunde(eMail);
    }

    public double getKundenBalance(Kunde kunde){
        return getKundenOrderSum(kunde) - getKundenZahlungenSum(kunde);
    }   

    private double getKundenOrderSum(Kunde kunde){
        return StreamSupport.stream(getAllBestellungen(kunde).spliterator(), false).map(order -> order.getSumOfOrder()).mapToDouble(order -> order).sum();
    }

    private double getKundenZahlungenSum(Kunde kunde){
        return StreamSupport.stream(getAllBestellungen(kunde).spliterator(), false).map(order -> order.getSumOfOrder()).mapToDouble(order -> order).sum();
    }

    public Iterable<Zahlungsvorgang> getAllZahlungen(Kunde kunde){
        return StreamSupport.stream(crepo.getZahlungsvorgaenge().spliterator(), false).filter(zahl -> zahl.getKunde().equals(kunde)).toList();
    }


    public Iterable<Bestellung> getAllBestellungen(Kunde kunde){
        return StreamSupport.stream(grepo.getBestellungen().spliterator(), false).filter(order -> order.getKunde().equals(kunde)).collect(Collectors.toList());
    }

    public Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag, LocalDateTime date){
        Zahlungsvorgang z = new Zahlungsvorgang(kunde, zahlungsweg, betrag, date);
        crepo.addZahlungsVorgang(z);
        return z;
    }
}
