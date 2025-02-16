package com.example.entities;

import java.util.UUID;
import com.example.valueobjects.Preis;
public class Produkt implements Preis.Priced{
    UUID uuid;
    String name;
    String beschreibung;
    String kategorie;
    Preis preis;

    Iterable<Pfandwert> pfand;
}
