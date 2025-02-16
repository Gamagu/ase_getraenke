package com.example.entities;

import java.time.LocalDate;

import com.example.valueobjects.BestellungProdukt;

public class Bestellung {
    Kunde kunde;
    LocalDate timestamp;
    Iterable<BestellungProdukt> produkte;
}
