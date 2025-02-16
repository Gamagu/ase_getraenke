package com.example;

import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;

public abstract class kundeusecases {
    abstract void createKunde(String name, String nachname);
    abstract Iterable<Kunde> getAllKunden();
    abstract void setName(Kunde uuid, String name, String nachname);
    abstract Kunde getKunde(UUID uuid);
    abstract double getKundenBalance(Kunde kunde);

    abstract Iterable<Bestellung> getAllBestellungen(Kunde kunde);
}
