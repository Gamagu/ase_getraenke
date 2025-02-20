package com.example.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;
import com.example.entities.Zahlungsvorgang;

abstract public class UserRepository implements BaseRepository{
    abstract public Iterable<Kunde> getKunden();
    abstract public void addKunde(Kunde kunde);
    abstract public Optional<Kunde> getKunde(UUID id);
    abstract public Optional<Kunde> getKunde(String email);
    abstract public Zahlungsvorgang getZahlungsvorgang(UUID id);
    abstract public Optional<Bestellung> getBestellungen(Kunde kunde);
    abstract public Iterable<Zahlungsvorgang> getZahlungsvorgaenge();
    abstract public void addZahlungsVorgang(Zahlungsvorgang zahlungsvorgang);
    
    
}