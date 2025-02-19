package com.example.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.xml.transform.Templates;

import com.example.entities.Kunde;
import com.example.entities.Produkt;
import com.example.util.Pair;
import com.example.valueobjects.Preis;
import com.example.GetraenkeRepositoryImpl;
import com.example.getraenkeusecases;

public class GetraenkeInputHandler {
    private final String NOPRODUKTMESSAGE = "There are no Product/s found";

    private final Scanner scanner;
    private final Map<String,Runnable> getrankeCommandMap;
    private final getraenkeusecases getraenkeusecases;

    public GetraenkeInputHandler(Scanner sc, getraenkeusecases getraenkeusecases){
        this.scanner = sc;
        this.getrankeCommandMap = initializeCommandMapGetraenke();
        this.getraenkeusecases = getraenkeusecases;
    }

    private Map<String, Runnable> initializeCommandMapGetraenke() {
        Map<String, Runnable> map = new HashMap<>();
        map.put("acceptlieferung", () -> handleAcceptLieferungInput());
        map.put("addpfandwert", () -> handleAddPfandWertInput());
        map.put("setpfandwert", () -> handleSetPfandwertInput());
        map.put("setpfandwertprodukt", () -> handleSetPfandwertProduktInput());
        map.put("getallpfandwerte", () -> handleGetAllPfandwerteInput());
        map.put("getpfandwert", () -> handleGetPfandWertInput());
        map.put("getallproducts", () -> handleGetAllProductsInput());
        map.put("getpriceforprodukt", () -> handleGetPriceForProduktInput());
        map.put("getpricehistoryforprodukt", () -> handleGetPriceHistoryForProduktInput());
        map.put("setpriceforprodukt", () -> handleSetPriceForProduktInput());
        map.put("getstockamountforprodukt", () -> handleGetStockAmountForProduktInput());
        map.put("addprodukt", () -> handleAddProduktInput());
        map.put("getproduct", () -> handleGetProductInput());
        map.put("addbestellung", () -> handleAddBestellungInput());
        map.put("addzahlungsvorgang", () -> handleAddZahlungsvorgangInput());
        return map;
    }

    //TODO Ask Nikals what he was thinking here 
    public void handleAcceptLieferungInput() {
        // Handle acceptLieferung logic here
    }
    //TODO Ask Nikals what he was thinking here 
    public void handleAddPfandWertInput() {
    // Handle addPfandWert logic here
    }

    //TODO Ask Nikals what he is thinking here 
    public void handleSetPfandwertInput() {
    // Handle setPfandwert logic here
    }
    //TODO Ask Nikals what he is thinking here 
    public void handleSetPfandwertProduktInput() {
    // Handle setPfandwertProdukt logic here
    }
    //TODO Ask Nikals what he is thinking here 
    public void handleGetAllPfandwerteInput() {
    // Handle getAllPfandwerte logic here
    }
    //TODO Ask Nikals what he is thinking here 
    public void handleGetPfandWertInput() {
    // Handle getPfandWert logic here
    }
    
    //TODO fix Type Issue 
    public void handleGetAllProductsInput() {
        Iterable<Produkt> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            errorNoProdukt();
            return;
        }
        int count = 1;
        for(Produkt produkt : productList){
            printProduktWithNumber(produkt, count);
            count++;
        }
    }
    // TODO fix TypeIssue
    public void handleGetPriceForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        System.out.println("The Price of the product: \n "+ produkt.toString() + 
                           "\n is: \n" + 
                           getraenkeRepositoryImpl.getPreisForProdukt(produkt));
    }
    
    public void handleGetPriceHistoryForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        Iterable<Preis> priceHistory  = getraenkeusecases.getPriceHistoryForProdukt(produkt);
        System.out.println("Price History of " + produkt.toString() + ":");
        for(Preis preis : priceHistory){
            System.out.println(preis.toString());
        }

    }
    
    public void handleSetPriceForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int newPrice = readIntInputWithPrompt("Input new price for product: ");
        if(!acceptInput()){
            return;
        }
        getraenkeusecases.setPriceForProdukt(produkt, newPrice);
    }

    public void handleGetStockAmountForProduktInput() {
        Optional<Produkt> produktOptional = pickOneProductFromAllProducts();
        if(produktOptional.isEmpty()){
            errorNoProdukt();
            return;
        }
        Produkt produkt = produktOptional.get();
        int stock = getraenkeusecases.getStockAmountForProdukt(produkt);
        System.out.println("The Stock of the Product "+ produkt.toString()+ " is : " + stock);        
    }
    
    public void handleAddProduktInput() {
        System.out.println("Add Product to existing Products");
        String name = readStringInputWithPrompt("Name: ");
        String beschreibung = readStringInputWithPrompt("Describtion: ");
        String kategorie = readStringInputWithPrompt("Categorie: ");
        int price = readIntInputWithPrompt("Price: ");

        if(!acceptInput()){
            return;
        }
        try {
            getraenkeusecases.addProdukt(name, beschreibung, kategorie, price);    
        } catch (Exception e) {
            System.out.println("An error occured while adding a new product");
            System.err.println(e.getMessage());
        }
        
    }
    
    public void handleGetProductInput() {
        String uuidString = readStringInputWithPrompt("UUID for product: ");
        UUID uuid = UUID.fromString(uuidString);
        try {
            Optional<Produkt> produkt = getraenkeusecases.getProduct(uuid);    
            if(produkt.isEmpty()){
                errorNoProdukt();
                return;
            }
            System.out.println(produkt.toString());
        } catch (Exception e) {
            System.err.println("Input: "+ uuid+ "\n" + e.getMessage());
        }
        
    }
    // TODO Refactor pickOneUserOfAllUsers so it can be accesed here 
    public void handleAddBestellungInput() {
    
    }
    // TODO Refactor pickOneUserOfAllUsers so it can be accesed here 
    public void handleAddZahlungsvorgangInput() {
    // Handle addZahlungsvorgang logic here
    }

    public Map<String,Runnable> getGetrankeCommandMap() {
        return this.getrankeCommandMap;
    }

    //TODO fix Type Issue 
    private Optional<Produkt> pickOneProductFromAllProducts() {
        Iterable<Pair<Produkt, String>> productVec = getraenkeusecases.getAllProducts();
        List<Produkt> productList = StreamSupport.stream(productVec.spliterator(), false)
                                    .collect(Collectors.toList());
        if(productList.isEmpty()){
            return Optional.empty();
        }

        int count = 1;
        for(Produkt product : productList) {
            printProduktWithNumber(product, count);
        }
        int indexProdukt = 0;
        while (true) {
            indexProdukt = readIntInputWithPrompt("Which Customername do you want to change? Enter the Number: ");
            if(indexProdukt < productList.size()+1 && indexProdukt > 0){
                break;
            }
            System.out.println("Something went wrong the "+ indexProdukt +  " is not in the list");
        }
        System.out.println("Chosen Produkt: " productList.get(indexProdukt-1).toString());
        return Optional.of(productList.get(indexProdukt-1));
    }

    private void printProduktWithNumber(Produkt produkt, int number){
        System.out.println(number + ". "+ produkt.toString());
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

    private String readStringInputWithPrompt(String ptompString){
        System.out.print(ptompString);
        return this.scanner.nextLine();
    }

    private void errorNoProdukt() {
        System.out.println(NOPRODUKTMESSAGE);
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
                System.out.println("Process was aborted");
                return false;
            }
        }
    }

}
