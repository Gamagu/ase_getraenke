package de.nyg.adapters.asegetraenke.repository;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Zahlungsvorgang;
import de.nyg.domain.asegetraenke.repositories.CustomerRepository;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final RepositoryData data;

    public CustomerRepositoryImpl(RepositoryData data) {
        this.data = data;
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
