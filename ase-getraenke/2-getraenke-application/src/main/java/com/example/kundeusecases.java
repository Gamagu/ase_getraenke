package com.example;

import java.util.Optional;
import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;

public abstract class kundeusecases {
    public abstract void createKunde(String name, String nachname, String eMail);
    // TODO sollte optional sein 
    public abstract Optional<Iterable<Kunde>> getAllKunden();
    public abstract void setName(Kunde uuid, String name, String nachname);
    // TODO sollte optional sein 
    public abstract Optional<Kunde> getKunde(String eMail);
    public abstract double getKundenBalance(Kunde kunde);

    public abstract Iterable<Bestellung> getAllBestellungen(Kunde kunde);
}
