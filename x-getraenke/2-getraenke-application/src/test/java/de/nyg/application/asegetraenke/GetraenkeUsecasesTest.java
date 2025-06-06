package de.nyg.application.asegetraenke;

import de.nyg.domain.asegetraenke.entities.*;
import de.nyg.domain.asegetraenke.repositories.CustomerRepository;
import de.nyg.domain.asegetraenke.repositories.GetraenkeRepository;
import de.nyg.domain.asegetraenke.util.Pair;
import de.nyg.domain.asegetraenke.util.Triple;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GetraenkeUsecasesTest {
     private GetraenkeRepository grepo;
    private CustomerRepository crepo;
    private GetraenkeUsecases usecases;

    @BeforeEach
    void setUp() {
        grepo = mock(GetraenkeRepository.class);
        crepo = mock(CustomerRepository.class);
        usecases = new GetraenkeUsecases(grepo, crepo);
    }

    @Test
    void testAcceptLieferung() throws Exception {
        Produkt produkt = mock(Produkt.class);
        Pair<Produkt, Integer> pair = new Pair<>(produkt, 10);
        List<Pair<Produkt, Integer>> produkte = List.of(pair);

        usecases.acceptLieferung(produkte, LocalDateTime.now());

        verify(grepo, times(1)).addLieferung(any(Lieferung.class));
    }

    @Test
    void testAddPfandWert() throws Exception {
        String beschreibung = "Flasche";
        double wert = 0.25;

        usecases.addPfandWert(beschreibung, wert);

        ArgumentCaptor<Pfandwert> captor = ArgumentCaptor.forClass(Pfandwert.class);
        verify(grepo, times(1)).addPfandwert(captor.capture());
        assertEquals(beschreibung, captor.getValue().beschreibung);
        assertEquals(wert, captor.getValue().wert);
    }

    @Test
    void testSetPfandwert() throws Exception {
        Pfandwert oldPfandwert = new Pfandwert(0.25, "Flasche");
        double newValue = 0.5;

        Pfandwert newPfandwert = usecases.setPfandwert(oldPfandwert, newValue);

        assertEquals(newValue, newPfandwert.wert);
        assertEquals(oldPfandwert.beschreibung, newPfandwert.beschreibung);
        verify(grepo, times(1)).addPfandwert(newPfandwert);
    }

    @Test
    void testSetPfandwertProdukt() throws Exception {
        Produkt produkt = mock(Produkt.class);
        List<Pfandwert> pfandwerte = List.of(new Pfandwert(0.25, "Flasche"));

        usecases.setPfandwertProdukt(produkt, pfandwerte);

        verify(produkt, times(1)).setPfandAssignment(pfandwerte, grepo);
    }

    @Test
    void testGetAllPfandwerte() {
        Pfandwert pfandwert = new Pfandwert(0.25, "Flasche");
        when(grepo.getPfandwerte()).thenReturn(List.of(pfandwert));

        Iterable<Pfandwert> result = usecases.getAllPfandwerte();

        assertTrue(result.iterator().hasNext());
        assertEquals(pfandwert, result.iterator().next());
    }

    @Test
    void testGetPfandWert() {
        UUID id = UUID.randomUUID();
        Pfandwert pfandwert = new Pfandwert(0.25, "Flasche");
        when(grepo.getPfandwert(id)).thenReturn(Optional.of(pfandwert));

        Optional<Pfandwert> result = usecases.getPfandWert(id);

        assertTrue(result.isPresent());
        assertEquals(pfandwert, result.get());
    }

    @Test
    void testGetAllProducts() {
        Produkt produkt = mock(Produkt.class);
        when(grepo.getProdukte()).thenReturn(List.of(produkt));

        Iterable<Produkt> result = usecases.getAllProducts();

        assertTrue(result.iterator().hasNext());
        assertEquals(produkt, result.iterator().next());
    }

    @Test
    void testSetPriceForProdukt() {
        Produkt produkt = mock(Produkt.class);
        double preis = 10.0;

        double result = usecases.setPriceForProdukt(produkt, preis);

        assertEquals(preis, result);
        verify(produkt, times(1)).setPreis(any(Preis.class), eq(grepo));
    }

    @Test
    void testGetStockAmountForProdukt() {
        Produkt produkt = mock(Produkt.class);
        when(grepo.getLieferungen(produkt)).thenReturn(Collections.emptyList());
        when(grepo.getBestellungen()).thenReturn(Collections.emptyList());

        int result = usecases.getStockAmountForProdukt(produkt);

        assertEquals(0, result);
    }

    @Test
    void testAddProdukt() throws Exception {
        Produkt produkt = mock(Produkt.class);
        double preis = 10.0;

        usecases.addProdukt(produkt, preis);

        verify(produkt, times(1)).setPreis(any(Preis.class), eq(grepo));
    }

    @Test
    void testAddBestellung() throws Exception {
        Kunde kunde = mock(Kunde.class);
        Produkt produkt = mock(Produkt.class);
        Triple<Produkt, Integer, Double> triple = new Triple<>(produkt, 10, 5.0);
        List<Triple<Produkt, Integer, Double>> produkte = List.of(triple);

        Bestellung bestellung = usecases.addBestellung(kunde, produkte);

        assertNotNull(bestellung);
        assertEquals(kunde, bestellung.getKunde());
        assertFalse(bestellung.getProdukte().count() == 0);
    }
}
