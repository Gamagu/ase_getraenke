package com.asegetraenke.repositories;

import java.util.Optional;
import java.util.UUID;

import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Zahlungsvorgang;

public abstract class CustomerRepository {
    public abstract Iterable<Kunde> getKunden();

    abstract public Optional<Kunde> getKunde(UUID id);

    abstract public Optional<Kunde> getKunde(String email);

    abstract public Optional<Zahlungsvorgang> getZahlungsvorgang(UUID id);

    abstract public void addKunde(Kunde kunde);

    abstract public Iterable<Zahlungsvorgang> getZahlungsvorgaenge();

    abstract public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang);
}
