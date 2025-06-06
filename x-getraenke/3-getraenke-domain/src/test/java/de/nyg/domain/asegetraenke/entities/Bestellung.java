package de.nyg.domain.asegetraenke.entities;
import de.nyg.domain.asegetraenke.repository.GetraenkeRepositoryMock;
import de.nyg.domain.asegetraenke.repository.RepositoryData;
import de.nyg.domain.asegetraenke.valueobjects.BestellungProdukt;
import de.nyg.domain.asegetraenke.valueobjects.Preis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BestellungTest {

    private GetraenkeRepositoryMock repositoryMock;
    private RepositoryData repositoryData;

    @BeforeEach
    void setUp() {
        repositoryData = new RepositoryData();
        repositoryMock = new GetraenkeRepositoryMock(repositoryData);
    }

    @Test
    void testCreateAndRetrieveBestellung() {
        Kunde kunde = repositoryData.testKunde;
        Produkt produkt = repositoryData.produkte.get(0);
        Preis preis = new Preis(10.0, produkt);
        BestellungProdukt bestellungProdukt = new BestellungProdukt(produkt, preis, 2);

        Bestellung bestellung = new Bestellung(kunde, LocalDateTime.now(), List.of(bestellungProdukt));
        repositoryMock.addBestellung(bestellung);

        Optional<Bestellung> retrievedBestellung = repositoryMock.getBestellungen(kunde);
        assertTrue(retrievedBestellung.isPresent());
    }

    @Test
    void testBestellungProperties() {
        Kunde kunde = repositoryData.testKunde;
        Produkt produkt = repositoryData.produkte.get(0);
        Preis preis = new Preis(15.0, produkt);
        BestellungProdukt bestellungProdukt = new BestellungProdukt(produkt, preis, 3);

        Bestellung bestellung = new Bestellung(kunde, LocalDateTime.now(), List.of(bestellungProdukt));

        assertEquals(kunde, bestellung.getKunde());
        assertEquals(1, bestellung.getProdukte().count());
        assertEquals(bestellungProdukt, bestellung.getProdukte().findAny().get());
    }

    @Test
    void testRetrieveBestellungByKunde() {
        Kunde kunde = new Kunde("Alice", "Smith", "alice.smith@example.com");
        repositoryMock.addKunde(kunde);

        Produkt produkt = new Produkt("Water", "Beverage", "67890");
        repositoryMock.addProdukt(produkt);

        Preis preis = new Preis(5.0, produkt);
        BestellungProdukt bestellungProdukt = new BestellungProdukt(produkt, preis, 1);

        Bestellung bestellung = new Bestellung(kunde, LocalDateTime.now(), List.of(bestellungProdukt));
        repositoryMock.addBestellung(bestellung);

        Optional<Bestellung> retrievedBestellung = repositoryMock.getBestellungen(kunde);
        assertTrue(retrievedBestellung.isPresent());
        assertEquals(bestellung, retrievedBestellung.get());
    }
}
