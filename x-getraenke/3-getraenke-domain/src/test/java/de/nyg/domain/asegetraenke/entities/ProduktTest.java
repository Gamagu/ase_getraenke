package de.nyg.domain.asegetraenke.entities;

import de.nyg.domain.asegetraenke.repository.GetraenkeRepositoryMock;
import de.nyg.domain.asegetraenke.repository.RepositoryData;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProduktTest {

    private GetraenkeRepositoryMock repositoryMock;
    private RepositoryData repositoryData;

    @BeforeEach
    void setUp() {
        repositoryData = new RepositoryData();
        repositoryMock = new GetraenkeRepositoryMock(repositoryData);
    }

    @Test
    void testSetAndGetPreis() {
        Produkt produkt = new Produkt("Cola", "Soft Drink", "Beverage");
        Preis preis = new Preis(1.5, produkt);
        repositoryMock.addPreis(preis);
        repositoryMock.addProdukt(produkt);
        produkt.setPreis(preis, repositoryMock);

        assertEquals(preis, produkt.getCurrentPreis());
    }

    @Test
    void testSetAndGetPfand() {
        Produkt produkt = new Produkt("Cola", "Soft Drink", "Beverage");
        Pfandwert pfandwert1 = new Pfandwert(0.25, "Bottle");
        Pfandwert pfandwert2 = new Pfandwert(0.15, "Can");
        repositoryMock.addPfandwert(pfandwert1);
        repositoryMock.addPfandwert(pfandwert2);

        produkt.setPfandAssignment(List.of(pfandwert1, pfandwert2), repositoryMock);

        Iterable<Pfandwert> pfandwerte = produkt.getPfandwert();
        assertTrue(pfandwerte.iterator().hasNext());
        assertEquals(2, ((List<Pfandwert>) pfandwerte).size());
    }

    @Test
    void testGetPfandSum() {
        Produkt produkt = new Produkt("Cola", "Soft Drink", "Beverage");
        Pfandwert pfandwert1 = new Pfandwert(0.25, "Bottle");
        Pfandwert pfandwert2 = new Pfandwert(0.15, "Can");

        repositoryMock.addPfandwert(pfandwert1);
        repositoryMock.addPfandwert(pfandwert2);
        repositoryMock.addProdukt(produkt);
        produkt.setPfandAssignment(List.of(pfandwert1, pfandwert2), repositoryMock);

        double pfandSum = produkt.getPfandSum();
        assertEquals(0.40, pfandSum, 0.01);
    }

    @Test
    void testToString() {
        Produkt produkt = new Produkt("Cola", "Soft Drink", "Beverage");
        repositoryMock.addProdukt(produkt);
        Preis preis = new Preis(1.5, produkt);

        repositoryMock.addPreis(preis);
        produkt.setPreis(preis, repositoryMock);

        assertEquals(repositoryMock.getProdukt(produkt.getId()).get().getCurrentPreis(), preis);
    }
}
