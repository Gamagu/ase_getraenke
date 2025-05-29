package de.nyg.adapters.asegetraenke.console;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleError;
import de.nyg.adapters.asegetraenke.console.utils.ConsolePrinter;
import de.nyg.adapters.asegetraenke.console.utils.ConsoleReader;
import de.nyg.adapters.asegetraenke.console.utils.EntityPicker;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;
import de.nyg.domain.asegetraenke.valueobjects.Preis;


public class GetraenkeInputHandlerTest {

    @Mock private GetraenkeUsecases getraenkeUsecases;
    @Mock private KundenUsecases kundenUsecases;
    @Mock private ConsoleError consoleError;
    @Mock private CommandRegistrar commandRegistrar;
    @Mock private EntityPicker<Kunde> kundenPicker;
    @Mock private EntityPicker<Produkt> produktPicker;
    @Mock private EntityPicker<Pfandwert> pfandwertPicker;
    @Mock private ConsoleReader consoleReader;
    @Mock private ConsolePrinter consolePrinter;

    private GetraenkeInputHandler handler;

    

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        handler = new GetraenkeInputHandler(getraenkeUsecases, kundenUsecases, consoleError,
                commandRegistrar, kundenPicker, produktPicker, pfandwertPicker,
                consoleReader, consolePrinter);
    }

    @Test
    public void testHandleAddPfandWertInput() throws Exception {
        when(consoleReader.readStringInputWithPrompt("Description: ")).thenReturn("Test");
        when(consoleReader.readDoubleInputWithPrompt("Value: ")).thenReturn(1.5);
        when(consoleReader.acceptInput()).thenReturn(true);

        handler.handleAddPfandWertInput();

        verify(getraenkeUsecases).addPfandWert("Test", 1.5);
    }

    @Test
    public void testHandleSetPfandwertInput() throws Exception {
        Pfandwert pfandwert = mock(Pfandwert.class);
        when(getraenkeUsecases.getAllPfandwerte()).thenReturn(List.of(pfandwert));
        when(pfandwertPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(pfandwert));
        when(consoleReader.readDoubleInputWithPrompt(anyString())).thenReturn(2.0);
        when(consoleReader.acceptInput()).thenReturn(true);

        handler.handleSetPfandwertInput();

        verify(getraenkeUsecases).setPfandwert(pfandwert, 2.0);
    }

    @Test
    public void testHandleGetAllPfandwerteInput_emptyList() {
        when(getraenkeUsecases.getAllPfandwerte()).thenReturn(List.of());

        handler.handleGetAllPfandwerteInput();

        verify(consoleError).errorNoProdukt();
    }

    @Test
    public void testHandleGetAllPfandwerteInput_nonEmptyList() {
        Pfandwert pf1 = mock(Pfandwert.class);
        Pfandwert pf2 = mock(Pfandwert.class);
        when(getraenkeUsecases.getAllPfandwerte()).thenReturn(List.of(pf1, pf2));

        handler.handleGetAllPfandwerteInput();

        verify(consolePrinter).printPfandwertWithNumber(pf1, 1);
        verify(consolePrinter).printPfandwertWithNumber(pf2, 2);
    }

    @Test
    public void testHandleGetPfandWertInput_valid() {
        String uuidStr = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(uuidStr);
        Pfandwert pfandwert = mock(Pfandwert.class);
        when(consoleReader.readStringInputWithPrompt(anyString())).thenReturn(uuidStr);
        when(getraenkeUsecases.getPfandWert(uuid)).thenReturn(Optional.of(pfandwert));

        handler.handleGetPfandWertInput();

        verify(consolePrinter).printPfandwertWithNumber(pfandwert, 1);
    }

    @Test
    public void testHandleGetAllProductsInput_empty() {
        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of());

        handler.handleGetAllProductsInput();

        verify(consoleError).errorNoProdukt();
    }

    @Test
    public void testHandleSetPriceForProduktInput() {
        Produkt produkt = mock(Produkt.class);
        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of(produkt));
        when(produktPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(produkt));
        when(consoleReader.readIntInputWithPrompt(anyString())).thenReturn(42);
        when(consoleReader.acceptInput()).thenReturn(true);

        handler.handleSetPriceForProduktInput();

        verify(getraenkeUsecases).setPriceForProdukt(produkt, 42);
    }

    @Test
    public void testHandleAddProduktInput() throws Exception {
        when(consoleReader.readStringInputWithPrompt("Name: ")).thenReturn("Name");
        when(consoleReader.readStringInputWithPrompt("Beschreibung: ")).thenReturn("Beschreibung");
        when(consoleReader.readStringInputWithPrompt("Kategorie: ")).thenReturn("Kategorie");
        when(consoleReader.readDoubleInputWithPrompt("Preis: ")).thenReturn(10.0);
        when(consoleReader.acceptInput()).thenReturn(true);

        handler.handleAddProduktInput();

        ArgumentCaptor<Produkt> produktCaptor = ArgumentCaptor.forClass(Produkt.class);
        ArgumentCaptor<Double> preisCaptor = ArgumentCaptor.forClass(Double.class);

        verify(getraenkeUsecases).addProdukt(produktCaptor.capture(), preisCaptor.capture());

        Produkt produkt = produktCaptor.getValue();
        double preis = preisCaptor.getValue();

        assertEquals(10.0, preis);
        assertEquals("Name", produkt.getName());
        assertEquals("Beschreibung", produkt.getBeschreibung());
        assertEquals("Kategorie",produkt.getKategorie());
    }


    @Test
    public void testHandleSetPfandwertProduktInput_successful() throws Exception {
        Pfandwert pfandwert = mock(Pfandwert.class);
        Produkt produkt = mock(Produkt.class);

        when(getraenkeUsecases.getAllPfandwerte()).thenReturn(List.of(pfandwert));
        when(pfandwertPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(pfandwert));

        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of(produkt));
        when(produktPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(produkt));

        handler.handleSetPfandwertProduktInput();

        verify(getraenkeUsecases).setPfandwertProdukt(eq(produkt), argThat(p -> p.iterator().next() == pfandwert));
    }

    @Test
    public void testHandleSetPfandwertProduktInput_noPfandwertSelected() {
        when(getraenkeUsecases.getAllPfandwerte()).thenReturn(List.of());
        when(pfandwertPicker.pickOneFromList(any(), any())).thenReturn(Optional.empty());

        handler.handleSetPfandwertProduktInput();

        verify(consoleError).errorNoPfandWert();
        verifyNoInteractions(produktPicker);
    }

    @Test
    public void testHandleAddBestellungInput_simple() throws Exception {
        Kunde kunde = mock(Kunde.class);
        Produkt produkt = mock(Produkt.class);
    
        when(kundenUsecases.getAllKunden()).thenReturn(List.of(kunde));
        when(kundenPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(kunde));
    
        when(consoleReader.readIntInputWithPrompt("How many Products do you want to add: "))
            .thenReturn(1);
    
        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of(produkt));
        when(produktPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(produkt));
        when(consoleReader.readIntInputWithPrompt("Wie viele Produkte: ")).thenReturn(2);
        when(consoleReader.readDoubleInputWithPrompt("Wie viel kostetet das Produkt: ")).thenReturn(4.99);
    
        handler.handleAddBestellungInput();
    
        verify(getraenkeUsecases).addBestellung(eq(kunde), any());
    }
    

    @Test
    public void testHandleAddBestellungInput_noCustomerSelected() {
        when(kundenUsecases.getAllKunden()).thenReturn(List.of());
        when(kundenPicker.pickOneFromList(any(), any())).thenReturn(Optional.empty());

        handler.handleAddBestellungInput();

        verify(consoleError).errorNoKunden();
        verifyNoMoreInteractions(getraenkeUsecases);
    }

    @Test
    public void testHandleAddZahlungsvorgangInput_successful() {
        Kunde kunde = mock(Kunde.class);
        when(kundenUsecases.getAllKunden()).thenReturn(List.of(kunde));
        when(kundenPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(kunde));
        when(consoleReader.readStringInputWithPrompt("Zahlungsweg: ")).thenReturn("bar");
        when(consoleReader.readDoubleInputWithPrompt("Betrag: ")).thenReturn(10.0);

        handler.handleAddZahlungsvorgangInput();

        verify(kundenUsecases).addZahlungsvorgang(eq(kunde), eq("bar"), eq(10.0), any(LocalDateTime.class));
    }

    @Test
    public void testHandleAddZahlungsvorgangInput_noCustomerSelected() {
        when(kundenUsecases.getAllKunden()).thenReturn(List.of());
        when(kundenPicker.pickOneFromList(any(), any())).thenReturn(Optional.empty());
    
        handler.handleAddZahlungsvorgangInput();
    
        verify(kundenUsecases).getAllKunden();
        verify(consoleError).errorNoKunden();
        verifyNoMoreInteractions(kundenUsecases);
    }
    
    @Test
    public void testHandleGetPriceForProduktInput_successful() {
        Produkt produkt = mock(Produkt.class);
        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of(produkt));
        when(produktPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(produkt));
        when(getraenkeUsecases.getPriceForProdukt(produkt)).thenReturn(mock(Preis.class));

        handler.handleGetPriceForProduktInput();

        verify(getraenkeUsecases).getPriceForProdukt(produkt);
    }


    @Test
    public void testHandleGetPriceHistoryForProduktInput_successful() {
        Produkt produkt = mock(Produkt.class);
        Preis preis1 = mock(Preis.class);
        Preis preis2 = mock(Preis.class);

        when(getraenkeUsecases.getAllProducts()).thenReturn(List.of(produkt));
        when(produktPicker.pickOneFromList(any(), any())).thenReturn(Optional.of(produkt));
        when(getraenkeUsecases.getPriceHistoryForProdukt(produkt)).thenReturn(List.of(preis1, preis2));

        handler.handleGetPriceHistoryForProduktInput();

        verify(getraenkeUsecases).getPriceHistoryForProdukt(produkt);
    }

    @Test
    public void testHandleGetProductInput_successful() throws Exception {
        String uuidStr = "123e4567-e89b-12d3-a456-426614174000";
        UUID uuid = UUID.fromString(uuidStr);
        Produkt produkt = mock(Produkt.class);

        when(consoleReader.readStringInputWithPrompt("UUID for product: ")).thenReturn(uuidStr);
        when(getraenkeUsecases.getProduct(uuid)).thenReturn(Optional.of(produkt));

        handler.handleGetProductInput();

        verify(getraenkeUsecases).getProduct(uuid);
    }






}
