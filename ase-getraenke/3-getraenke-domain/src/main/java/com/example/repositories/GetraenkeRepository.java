package com.example.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Lieferung;
import com.example.entities.Produkt;
import com.example.valueobjects.Pfandwert;
import com.example.valueobjects.Preis;
import com.example.valueobjects.Preis.Priced;

abstract public  class GetraenkeRepository implements BaseRepository{
    abstract public Iterable<Pfandwert> getPfandwerte();
    abstract public Iterable<Bestellung> getBestellungen();
    abstract public Iterable<Lieferung> getLieferungen();
    abstract public Iterable<Lieferung> getLieferungen(int lieferId);
    abstract public Iterable<Lieferung> getLieferungen(Produkt produkt);
    abstract public Iterable<Produkt> getProdukte();

    abstract public void addProdukt(Produkt produkt);
    abstract public void addPfandwert(Pfandwert pfandwert);
    abstract public void addBestellung(Bestellung bestellung);
    abstract public void addPrice(Preis preis);
    abstract public void addLieferung(Lieferung lieferung);
    abstract public Iterable<Preis> getPreisForProdukt(Produkt produkt);
    abstract public Optional<Preis> getPreis(Priced obj, double price, LocalDateTime data);
    abstract public Produkt getProdukt(UUID id);
    abstract public Pfandwert getPfandwert(UUID id);
}