package com.asegetraenke.aggregate;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.asegetraenke.GetraenkeUsecases;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;

public class ProductInformation {
    public final Produkt produkt;
    public final ArrayList<Pfandwert> pfandwerte = new ArrayList<>();
    public final ArrayList<Preis> preise = new ArrayList<>();
    public final int ordered;
    public final int inStock;

    public ProductInformation(Produkt produkt, GetraenkeUsecases gusecases){
        this.produkt = produkt;
        for(Pfandwert p : produkt.getPfandwert()){
            pfandwerte.add(p);
        }
        preise.addAll(StreamSupport.stream(gusecases.getPriceHistoryForProdukt(produkt).spliterator(), false).sorted((p1,p2) -> p1.getCreationDate().compareTo(p2.getCreationDate())).collect(Collectors.toList()));
        ordered = gusecases.getOrderedAmountForProdukt(produkt);
        inStock = gusecases.getStockAmountForProdukt(produkt);
    }
}
