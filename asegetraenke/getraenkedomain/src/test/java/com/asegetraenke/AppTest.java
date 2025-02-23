package com.asegetraenke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.asegetraenke.entities.Bestellung;
import com.asegetraenke.entities.Kunde;
import com.asegetraenke.entities.Produkt;
import com.asegetraenke.repository.GetraenkeRepositoryMock;
import com.asegetraenke.repository.RepositoryData;
import com.asegetraenke.valueobjects.BestellungProdukt;
import com.asegetraenke.valueobjects.Pfandwert;
import com.asegetraenke.valueobjects.Preis;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    GetraenkeRepositoryMock repo = new  GetraenkeRepositoryMock(new RepositoryData());
    Kunde k = repo.getKunde("Ben@Ben.de").get();
    Produkt p = repo.getProdukte().iterator().next();

    @Test
    public void CreateBestellung() {
        Produkt p = new Produkt("TestP", "TestP", "TestP");
        ArrayList<Pfandwert> pfandwerte = new ArrayList<>();
        pfandwerte.add(new Pfandwert(5, "Test"));
        pfandwerte.add(new Pfandwert(2.5, "Test"));
        for(Pfandwert pfandwert : pfandwerte){
            GetraenkeRepositoryMock.getGetraenkeMockRepo().addPfandwert(pfandwert);
        }   
        p.setPfandAssignment(pfandwerte, GetraenkeRepositoryMock.getGetraenkeMockRepo());
        ArrayList<BestellungProdukt> b = new ArrayList<>();
        Preis preis = new Preis(10.0, p);
        b.add(new BestellungProdukt(p, preis, 1));
        Bestellung b1 = new Bestellung(k, LocalDateTime.now(), b);
        assertEquals(b1.getSumOfOrder(), 17.5);
        assertEquals(b1.getNumber(), 1);
    }

    @Test
    public void setPrice() {
        Preis newPreis = new Preis(200, p);

        Preis oldPrice = repo.getPreisForProdukt(p).iterator().next();
        assertEquals(10, oldPrice.getPrice());
        p.setPreis(newPreis, repo);
        assertEquals(200, p.getCurrentPreis().getPrice());
    }
}
