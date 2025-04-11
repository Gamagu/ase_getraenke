package de.nyg.adapters.asegetraenke.console;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.nyg.application.asegetraenke.KundenUsecases;
import de.nyg.adapters.asegetraenke.console.Utils.ConsoleUtils;
import de.nyg.adapters.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import de.nyg.domain.asegetraenke.entities.Kunde;

public class KundenInputHandlerTest {
    @Mock
    private KundenUsecases mockedKundeusecases;
    @Mock
    private ConsoleUtils mockedconsoleUtils;
    @Mock
    private CommandRegistrar commandRegistrar;


    private final String TESTNAME = "TestName";
    private final String TESTNACHNAME= "TestNachname";
    private final String TESTEMAIL = "test@example.com";

    private final String PROMPTNAME = "Name: ";
    private final String PROMPTNACHNAME = "Nachname: ";
    private final String PROMPTEMAIL = "E-Mail: ";
    
    private KundenInputHandler kundenInputHandler;
    private Kunde kunde1;
    private Kunde kunde2;
    private Iterable<Kunde> kundenVec;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {

        this.kunde1 = new Kunde("name1","nachname1", "test1@test.de");
        this.kunde2 = new Kunde("name2","nachname2", "test2@test.de");
        kundenVec = Arrays.asList(kunde1,kunde2);

        MockitoAnnotations.openMocks(this);
        kundenInputHandler = new KundenInputHandler(mockedKundeusecases, mockedconsoleUtils, commandRegistrar);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
        public void tearDown() {
        System.setOut(originalOut);
    }


    @Test
    public void testHandleCreateKundeInput() {
        // Mock the interactions
        when(mockedKundeusecases.createKunde(anyString(), anyString(),anyString())).thenReturn(null);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNAME)).thenReturn(TESTNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNACHNAME)).thenReturn(TESTNACHNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTEMAIL)).thenReturn(TESTEMAIL);
        when(mockedconsoleUtils.acceptInput()).thenReturn(true);

        kundenInputHandler = new KundenInputHandler(mockedKundeusecases, mockedconsoleUtils, commandRegistrar);

        // Perform the action
        kundenInputHandler.handleCreateKundeInput();
        
        // Verify that the method was called with expected arguments
        verify(mockedKundeusecases).createKunde( TESTNAME, TESTNACHNAME, TESTEMAIL); 
    }

    @Test
    public void testHandleCreateKundeInputNoAccept() {
        // Mock 
        when(mockedKundeusecases.createKunde(anyString(), anyString(),anyString())).thenReturn(null);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNAME)).thenReturn(TESTNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNACHNAME)).thenReturn(TESTNACHNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTEMAIL)).thenReturn(TESTEMAIL);
        when(mockedconsoleUtils.acceptInput()).thenReturn(false);

        //Call
        kundenInputHandler.handleCreateKundeInput();

        //Verify
        verifyNoInteractions(mockedKundeusecases);
    }


    @Test 
    public void testHandleGetAllKundenInput() {

        //Mock
        when(mockedKundeusecases.getAllKunden()).thenReturn(this.kundenVec);
        
        // Perform the action
        kundenInputHandler.handleGetAllKundenInput();

        //Verify
        verify(mockedconsoleUtils).printKundeWithNumber(this.kunde1, 1);
        verify(mockedconsoleUtils).printKundeWithNumber(this.kunde2, 2);
    }

    @Test
    public void testHandleGetAllKundenInputNoKunden() {
        //Mock
        when(mockedKundeusecases.getAllKunden()).thenReturn(Collections.emptyList());
        
        // Perform the action
        kundenInputHandler.handleGetAllKundenInput();

        //Verify
        verify(mockedconsoleUtils).errorNoKunden();
    }

    @Test
    public void testHandleSetNameInput() {
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.of(this.kunde1));
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNAME)).thenReturn(TESTNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNACHNAME)).thenReturn(TESTNACHNAME);
        when(mockedconsoleUtils.acceptInput()).thenReturn(true);

        //Executing
        kundenInputHandler.handleSetNameInput();

        //Verify
        verify(mockedKundeusecases).setName(this.kunde1,TESTNAME,TESTNACHNAME);
    }

    @Test
    public void testHandleSetNameInputNoKunde() {
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.empty());
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNAME)).thenReturn(TESTNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNACHNAME)).thenReturn(TESTNACHNAME);
        when(mockedconsoleUtils.acceptInput()).thenReturn(true);

        //Executing
        kundenInputHandler.handleSetNameInput();

        //Verify
        verify(mockedconsoleUtils).errorNoKunden();
        verifyNoInteractions(mockedKundeusecases);
    }

    @Test
    public void testHandleSetNameInputNoAccept() {
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.of(this.kunde1));
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNAME)).thenReturn(TESTNAME);
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTNACHNAME)).thenReturn(TESTNACHNAME);
        when(mockedconsoleUtils.acceptInput()).thenReturn(false);
    
        //Executing
        kundenInputHandler.handleSetNameInput();
        
        //Verify
        verifyNoInteractions(mockedKundeusecases);
    }

    @Test
    public void testHandleGetKundeInput() {
        // Mock
        when(mockedconsoleUtils.readStringInputWithPrompt(PROMPTEMAIL)).thenReturn(TESTEMAIL);
        when(mockedKundeusecases.getKunde(TESTEMAIL)).thenReturn(Optional.of(this.kunde1));

        // Execute
        kundenInputHandler.handleGetKundeInput();

        //Verify
        verify(mockedKundeusecases).getKunde(TESTEMAIL);
    }

    @Test
    public void testHandleGetKundenBalanceInput() {
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.of(this.kunde1));
    
        //Execute
        kundenInputHandler.handleGetKundenBalanceInput();

        // Verify
        verify(mockedKundeusecases).getKundenBalance(this.kunde1);
    }

    @Test
    public void testHandleGetKundenBalanceInputNoKunde() {
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.empty());

        //Execute
        kundenInputHandler.handleGetKundenBalanceInput();

        //Verify 
        verifyNoInteractions(mockedKundeusecases);
        verify(mockedconsoleUtils).errorNoKunden();
    
    }

    @Test
    public void testHandleGetAllBestellungenInput(){
        //Mock
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.of(this.kunde1));

        //Execute 
        kundenInputHandler. handleGetAllBestellungenInput();

        //Verify
        verify(mockedKundeusecases).getAllBestellungen(this.kunde1);

    }

    @Test
    public void testHandleGetAllBestellungenInputNoKunde(){
        //Mock 
        when(mockedconsoleUtils.pickOneUserFromAllUsers()).thenReturn(Optional.empty());

        //Execute 
        kundenInputHandler. handleGetAllBestellungenInput();

        //Verify
        verify(mockedconsoleUtils).errorNoKunden();
        verifyNoInteractions(mockedKundeusecases);
        
    }
}
