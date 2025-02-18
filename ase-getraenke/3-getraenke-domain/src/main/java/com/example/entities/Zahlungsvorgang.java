package com.example.entities;

import java.time.LocalDateTime;

import com.example.util.EntityWrapper;

public class Zahlungsvorgang extends EntityWrapper<Zahlungsvorgang>{
    Kunde kunde;
    String zahlungsweg;
    double betrag;
    LocalDateTime date;

    public Zahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag, LocalDateTime datetime){
        this.kunde = kunde;
        this.zahlungsweg = zahlungsweg;
        this.betrag = betrag;
        this.date = datetime;
    }
}
