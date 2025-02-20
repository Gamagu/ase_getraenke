package com.example.repository;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Zahlungsvorgang;
import com.example.repositories.UserRepository;

class UserRepositoryImpl extends UserRepository{
    private DataSet data;
    
    @Override
    public Optional<Kunde> getKunde(UUID id) {
        return data.kunden.stream()
                .filter(kunde -> kunde.getId().equals(id))
                .findFirst();
    }

    @Override   
    public Iterable<Kunde> getKunden() {

        return Collections.unmodifiableList(data.kunden);
    };

    @Override
    public Iterable<Zahlungsvorgang> getZahlungsvorgaenge() {
        return Collections.unmodifiableList(data.zahlungsvorgaenge);
    }

    @Override
    public void addKunde(Kunde kunde) {
        data.kunden.add(kunde);
    }

    @Override
    public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang) {
        data.zahlungsvorgaenge.add(zahlungsvorgang);
    }

    @Override
    public Zahlungsvorgang getZahlungsvorgang(UUID id) {
        return data.zahlungsvorgaenge.stream()
                .filter(zahlungsvorgang -> zahlungsvorgang.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Zahlungsvorgang mit ID " + id + " existiert nicht"));
    }

    @Override
    public Optional<Kunde> getKunde(String email){
        return data.kunden.stream().filter(kunde -> kunde.getMail().equals(email)).findFirst();
    }

    @Override
    public Optional<Bestellung> getBestellungen(Kunde kunde) {
        return data.bestellungen.stream()
                .filter(bestellung -> bestellung.getKunde().equals(kunde))
                .findFirst();
    }

    @Override
    public void safe() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'safe'");
    }
}