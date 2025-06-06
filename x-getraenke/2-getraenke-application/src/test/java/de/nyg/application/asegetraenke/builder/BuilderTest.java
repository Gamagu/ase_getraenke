package de.nyg.application.asegetraenke.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;

public class BuilderTest {
    @Test
    void testBuildValidKunde() {
        Kunde kunde = new KundeBuilder()
                .withName("Max")
                .withNachname("Mustermann")
                .withEmail("max.mustermann@example.com")
                .build();

        assertNotNull(kunde);
        assertEquals("Max", kunde.getName());
        assertEquals("Mustermann", kunde.getNachname());
        assertEquals("max.mustermann@example.com", kunde.getMail());
    }

    @Test
    void testBuildThrowsExceptionWhenFieldsAreNull() {
        KundeBuilder builder = new KundeBuilder();

        Exception exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Name, Nachname und E-Mail dürfen nicht null sein", exception.getMessage());
    }

    @Test
    void testBuildThrowsExceptionWhenEmailIsNull() {
        KundeBuilder builder = new KundeBuilder()
                .withName("Max")
                .withNachname("Mustermann");

        Exception exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Name, Nachname und E-Mail dürfen nicht null sein", exception.getMessage());
    }
    @Test
    void testBuildValidProdukt() {
        Produkt produkt = new ProduktBuilder()
                .withName("Cola")
                .withBeschreibung("Erfrischungsgetränk")
                .withKategorie("Softdrink")
                .build();

        assertNotNull(produkt);
        assertEquals("Cola", produkt.getName());
        assertEquals("Erfrischungsgetränk", produkt.getBeschreibung());
        assertEquals("Softdrink", produkt.getKategorie());
    }

    @Test
    void testBuildThrowsExceptionWhenFieldsAreNullInProduktBuilder() {
        ProduktBuilder builder = new ProduktBuilder();

        Exception exception = assertThrows(IllegalStateException.class, builder::build);
        assertEquals("Name, Beschreibung und Kategorie müssen gesetzt sein.", exception.getMessage());
    }

    @Test
    void testBuildWithPreisAndPfandwerte() {
        GetraenkeRepository repo = mock(GetraenkeRepository.class);
        Preis preis = mock(Preis.class);
        Pfandwert pfandwert = mock(Pfandwert.class);

        Produkt produkt = new ProduktBuilder()
                .withName("Cola")
                .withBeschreibung("Erfrischungsgetränk")
                .withKategorie("Softdrink")
                .withPreis(preis)
                .withPfandwert(pfandwert)
                .build(repo);

        assertNotNull(produkt);
        assertEquals("Cola", produkt.getName());
        assertEquals("Erfrischungsgetränk", produkt.getBeschreibung());
        assertEquals("Softdrink", produkt.getKategorie());
        // Additional assertions can be added if Produkt exposes Preis and Pfandwerte
    }
}
