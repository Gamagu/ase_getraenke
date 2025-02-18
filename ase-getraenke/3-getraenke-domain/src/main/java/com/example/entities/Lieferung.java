package com.example.entities;

public class Lieferung {
    int lieferId;
    Produkt produkt;
    int menge;

    public Produkt getProdukt(){
        return produkt;
    }

    public int getLieferId(){
        return lieferId;
    }

    public int getMenge(){
        return menge;
    }
    
}
