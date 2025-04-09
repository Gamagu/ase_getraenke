package adapter.asegetraenke.console;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import adapter.asegetraenke.console.Utils.ConsoleUtils;
import adapter.asegetraenke.console.consolefunctionmapping.CommandRegistrar;
import adapter.asegetraenke.console.consolefunctionmapping.ICommand;
import application.asegetraenke.KundenUsecases;
import domain.asegetraenke.entities.Bestellung;
import domain.asegetraenke.entities.Kunde;

public class KundenInputHandler {
    private final KundenUsecases kundeUseCases;
    private final ConsoleUtils consoleUtils;
    
    public KundenInputHandler(KundenUsecases kundeUseCases, ConsoleUtils consoleUtils, CommandRegistrar registrar) {
        this.kundeUseCases = kundeUseCases;
        this.consoleUtils = consoleUtils;
        registrar.registerCommands(this);
    }

    @ICommand(value = "createkunde", category = "kunde")
    public void handleCreateKundeInput() {
        System.out.println("Create Customer: ");
        String name = consoleUtils.readStringInputWithPrompt("Name: ");
        String nachname = consoleUtils.readStringInputWithPrompt("Nachname: ");    
        String eMail = consoleUtils.readStringInputWithPrompt("E-Mail: ");
        if(consoleUtils.acceptInput()){
            kundeUseCases.createKunde(name, nachname, eMail);
            System.out.println("Kunde was created succesfully");
        }
    }

    @ICommand(value = "getallkunden", category = "kunde")
    public void handleGetAllKundenInput() {
        Iterable<Kunde> kundenVec = this.kundeUseCases.getAllKunden();
        List<Kunde> kundenList = StreamSupport.stream(kundenVec.spliterator(), false)
                                 .collect(Collectors.toList());
        
        if(kundenList.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        int count = 1;
        for(Kunde kunde : kundenList){
            consoleUtils.printKundeWithNumber(kunde,count);
            count++;
        }
    }

    @ICommand(value = "setname", category = "kunde")
    public void handleSetNameInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        System.out.println(kunde.toString());
        String newFirstName = consoleUtils.readStringInputWithPrompt("Name: ");
        String newSecondName = consoleUtils.readStringInputWithPrompt("Nachname: ");
        if(!consoleUtils.acceptInput()){
            return;
        }
        this.kundeUseCases.setName(kunde, newFirstName, newSecondName);
        System.out.println("Name was succesfully changed");
    }


    @ICommand(value = "getkunde", category = "kunde")
    public void handleGetKundeInput() {
        String eMail = consoleUtils.readStringInputWithPrompt("E-Mail: ");
        Optional<Kunde> kunde = this.kundeUseCases.getKunde(eMail);
        if(kunde.isPresent()){
            return;
        }
        System.out.println("No Customer with the Mail: " + eMail + " was found.");
    }
    
    @ICommand(value = "getkundenbalance", category = "kunde")
    public void handleGetKundenBalanceInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        double dBalance = this.kundeUseCases.getKundenBalance(kunde);
        System.out.println( kunde.toString()+ "\n"+ dBalance);
    }

    @ICommand(value = "getallbestellungen", category = "kunde")
    public void handleGetAllBestellungenInput() {
        Optional<Kunde> kundeOptional = consoleUtils.pickOneUserFromAllUsers();
        if(kundeOptional.isEmpty()){
            consoleUtils.errorNoKunden();
            return;
        }
        Kunde kunde = kundeOptional.get();
        Iterable<Bestellung> bestellungsVec = this.kundeUseCases.getAllBestellungen(kunde);
        for(Bestellung bestellung : bestellungsVec){
            System.out.println(bestellung.toString());
        }
    }
}
