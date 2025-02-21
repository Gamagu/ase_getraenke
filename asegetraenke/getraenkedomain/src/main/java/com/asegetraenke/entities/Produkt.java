package com.asegetraenke.entities;
import java.util.ArrayList;
import java.util.stream.StreamSupport;

import com.asegetraenke.repositories.GetraenkeRepository;
import com.asegetraenke.util.EntityWrapper;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;


public class Produkt extends EntityWrapper<Produkt> implements Preis.Priced{
    private String name;
    private String beschreibung;
    private String kategorie;
    private Preis preis;
    private Iterable<Pfandwert> pfand;

    public Produkt(String name, String beschreibung, String kategory){
        super();
        this.kategorie = kategory;
        this.beschreibung = beschreibung;
        this.name = name;
    }

    public Preis getCurrentPreis(){
        return preis;
    }

    public Iterable<Pfandwert> getPfandwert(){
        ArrayList<Pfandwert> ret = new ArrayList<>();
        for(Pfandwert r : pfand){
            ret.add(r);
        }
        return ret;
    }

    public void setPreis(Preis preis, GetraenkeRepository repo){
        this.preis = preis;
        repo.addPrice(preis);
    }

    public void setPfandAssignment(Iterable<Pfandwert> newPfand, GetraenkeRepository repo){
        Iterable<Pfandwert> savedPfandwerte =  repo.getPfandwerte();
        for(Pfandwert wert : newPfand){
            assert(StreamSupport.stream(savedPfandwerte.spliterator(), false).anyMatch(t-> t.equals(wert)));
        }
        this.pfand = newPfand;
    }

    public double getPfandSum(){
        double ret = 0;
        for (Pfandwert p : pfand ){
            ret += p.wert;
        }
        return ret;
    }

    @Override
    public String toString() {
        return String.format("%s (%s,  %s) %.2f", name, kategorie, beschreibung, preis.getPrice());
    }
}
