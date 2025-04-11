package de.nyg.domain.asegetraenke.valueobjects;

import de.nyg.domain.asegetraenke.entities.Produkt;

public class BestellungProdukt {
    Produkt produkt;
    Iterable<Pfandwert> pfand;
    Preis preis;
    int menge;

    public BestellungProdukt(Produkt produkt, Preis preis, int menge){
        this.menge = menge;
        this.preis = preis;
        this.produkt = produkt;
        pfand = produkt.getPfandwert();
    }

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
