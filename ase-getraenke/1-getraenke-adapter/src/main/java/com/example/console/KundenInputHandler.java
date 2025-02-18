package com.example.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

import com.example.kundeusecases;
import com.example.entities.Kunde;

public class KundenInputHandler {
    private final Scanner scanner;
    private final Map<String, Runnable> kundeCommandMap;
    private final kundeusecases kundeUseCases;
    
    public KundenInputHandler(Scanner scanner, kundeusecases kundeUseCases) {
        this.scanner = scanner;
        this.kundeCommandMap = initializeCommandMapKunde();
        this.kundeUseCases = kundeUseCases;
    }

    private Map<String, Runnable> initializeCommandMapKunde() {
        Map<String, Runnable> map = new HashMap<>();
        map.put("createkunde", () -> handleCreateKundeInput());
        map.put("getallkunden", () -> handleGetAllKundenInput());
        map.put("setname", () -> handleSetNameInput());
        map.put("getkunde", () -> handleGetKundeInput());
        map.put("getkundenbalance", () -> handleGetKundenBalanceInput());
        map.put("getallbestellungen", () -> handleGetAllBestellungenInput());
        return map;
    }

    private void handleCreateKundeInput() {
        System.out.println("Create Customer: ");
        String name = readStringInputWithPrompt("Name: ");
        String nachname = readStringInputWithPrompt("Nachname: ");    
        String eMail = readStringInputWithPrompt("E-Mail: ");
        if(acceptInput()){
            kundeUseCases.createKunde(name, nachname, eMail);
            System.out.println("Kunde was create succesfully");
        }
    }

    private void handleGetAllKundenInput() {
        Iterable<Kunde> kundenList = this.kundeUseCases.getAllKunden();
        int count = 1;
        for(Kunde kunde : kundenList){
            printKundeWithNumber(kunde,count);
            count++;
        }
    }

    private void handleSetNameInput() {
        Iterable<Kunde> kundenIter = this.kundeUseCases.getAllKunden();
        List<Kunde> kundenList = new ArrayList<Kunde>();
        kundenList.addAll(kundenList);
        int count = 1;
        for(Kunde kunde : kundenIter) {
            printKundeWithNumber(kunde, count);
            kundenList.add(kunde);
            
        }
        int indexCustomer = 0;
        while (true) {
            indexCustomer = readIntInputWithPrompt("Which Customername do you want to change?");
            if(indexCustomer < count && indexCustomer > 0){
                break;
            }
            System.out.println("Something went wrong the "+ indexCustomer +  " is not in the list");
        }
        Kunde kunde = kundenList.get(indexCustomer-1);
        System.out.println(kunde.toString());
        String newFirstName = readStringInputWithPrompt("Please enter a new first Name: ");
        String newSecondName = readStringInputWithPrompt("Please enter a new first Name: ");
        this.kundeUseCases.setName(kunde, newFirstName, newSecondName);
        System.out.println("Name was succesfully changed");
    }

    private void handleGetKundeInput() {
        //TODO Implemente
    }

    private void handleGetKundenBalanceInput() {
        //TODO Implemente
    }

    private void handleGetAllBestellungenInput() {
        //TODO Implemente
    }
    
    
    public Map<String, Runnable> getKundeCommandMap() {
        return kundeCommandMap;
    }

    private String readStringInputWithPrompt(String prompt){
        System.out.print(prompt);
        return this.scanner.nextLine();
    }

    private int readIntInputWithPrompt(String prompt){
        System.out.print(prompt);
        while(true){
            String input = this.scanner.nextLine();
            try{
                int inputCastInt = Integer.parseInt(input);
                return inputCastInt;
            }catch(Exception e){
                System.out.println("The input: "+ input+ " can not be translated into a number");
            }
        }
    }

    private Float readFloatInputWithPrompt(String prompt){
        System.out.print(prompt);
        while(true){
            String input = this.scanner.nextLine();
            try{
                Float inputCastInt = Float.parseFloat(input);
                return inputCastInt;
            }catch(Exception e){
                System.out.println("The input: "+ input+ " can not be translated into a number");
            }
        }
    }

    private Boolean acceptInput(){
        while(true){
            System.out.print("Finish process yes[y] / no[n]");
            String input = this.scanner.nextLine();
            input = input.toLowerCase();
            if(input.equals("y")){
                return true;
            }
            if(input.equals("n")){
                return false;
            }
        }
    }

    private void printKundeWithNumber(Kunde kunde, int number){
        System.out.println(number + ". "+ kunde.toString());
    } 
    
}
