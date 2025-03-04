package com.asegetraenke.usecases;

import java.util.UUID;

import com.asegetraenke.entities.Kunde;


public abstract class KundeUsecases {
    abstract void createKunde(String name, String nachname);
    abstract Kunde getKunde(UUID uuid);
    abstract Iterable<Kunde> getAllKunden();
    abstract void setName(UUID uuid, String name, String nachname);
}
