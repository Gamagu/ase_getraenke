package de.nyg.application.asegetraenke.builder;

import java.util.ArrayList;
import java.util.List;

import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;

public class ProduktBuilder {
    private String name;
    private String beschreibung;
    private String kategorie;
    private Preis preis;
    private List<Pfandwert> pfandwerte = new ArrayList<>();

    public ProduktBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProduktBuilder withBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
        return this;
    }

    public ProduktBuilder withKategorie(String kategorie) {
        this.kategorie = kategorie;
        return this;
    }

    public ProduktBuilder withPreis(Preis preis) {
        this.preis = preis;
        return this;
    }

    public ProduktBuilder withPfandwert(Pfandwert pfandwert) {
        this.pfandwerte.add(pfandwert);
        return this;
    }

    public ProduktBuilder withPfandwerte(Iterable<Pfandwert> pfandwerte) {
        pfandwerte.forEach(this.pfandwerte::add);
        return this;
    }

    public Produkt build(GetraenkeRepository repo) {
        if (name == null || beschreibung == null || kategorie == null) {
            throw new IllegalStateException("Name, Beschreibung und Kategorie müssen gesetzt sein.");
        }

        Produkt produkt = new Produkt(name, beschreibung, kategorie);

        if (preis != null) {
            produkt.setPreis(preis, repo);
        }

        if (!pfandwerte.isEmpty()) {
            produkt.setPfandAssignment(pfandwerte, repo);
        }

        return produkt;
    }
    public Produkt build() {
        if (name == null || beschreibung == null || kategorie == null) {
            throw new IllegalStateException("Name, Beschreibung und Kategorie müssen gesetzt sein.");
        }

        Produkt produkt = new Produkt(name, beschreibung, kategorie);

        return produkt;
    }
}
