package com.asegetraenke;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.util.Pair;
import com.asegetraenke.util.Triple;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;

public interface IGetraenkeUsecases {

void acceptLieferung(Iterable<Pair<Produkt, Integer>> produkte, LocalDateTime date) throws Exception;

void addPfandWert(String beschreibung, double wert) throws Exception;

Pfandwert setPfandwert(Pfandwert pfandwert, double newValue) throws Exception;

void setPfandwertProdukt(Produkt produkt, Iterable<Pfandwert> pfandliste) throws Exception;

Iterable<Pfandwert> getAllPfandwerte();

Optional<Pfandwert> getPfandWert(UUID id);

Iterable<Produkt> getAllProducts();

Preis getPriceForProdukt(Produkt product);

Iterable<Preis> getPriceHistoryForProdukt(Produkt product);

double setPriceForProdukt(Produkt product, double preis);

int getStockAmountForProdukt(Produkt product);

int getAmountLieferungenForProdukt(Produkt product);

int getOrderedAmountForProdukt(Produkt product);

void addProdukt(String name, String beschreibung, String kategorie, double preis) throws Exception;

Optional<Produkt> getProduct(UUID produktId) throws Exception;

Bestellung addBestellung(Kunde kunde, Iterable<Triple<Produkt, Integer, Double>> produkte) throws Exception;
}