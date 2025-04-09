package adapter.asegetraenke.repository;

import java.util.ArrayList;
import java.util.List;

import domain.asegetraenke.entities.Bestellung;
import domain.asegetraenke.entities.Kunde;
import domain.asegetraenke.entities.Lieferung;
import domain.asegetraenke.entities.Produkt;
import domain.asegetraenke.entities.Zahlungsvorgang;
import domain.asegetraenke.valueobjects.Pfandwert;
import domain.asegetraenke.valueobjects.Preis;

public class RepositoryData {
    public final List<Kunde> kunden;
    public final List<Produkt> produkte;
    public final List<Pfandwert> pfandwerte;
    public final List<Bestellung> bestellungen;
    public final List<Zahlungsvorgang> zahlungsvorgaenge;
    public final List<Preis> preise;
    public final List<Lieferung> lieferungen;

    public RepositoryData(){
        kunden = new ArrayList<>();
        produkte = new ArrayList<>();
        pfandwerte = new ArrayList<>();
        bestellungen = new ArrayList<>();
        zahlungsvorgaenge = new ArrayList<>();
        preise = new ArrayList<>();
        lieferungen = new ArrayList<>();
    }
}
