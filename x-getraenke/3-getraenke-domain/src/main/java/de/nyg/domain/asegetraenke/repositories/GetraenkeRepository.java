package de.nyg.domain.asegetraenke.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import de.nyg.domain.asegetraenke.entities.Bestellung;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Lieferung;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;
import de.nyg.domain.asegetraenke.valueobjects.Preis.Priced;

public abstract interface GetraenkeRepository {
    abstract public Iterable<Produkt> getProdukte();

    abstract public Iterable<Pfandwert> getPfandwerte();

    abstract public Iterable<Bestellung> getBestellungen();

    abstract public Iterable<Lieferung> getLieferungen();

    abstract public Iterable<Lieferung> getLieferungen(int lieferId);

    abstract public Iterable<Lieferung> getLieferungen(Produkt produkt);

    abstract public void addProdukt(Produkt produkt);

    abstract public void addPfandwert(Pfandwert pfandwert);

    abstract public void addBestellung(Bestellung bestellung);

    abstract public void addPrice(Preis preis);

    abstract public void addLieferung(Lieferung lieferung);

    abstract public Optional<Produkt> getProdukt(UUID id);

    abstract public Optional<Pfandwert> getPfandwert(UUID id);

    abstract public Optional<Bestellung> getBestellungen(Kunde kunde);

    abstract public Optional<Preis> getPreis(Priced obj, double price, LocalDateTime data);

    abstract public Iterable<Preis> getPreisForProdukt(Produkt produkt);

    abstract public void safe() throws Exception;

}
