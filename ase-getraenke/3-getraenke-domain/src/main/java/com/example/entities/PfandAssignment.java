package com.example.entities;

import com.example.valueobjects.Pfandwert;

public class PfandAssignment {
    private Produkt produkt;
    private Iterable<Pfandwert> pfand;

    public PfandAssignment(Produkt produkt, Iterable<Pfandwert> pfand){
        this.produkt = produkt;
        this.pfand = pfand;
    }

    public void setPfand(Iterable<Pfandwert> newPfand){
        this.pfand = newPfand;
    }
}
