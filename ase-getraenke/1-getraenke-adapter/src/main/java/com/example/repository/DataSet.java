package com.example.repository;

import java.util.List;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Lieferung;
import com.example.entities.Produkt;
import com.example.entities.Zahlungsvorgang;
import com.example.valueobjects.Pfandwert;
import com.example.valueobjects.Preis;

public class DataSet {
    List<Kunde> kunden;
    List<Produkt> produkte;
    List<Pfandwert> pfandwerte;
    List<Preis> preise;
    List<Bestellung> bestellungen;
    List<Lieferung> lieferungen;
    List<Zahlungsvorgang> zahlungsvorgaenge;
}
