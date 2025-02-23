package com.asegetraenke.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Lieferung;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.repositories.GetraenkeRepository;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;
import com.asegetraenke.valueobjects.Preis.Priced;

import java.time.LocalDateTime;
import java.util.Collections;

public class GetraenkeRepositoryImpl extends GetraenkeRepository {
    private final RepositoryData data;

    public GetraenkeRepositoryImpl(RepositoryData data) {
        this.data = data;
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

    public Produkt getProdukt(UUID id) {
        return data.produkte.stream()
                .filter(produkt -> produkt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Produkt mit ID " + id + " existiert nicht"));
    }

    public Pfandwert getPfandwert(UUID id) {
        return data.pfandwerte.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Pfandwert mit ID " + id + " existiert nicht"));
    }

    public Bestellung getBestellungen(UUID id) {
        return data.bestellungen.stream()
                .filter(bestellung -> bestellung.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Bestellungen mit ID " + id + " existiert nicht"));
    }

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
}
