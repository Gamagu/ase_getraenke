package com.example.entities;

import java.util.concurrent.atomic.AtomicInteger;

public class Lieferung {
    public final static AtomicInteger counter = new AtomicInteger();
    int lieferId;
    Produkt produkt;
    int menge;

    public Lieferung(Produkt produkt, int menge, int lieferId){
        this.menge = menge;
        this.produkt = produkt;
        this.lieferId = lieferId;
    }

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
