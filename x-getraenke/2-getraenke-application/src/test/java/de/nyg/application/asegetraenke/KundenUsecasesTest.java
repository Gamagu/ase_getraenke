package de.nyg.application.asegetraenke;

import de.nyg.application.asegetraenke.builder.ProduktBuilder;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.entities.Zahlungsvorgang;
import de.nyg.domain.asegetraenke.repository.GetraenkeRepositoryMock;
import de.nyg.domain.asegetraenke.repository.RepositoryData;
import de.nyg.domain.asegetraenke.util.Triple;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KundenUsecasesTest {

    private GetraenkeRepositoryMock grepo;
    private KundenUsecases kundenUsecases;
    private GetraenkeUsecases gUsecasess;

    @BeforeEach
    void setUp() {
        grepo = new GetraenkeRepositoryMock(new RepositoryData());
        kundenUsecases = new KundenUsecases(grepo,grepo);
        gUsecasess = new GetraenkeUsecases(grepo, grepo);
    }


    @Test
    void testGetAllKundenAndAdd() {
        int c2 = 0;
        Iterable<Kunde> kundenBefore = grepo.getKunden();
        for (Kunde kunde : kundenBefore) {
            c2++;
        }
        grepo.addKunde(new Kunde("test@example.com", "John", "Doe"));
        Iterable<Kunde> result = kundenUsecases.getAllKunden();
        int c1 = 0;
        for (Kunde kunde : result) {
            c1++;
        }
        assert(c1-c2 == 1);
    }

    @Test
    void testSetName() {
        Kunde kunde = new Kunde("test@example.com", "John", "Doe");
        kundenUsecases.setName(kunde, "Jane", "Smith");
        assertEquals("Jane", kunde.getName());
        assertEquals("Smith", kunde.getNachname());
    }

    @Test
    void testGetKunde() {
        Kunde kunde = new Kunde( "John", "Doe","test@example.com");
        grepo.addKunde(kunde);
        Optional<Kunde> result = kundenUsecases.getKunde("test@example.com");
        assertTrue(result.isPresent());
        assertEquals(kunde, result.get());
    }

    @Test
    void testGetKundenBalance() throws Exception{
        Kunde kunde = new Kunde( "John", "Doe","test@example.com");
        Produkt produkt = new Produkt("TestP", "TestB", "TestK");
        gUsecasess.addProdukt(produkt, 10);
        
        List<Triple<Produkt, Integer, Double>> produkte = new ArrayList<>(); // Retrieve a product, wrap in Bestellprodukt, and add to a list
        produkte.add(new Triple<Produkt, Integer, Double>(produkt, 1, 10.0));
        gUsecasess.addBestellung(kunde, produkte);
        double balance = kundenUsecases.getKundenBalance(kunde);
        assertEquals(10.0, balance);
        kundenUsecases.addZahlungsvorgang(kunde, "Test", balance, LocalDateTime.now());
        double balance2 = kundenUsecases.getKundenBalance(kunde);
        assertEquals(0.0, balance2);
    }

    @Test
    void testAddZahlungsvorgang() {
        Kunde kunde = new Kunde("test@example.com", "John", "Doe");
        LocalDateTime date = LocalDateTime.now();
        Zahlungsvorgang zahlung = kundenUsecases.addZahlungsvorgang(kunde, "Credit Card", 100.0, date);
        grepo.addZahlungsVorgang(zahlung);
        assertEquals(kunde, zahlung.getKunde());
        assertEquals(100.0, zahlung.getBetrag());
        assertEquals("Credit Card", zahlung.getZahlungsWeg());
        assertEquals(date, zahlung.getDate());
    }
}
