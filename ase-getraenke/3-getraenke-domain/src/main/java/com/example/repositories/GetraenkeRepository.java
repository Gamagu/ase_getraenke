package com.example.repositories;

import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Lieferung;
import com.example.entities.Pfandwert;
import com.example.entities.Produkt;
import com.example.entities.Zahlungsvorgang;
import com.example.valueobjects.Preis;

public abstract class GetraenkeRepository {
    abstract public Iterable<Kunde> getKunden();
    abstract public Iterable<Produkt> getProdukte();
    abstract public Iterable<Pfandwert> getPfandwerte();
    abstract public Iterable<Bestellung> getBestellungen();
    abstract public Iterable<Zahlungsvorgang> getZahlungsvorgaenge();
    abstract public Iterable<Lieferung> getLieferungen();
    abstract public Iterable<Lieferung> getLieferungen(int lieferId);
    abstract public Iterable<Lieferung> getLieferungen(Produkt produkt);
    

    abstract public void addKunde(Kunde kunde);
    abstract public void addProdukt(Produkt produkt);
    abstract public void addPfandwert(Pfandwert pfandwert);
    abstract public void addBestellung(Bestellung bestellung);
    abstract public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang);
    abstract public void addPrice(Preis preis);


    abstract public Kunde getKunde(UUID id);
    abstract public Produkt getProdukt(UUID id);
    abstract public Pfandwert getPfandwert(UUID id);
    abstract public Bestellung getBestellungen(UUID id);
    abstract public Zahlungsvorgang getZahlungsvorgang(UUID id);

    abstract public Iterable<Preis> getPreisForProdukt(Produkt produkt);

    abstract public void safe() throws Exception;


}
