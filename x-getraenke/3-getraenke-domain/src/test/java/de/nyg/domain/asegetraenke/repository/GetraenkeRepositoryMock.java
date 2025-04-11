package de.nyg.domain.asegetraenke.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Lieferung;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.entities.Zahlungsvorgang;
import de.nyg.domain.asegetraenke.repositories.CustomerRepository;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;
import de.nyg.domain.asegetraenke.valueobjects.Preis.Priced;

import java.time.LocalDateTime;
import java.util.Collections;

public class GetraenkeRepositoryMock implements GetraenkeRepository, CustomerRepository {
    private final RepositoryData data;
    static  GetraenkeRepositoryMock instance;

    public static GetraenkeRepositoryMock getGetraenkeMockRepo() {
        return instance;
    }

    public GetraenkeRepositoryMock(RepositoryData data) {
        this.data = data;
        if (instance == null) {
            instance = this;
        }
    }

    public Iterable<Produkt> getProdukte() {
        return Collections.unmodifiableList(data.produkte);
    }

    public Iterable<Pfandwert> getPfandwerte() {
        return Collections.unmodifiableList(data.pfandwerte);
    }

    public Iterable<Bestellung> getBestellungen() {
        return Collections.unmodifiableList(data.bestellungen);
    }

    @Override
    public Iterable<Lieferung> getLieferungen() {
        return Collections.unmodifiableList(data.lieferungen);
    }

    public void addProdukt(Produkt produkt) {
    };

    public void addPfandwert(Pfandwert pfandwert) {
        data.pfandwerte.add(pfandwert);
    }

    public void addBestellung(Bestellung bestellung) {
        data.bestellungen.add(bestellung);
    }

    public void addPreis(Preis preis) {
        data.preise.add(preis);
    }

    public void addLieferung(Lieferung lieferung) {
        data.lieferungen.add(lieferung);
    }

    public Optional<Produkt> getProdukt(UUID id) {
        return data.produkte.stream()
                .filter(produkt -> produkt.getId().equals(id))
                .findFirst();
    }

    public Optional<Pfandwert> getPfandwert(UUID id) {
        return data.pfandwerte.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst();
    }
    // @Override
    // public Optional<Bestellung> getBestellungen(UUID id) {
    //     return data.bestellungen.stream()
    //             .filter(bestellung -> bestellung.getId().equals(id))
    //             .findFirst();
    // }

    public Iterable<Preis> getPreisForProdukt(Produkt produkt) {
        return data.preise.stream().filter(preis -> preis.getParentObject() == produkt).toList();
    }

    public Iterable<Lieferung> getLieferungen(Produkt produkt) {
        return data.lieferungen.stream().filter(lieferung -> lieferung.getProdukt() == produkt).toList();
    }

    public Iterable<Lieferung> getLieferungen(int lieferId) {
        return data.lieferungen.stream().filter(lieferung -> lieferung.getLieferId() == lieferId).toList();
    }

    public void safe() throws Exception {
        throw new Exception("Not implemented!");
    }

    @Override
    public void addPrice(Preis preis) {
        if (data.produkte.stream().filter(p -> p.equals(preis.getParentObject())).count()
                + 0 /* All priced Objects */ == 1) {
            data.preise.add(preis);
        } else {
            throw new NoSuchElementException("No matching Priced Object in here");
        }
    }

    @Override
    public Optional<Preis> getPreis(Priced obj, double price, LocalDateTime date) {
        Preis p = new Preis(price, obj, date);
        return data.preise.stream().filter(preis -> preis.equals(p)).findFirst();
    }

    @Override
    public Optional<Bestellung> getBestellungen(Kunde kunde) {
        return data.bestellungen.stream()
                .filter(bestellung -> bestellung.getKunde().equals(kunde))
                .findFirst();
    }

    public Iterable<Kunde> getKunden() {

        return Collections.unmodifiableList(data.kunden);
    };

    public Iterable<Zahlungsvorgang> getZahlungsvorgaenge() {
        return Collections.unmodifiableList(data.zahlungsvorgaenge);
    }

    public void addKunde(Kunde kunde) {
        data.kunden.add(kunde);
    }

    public Optional<Kunde> getKunde(UUID id) {
        return data.kunden.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst();
    }

    public Optional<Zahlungsvorgang> getZahlungsvorgang(UUID id) {
        return data.zahlungsvorgaenge.stream()
                .filter(zahlungsvorgang -> zahlungsvorgang.getId().equals(id))
                .findFirst();
    }

    public Optional<Kunde> getKunde(String email) {
        return data.kunden.stream().filter(kunde -> kunde.getMail().equals(email)).findFirst();
    }

    public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang) {
        data.zahlungsvorgaenge.add(zahlungsvorgang);
    }
}
