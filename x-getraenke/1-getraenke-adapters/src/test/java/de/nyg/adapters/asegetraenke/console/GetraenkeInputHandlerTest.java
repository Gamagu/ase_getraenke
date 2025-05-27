package de.nyg.adapters.asegetraenke.console;

import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nyg.application.asegetraenke.GetraenkeUsecases;
import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.Utils.ConsolePrinter;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleReader;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleError;
import de.nyg.adapters.asegetraenke.console.Utils.EntityPicker;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.domain.asegetraenke.entities.Kunde;
import de.nyg.domain.asegetraenke.entities.Produkt;
import de.nyg.domain.asegetraenke.valueobjects.Pfandwert;


public class GetraenkeInputHandlerTest {
    @Mock 
    private GetraenkeUsecases mockedGetraenkeUsecases;
    @Mock 
    private KundenUsecases mockedKundenUsecases;
    @Mock
    private ConsoleError mockedConsoleUtils;
    @Mock
    private CommandRegistrar commandRegistrar;
    @Mock 
    private EntityPicker<Kunde> mockedKundenPicker;
    @Mock 
    private EntityPicker<Produkt> mockedProductPicker;
    @Mock 
    private EntityPicker<Pfandwert> mockedPfandwertPicker;
    @Mock 
    private ConsoleReader mockedConsoleReader;
    @Mock
    private ConsolePrinter mockedConsolePrinter;

    private GetraenkeInputHandler getraenkeInputHandler;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.getraenkeInputHandler = new GetraenkeInputHandler(mockedGetraenkeUsecases, mockedKundenUsecases, mockedConsoleUtils, commandRegistrar,mockedKundenPicker,mockedProductPicker,mockedPfandwertPicker, mockedConsoleReader, mockedConsolePrinter);
    }

    @Test
    public void testHandleAddPfandWertInput() throws Exception{
        //Mock 
        String mockedDescription = "Description";
        double mockedValue = 16.3;
        when(mockedConsoleReader.readStringInputWithPrompt("Description: ")).thenReturn(mockedDescription);
        when(mockedConsoleReader.readDoubleInputWithPrompt("Value: ")).thenReturn(mockedValue);
        
        when(this.mockedConsoleReader.acceptInput()).thenReturn(true);

        //Call
        getraenkeInputHandler.handleAddPfandWertInput();

        //Verify
        verify(this.mockedGetraenkeUsecases).addPfandWert(mockedDescription, mockedValue);
    }

    @Disabled @Test
    public void testHandleSetPfandwertInput() throws Exception{
        //Mock
        String inputValuePrompt = "New Value for Pfandwert: ";
        Double returnValue = 16.3;
        Pfandwert mockPfandwert = new Pfandwert(5,"TestBeschreibung");
        when(mockedConsoleReader.readDoubleInputWithPrompt(inputValuePrompt));
        when(mockedPfandwertPicker.pickOneFromList(anyList(), any())).thenReturn(Optional.of(mockPfandwert));
        when(mockedConsoleReader.acceptInput()).thenReturn(true);

        //Run
        getraenkeInputHandler.handleSetPfandwertInput();

        //Verify
        verify(mockedGetraenkeUsecases.setPfandwert(mockPfandwert, returnValue));
    }
}

