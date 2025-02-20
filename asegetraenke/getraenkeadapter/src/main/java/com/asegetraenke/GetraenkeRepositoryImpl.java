package com.asegetraenke;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Lieferung;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.entities.Zahlungsvorgang;
import com.asegetraenke.repositories.GetraenkeRepository;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;
import com.asegetraenke.valueobjects.Preis.Priced;

import java.time.LocalDateTime;
import java.util.Collections;

public class GetraenkeRepositoryImpl extends GetraenkeRepository {
    private List<Kunde> kunden;
    private List<Produkt> produkte;
    private List<Pfandwert> pfandwerte;
    private List<Bestellung> bestellungen;
    private List<Zahlungsvorgang> zahlungsvorgaenge;
    private List<Preis> preise;
    private List<Lieferung> lieferungen;

    public Iterable<Kunde> getKunden() {

        return Collections.unmodifiableList(kunden);
    };

    public Iterable<Produkt> getProdukte() {
        return Collections.unmodifiableList(produkte);
    }

    public Iterable<Pfandwert> getPfandwerte() {
        return Collections.unmodifiableList(pfandwerte);
    }

    public Iterable<Bestellung> getBestellungen(){return Collections.unmodifiableList(bestellungen);}

    public Iterable<Zahlungsvorgang> getZahlungsvorgaenge() {
        return Collections.unmodifiableList(zahlungsvorgaenge);
    }

    @Override
    public Iterable<Lieferung> getLieferungen() {
        return Collections.unmodifiableList(lieferungen);
    }

    public void addKunde(Kunde kunde) {
        kunden.add(kunde);
    }

    public void addProdukt(Produkt produkt) {
    };

    public void addPfandwert(Pfandwert pfandwert) {
        pfandwerte.add(pfandwert);
    }

    public void addBestellung(Bestellung bestellung) {
        bestellungen.add(bestellung);
    }

    public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang) {
        zahlungsvorgaenge.add(zahlungsvorgang);
    }

    public void addPreis(Preis preis){
        preise.add(preis);
    }

    public void addLieferung(Lieferung lieferung){
        lieferungen.add(lieferung);
    }

    public Optional<Kunde> getKunde(UUID id) {
        return kunden.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst();
    }

    public Produkt getProdukt(UUID id) {
        return produkte.stream()
                .filter(produkt -> produkt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Produkt mit ID " + id + " existiert nicht"));
    }

    public Pfandwert getPfandwert(UUID id) {
        return pfandwerte.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Pfandwert mit ID " + id + " existiert nicht"));
    }

    public Bestellung getBestellungen(UUID id) {
        return bestellungen.stream()
                .filter(bestellung -> bestellung.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Bestellungen mit ID " + id + " existiert nicht"));
    }

    public Zahlungsvorgang getZahlungsvorgang(UUID id) {
        return zahlungsvorgaenge.stream()
                .filter(zahlungsvorgang -> zahlungsvorgang.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Zahlungsvorgang mit ID " + id + " existiert nicht"));
    }

    public Iterable<Preis> getPreisForProdukt(Produkt produkt){
        return preise.stream().filter(preis -> preis.getParentObject() == produkt).toList();
    }

    public Iterable<Lieferung> getLieferungen(Produkt produkt){
        return lieferungen.stream().filter(lieferung -> lieferung.getProdukt() == produkt).toList();
    }

    public Iterable<Lieferung> getLieferungen(int lieferId){
        return lieferungen.stream().filter(lieferung -> lieferung.getLieferId() == lieferId).toList();
    }
    public void safe() throws Exception {
        throw new Exception("Not implemented!");
    }

    @Override
    public void addPrice(Preis preis) {
        if(produkte.stream().filter(p -> p.equals(preis.getParentObject())).count() + 0 /* All priced Objects */ == 1){
            preise.add(preis);
        } else {
            throw new NoSuchElementException("No matching Priced Object in here");
        }
    }
    
    @Override
    public Optional<Preis> getPreis(Priced obj, double price, LocalDateTime date){
        Preis p = new Preis(price, obj, date);
        return preise.stream().filter(preis -> preis.equals(p)).findFirst();
     }

    public Optional<Kunde> getKunde(String email){
        return kunden.stream().filter(kunde -> kunde.getMail().equals(email)).findFirst();
    }

    @Override
    public Optional<Bestellung> getBestellungen(Kunde kunde) {
        return bestellungen.stream()
                .filter(bestellung -> bestellung.getKunde().equals(kunde))
                .findFirst();
    }
}
