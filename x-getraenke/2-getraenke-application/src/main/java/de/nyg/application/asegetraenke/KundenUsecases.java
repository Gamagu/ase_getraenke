package de.nyg.application.asegetraenke;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Zahlungsvorgang;
import de.nyg.domain.asegetraenke.repositories.CustomerRepository;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;


public class KundenUsecases {
    final private GetraenkeRepository grepo;
    final private CustomerRepository crepo;
    public KundenUsecases(GetraenkeRepository grepo, CustomerRepository crepo){
        this.grepo = grepo;
        this.crepo = crepo;
    }

    public Kunde createKunde(Kunde kunde){
        crepo.addKunde(kunde);
        return kunde;
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

    public double getKundenOrderSum(Kunde kunde){
        return StreamSupport.stream(getAllBestellungen(kunde).spliterator(), false).map(order -> order.getSumOfOrder()).mapToDouble(order -> order).sum();
    }

    public double getKundenZahlungenSum(Kunde kunde){
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
