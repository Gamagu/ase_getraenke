package com.asegetraenke.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Lieferung;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.entities.Zahlungsvorgang;
import com.asegetraenke.valueobjects.BestellungProdukt;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;

public class RepositoryData {
    public final List<Kunde> kunden;
    public final List<Produkt> produkte;
    public final List<Pfandwert> pfandwerte;
    public final List<Bestellung> bestellungen;
    public final List<Zahlungsvorgang> zahlungsvorgaenge;
    public final List<Preis> preise;
    public final List<Lieferung> lieferungen;

    public Kunde testKunde;

    public RepositoryData(){
        kunden = new ArrayList<>();
        produkte = new ArrayList<>();
        pfandwerte = new ArrayList<>();
        bestellungen = new ArrayList<>();
        zahlungsvorgaenge = new ArrayList<>();
        preise = new ArrayList<>();
        lieferungen = new ArrayList<>();
        testKunde = new Kunde("Ben", "Ben", "Ben@Ben.de");

        kunden.add(new Kunde("Ben", "Ben", "Ben@Ben.de"));
        kunden.add(new Kunde("Peter", "Peter", "Peter@Ben.de"));
        Produkt p = new Produkt("TestP", "TestP", "TestP");
        produkte.add(p);
        ArrayList<Pfandwert> pfandwerte = new ArrayList<>();
        pfandwerte.add(new Pfandwert(0, null));
        pfandwerte.add(new Pfandwert(5, "Test"));
        pfandwerte.add(new Pfandwert(2.5, "Test"));
        for(Pfandwert pfandwert : this.pfandwerte){
            pfandwerte.add(pfandwert);
        }   
        ArrayList<BestellungProdukt> b = new ArrayList<>();
        Preis preis = new Preis(10.0, p);
        preise.add(preis);
        
        b.add(new BestellungProdukt(p, preis, 1));
        bestellungen.add(new Bestellung(testKunde, LocalDateTime.now(), b));

        Zahlungsvorgang z1 = new Zahlungsvorgang(testKunde, "Paypal", 10, LocalDateTime.now());
        zahlungsvorgaenge.add(z1);

    }
}
