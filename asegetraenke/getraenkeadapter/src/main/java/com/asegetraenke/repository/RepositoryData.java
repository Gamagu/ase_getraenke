package com.asegetraenke.repository;

import java.util.ArrayList;
import java.util.List;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Lieferung;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.entities.Zahlungsvorgang;
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

    RepositoryData(){
        kunden = new ArrayList<>();
        produkte = new ArrayList<>();
        pfandwerte = new ArrayList<>();
        bestellungen = new ArrayList<>();
        zahlungsvorgaenge = new ArrayList<>();
        preise = new ArrayList<>();
        lieferungen = new ArrayList<>();
    }
}
