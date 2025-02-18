package com.example;

import java.util.UUID;

import com.example.entities.Bestellung;
import com.example.entities.Kunde;

public abstract class kundeusecases {
    public abstract void createKunde(String name, String nachname, String eMail);
    public abstract Iterable<Kunde> getAllKunden();
    public abstract void setName(Kunde uuid, String name, String nachname);
    public abstract Kunde getKunde(UUID uuid);
    public abstract double getKundenBalance(Kunde kunde);

    public abstract Iterable<Bestellung> getAllBestellungen(Kunde kunde);
}
