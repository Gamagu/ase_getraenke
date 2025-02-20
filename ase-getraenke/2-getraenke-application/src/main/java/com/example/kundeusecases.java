package com.example;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Zahlungsvorgang;
import com.example.repositories.GetraenkeRepository;
import com.example.repositories.UserRepository;

public abstract class kundeusecases {
    final private GetraenkeRepository gRepo;
    final private UserRepository uRepo;
    public kundeusecases(GetraenkeRepository grepo, UserRepository urepo){
        this.gRepo = grepo;
        this.uRepo = urepo;
    }

    public Kunde createKunde(String name, String nachname, String eMail){
        Kunde k = new Kunde(name, nachname, eMail);
        uRepo.addKunde(k);
        return k;
    }

    public Iterable<Kunde> getAllKunden(){
        return uRepo.getKunden();
    }
    public void setName(Kunde kunde, String name, String nachname){
        kunde.setName(name, nachname);
    }

    public Optional<Kunde> getKunde(String eMail){
        return uRepo.getKunde(eMail);
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
        return StreamSupport.stream(uRepo.getZahlungsvorgaenge().spliterator(), false).filter(zahl -> zahl.getKunde().equals(kunde)).toList();
    }


    public Iterable<Bestellung> getAllBestellungen(Kunde kunde){
        return StreamSupport.stream(gRepo.getBestellungen().spliterator(), false).filter(order -> order.getKunde().equals(kunde)).collect(Collectors.toList());
    }

    public Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag,
            LocalDateTime date) throws Exception{
                Zahlungsvorgang z = new Zahlungsvorgang(kunde, zahlungsweg,betrag, date);
                uRepo.addZahlungsVorgang(z);
                return z;
    }
}
