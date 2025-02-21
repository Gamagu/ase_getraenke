package com.asegetraenke.entities;

import java.time.LocalDateTime;

import com.asegetraenke.util.EntityWrapper;


public class Zahlungsvorgang extends EntityWrapper<Zahlungsvorgang>{
    Kunde kunde;
    String zahlungsweg;
    double betrag;
    LocalDateTime date;

    public Zahlungsvorgang(Kunde kunde, String zahlungsweg, double betrag, LocalDateTime datetime){
        super();
        this.kunde = kunde;
        this.zahlungsweg = zahlungsweg;
        this.betrag = betrag;
        this.date = datetime;
    }

    public Kunde getKunde(){
        return kunde;
    }
}
