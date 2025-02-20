package com.example.repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Lieferung;
import com.example.entities.Produkt;
import com.example.repositories.GetraenkeRepository;
import com.example.valueobjects.Pfandwert;
import com.example.valueobjects.Preis;
import com.example.valueobjects.Preis.Priced;

import java.time.LocalDateTime;
import java.util.Collections;

public class GetraenkeRepositoryImpl extends GetraenkeRepository {
    private DataSet data;

    GetraenkeRepositoryImpl(DataSet data){
        this.data = data;
    }

    @Override
    public Iterable<Produkt> getProdukte() {
        return Collections.unmodifiableList(data.produkte);
    }
    @Override
    public Iterable<Pfandwert> getPfandwerte() {
        return Collections.unmodifiableList(data.pfandwerte);
    }

    public Iterable<Bestellung> getBestellungen(){return Collections.unmodifiableList(data.bestellungen);}


    @Override
    public Iterable<Lieferung> getLieferungen() {
        return Collections.unmodifiableList(data.lieferungen);
    }
    
    @Override
    public void addProdukt(Produkt produkt) {
    };
    @Override
    public void addPfandwert(Pfandwert pfandwert) {
        data.pfandwerte.add(pfandwert);
    }
    @Override
    public void addBestellung(Bestellung bestellung) {
        data.bestellungen.add(bestellung);
    }
    
    @Override
    public void addLieferung(Lieferung lieferung){
        data.lieferungen.add(lieferung);
    
    }
    @Override
    public Produkt getProdukt(UUID id) {
        return data.produkte.stream()
                .filter(produkt -> produkt.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Produkt mit ID " + id + " existiert nicht"));
    }
    @Override
    public Pfandwert getPfandwert(UUID id) {
        return data.pfandwerte.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Pfandwert mit ID " + id + " existiert nicht"));
    }

    @Override
    public Iterable<Preis> getPreisForProdukt(Produkt produkt){
        return data.preise.stream().filter(preis -> preis.getParentObject() == produkt).toList();
    }
    @Override
    public Iterable<Lieferung> getLieferungen(Produkt produkt){
        return data.lieferungen.stream().filter(lieferung -> lieferung.getProdukt() == produkt).toList();
    }
    @Override
    public Iterable<Lieferung> getLieferungen(int lieferId){
        return data.lieferungen.stream().filter(lieferung -> lieferung.getLieferId() == lieferId).toList();
    }
    @Override
    public void safe() throws Exception {
        throw new Exception("Not implemented!");
    }

    @Override
    public void addPrice(Preis preis) {
        if(data.produkte.stream().filter(p -> p.equals(preis.getParentObject())).count() + 0 /* All priced Objects */ == 1){
            data.preise.add(preis);
        } else {
            throw new NoSuchElementException("No matching Priced Object in here");
        }
    }
    
    @Override
    public Optional<Preis> getPreis(Priced obj, double price, LocalDateTime date){
        Preis p = new Preis(price, obj, date);
        return data.preise.stream().filter(preis -> preis.equals(p)).findFirst();
     }
}