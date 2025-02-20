package com.asegetraenke;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Zahlungsvorgang;
import com.asegetraenke.repositories.GetraenkeRepository;


public abstract class kundeusecases {
    final private GetraenkeRepository repo;
    public kundeusecases(GetraenkeRepository repo){
        this.repo = repo;
    }

    public Kunde createKunde(String name, String nachname, String eMail){
        Kunde k = new Kunde(name, nachname, eMail);
        repo.addKunde(k);
        return k;
    }

    public Iterable<Kunde> getAllKunden(){
        return repo.getKunden();
    }
    public void setName(Kunde kunde, String name, String nachname){
        kunde.setName(name, nachname);
    }

    public Optional<Kunde> getKunde(String eMail){
        return repo.getKunde(eMail);
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
        return StreamSupport.stream(repo.getZahlungsvorgaenge().spliterator(), false).filter(zahl -> zahl.getKunde().equals(kunde)).toList();
    }


    public Iterable<Bestellung> getAllBestellungen(Kunde kunde){
        return StreamSupport.stream(repo.getBestellungen().spliterator(), false).filter(order -> order.getKunde().equals(kunde)).collect(Collectors.toList());
    }
}
