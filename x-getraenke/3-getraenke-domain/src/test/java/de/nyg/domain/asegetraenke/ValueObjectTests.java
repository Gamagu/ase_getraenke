package de.nyg.domain.asegetraenke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.BestellungProdukt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;

class ValueObjectTests {

@Test
    public void testBestellProduktCreation() {
        Produkt produkt = mock(Produkt.class);
        Preis preis = mock(Preis.class);
        BestellungProdukt bestellProdukt = new BestellungProdukt(produkt, preis, 2);

        assertNotNull(bestellProdukt);
        assertEquals(produkt, bestellProdukt.getProdukt());
        assertEquals(preis, bestellProdukt.getPreis());
        assertEquals(2, bestellProdukt.getMenge());
    }    
    
    @Test
    public void testPfandwertCreation() {
        Pfandwert pfandwert = new Pfandwert(1.5, "Glasflasche");

        assertNotNull(pfandwert);
        assertEquals(1.5, pfandwert.wert);
        assertEquals("Glasflasche", pfandwert.beschreibung);
    }
    
    
        @Test
        public void testPreisCreation() {
            Produkt produkt = mock(Produkt.class);
            Preis preis = new Preis(10.0, produkt);
    
            assertNotNull(preis);
            assertEquals(10.0, preis.getPrice());
            assertEquals(produkt, preis.getParentObject());
            assertNotNull(preis.getCreationDate());
        }
    
        @Test
        public void testPreisEquality() {
            Produkt produkt = mock(Produkt.class);
            Preis preis1 = new Preis(10.0, produkt);
            Preis preis2 = new Preis(10.0, produkt);
    
            assertNotEquals(preis1, preis2); // should fail because of the timedifference between the creation
        }
}
