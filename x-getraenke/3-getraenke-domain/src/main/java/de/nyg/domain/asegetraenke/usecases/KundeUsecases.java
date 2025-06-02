package de.nyg.domain.asegetraenke.usecases;

import java.util.UUID;

import de.nyg.domain.asegetraenke.entities.Kunde;


public abstract class KundeUsecases {
    abstract public void createKunde(String name, String nachname);
    abstract public Kunde getKunde(UUID uuid);
    abstract public Iterable<Kunde> getAllKunden();
    abstract public void setName(UUID uuid, String name, String nachname);
}
