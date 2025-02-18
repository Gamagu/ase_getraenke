package com.example.valueobjects;

import com.example.entities.Produkt;

public class BestellungProdukt {
    Produkt produkt;
    Preis preis;
    int menge;

    public Produkt getProdukt(){
        return produkt;
    }

    public int getMenge(){
        return menge;
    }

    public Preis getPreis(){
        return preis;
    }
}
