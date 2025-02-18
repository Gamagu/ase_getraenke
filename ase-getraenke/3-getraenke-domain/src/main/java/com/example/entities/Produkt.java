package com.example.entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.StreamSupport;

import com.example.repositories.GetraenkeRepository;
import com.example.util.EntityWrapper;
import com.example.valueobjects.Pfandwert;
import com.example.valueobjects.Preis;
public class Produkt extends EntityWrapper<Produkt> implements Preis.Priced{
    private String name;
    private String beschreibung;
    private String kategorie;
    private Preis preis;
    private Iterable<Pfandwert> pfand;

    public Produkt(String name, String beschreibung, String kategory){
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

    @Override
    public String toString() {
        return String.format("%s (%s,  %s) %.2f", name, kategorie, beschreibung, preis.getPrice());
    }
}
