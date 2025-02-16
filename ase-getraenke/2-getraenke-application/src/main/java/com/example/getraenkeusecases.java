package com.example;

import java.time.LocalDateTime;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Pfandwert;
import com.example.entities.Produkt;
import com.example.entities.Zahlungsvorgang;
import com.example.util.Pair;
import com.example.valueobjects.Preis;

abstract class GetraenkeUsecases {
     /**
     * 
     * @param produkte Liste der Produkt Ids und deren Mengen
     * @param date Zeitpunkt der Lieferung
     */
    abstract void acceptLieferung(Iterable<Pair<Produkt,Integer>> produkte, LocalDateTime date);

    // soll neuen Pfandwert anlegen
    abstract void addPfandWert(String beschreibung, double wert);
    // neuen Preis für Pfand setzen
    abstract void setPfandwert(Pfandwert pfandwert);
    // setzt Pfandwert für ein Produkt. UUIDs sind die IDs der jeweiligen Produkte und der value des pairs, ist die anzahl des Pfandwertes.
    abstract void setPfandwertProdukt(Produkt produkt, Iterable<Pair<Pfandwert,Integer>> pfandliste);
    // for the next functions, the of pair is the identifier and the second is a decription. UUID could be mapped to incrementing numbers.
    abstract Iterable<Pair<Pfandwert, String>> getAllPfandwerte();
    abstract Pfandwert getPfandWert(UUID id);

    abstract Iterable<Pair<Pfandwert, String>> getAllProducts();
    abstract Preis getPriceForProdukt(Produkt product);
    abstract Iterable<Preis> getPriceHistoryForProdukt(Produkt product);
    abstract double setPriceForProdukt(Produkt product, double preis);
    abstract int getStockAmountForProdukt(Produkt product);
    abstract void addProdukt(String Name, String beschreibung, String kategorie, double preis);
    abstract Produkt getProduct(UUID produktId);

    abstract Bestellung addBestellung(Kunde kunde, Iterable<Pair<Produkt, Integer>> produkte);
    abstract Zahlungsvorgang addZahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag, LocalDateTime date);

}