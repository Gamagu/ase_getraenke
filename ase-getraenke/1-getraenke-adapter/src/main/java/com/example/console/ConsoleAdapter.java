package com.example.console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleAdapter {

    private final Scanner scanner;
    private final boolean isRunning;
    private final String welcomeMessageString = "TODO add welcome with short explaination"; 
    private final Map<String, Runnable> commandMap;

    public ConsoleAdapter() {
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.commandMap = initializeCommandMap();
    }

    private Map<String, Runnable> initializeCommandMap() {
            Map<String, Runnable> map = new HashMap<>();
            map.put("acceptlieferung", this::handleAcceptLieferungInput);
            map.put("addpfandwert", this::handleAddPfandWertInput);
            map.put("setpfandwert", this::handleSetPfandwertInput);
            map.put("setpfandwertprodukt", this::handleSetPfandwertProduktInput);
            map.put("getallpfandwerte", this::handleGetAllPfandwerteInput);
            map.put("getpfandwert", this::handleGetPfandWertInput);
            map.put("getallproducts", this::handleGetAllProductsInput);
            map.put("getpriceforprodukt", this::handleGetPriceForProduktInput);
            map.put("getpricehistoryforprodukt", this::handleGetPriceHistoryForProduktInput);
            map.put("setpriceforprodukt", this::handleSetPriceForProduktInput);
            map.put("getstockamountforprodukt", this::handleGetStockAmountForProduktInput);
            map.put("addprodukt", this::handleAddProduktInput);
            map.put("getproduct", this::handleGetProductInput);
            map.put("addbestellung", this::handleAddBestellungInput);
            map.put("addzahlungsvorgang", this::handleAddZahlungsvorgangInput);
            return map;
            
}

    public void start() {
        System.out.println(welcomeMessageString);
        printOption();
        while(isRunning) {
            String option = scanner.nextLine();
            option = option.trim();
            option = option.toLowerCase();

            Runnable action = this.commandMap.get(option);
            if (action == null) {
                System.out.println(option + " is not a viable option, please refer to the possible options: \n");
                printOption();
                continue;
            }
        }
    }
    private void handleInput() {

    }

    private void handleAcceptLieferungInput() {
        // Handle acceptLieferung logic here
    }
        
    private void handleAddPfandWertInput() {
    // Handle addPfandWert logic here
    }
    
    private void handleSetPfandwertInput() {
    // Handle setPfandwert logic here
    }
    
    private void handleSetPfandwertProduktInput() {
    // Handle setPfandwertProdukt logic here
    }
    
    private void handleGetAllPfandwerteInput() {
    // Handle getAllPfandwerte logic here
    }
    
    private void handleGetPfandWertInput() {
    // Handle getPfandWert logic here
    }
    
    private void handleGetAllProductsInput() {
    // Handle getAllProducts logic here
    }
    
    private void handleGetPriceForProduktInput() {
    // Handle getPriceForProdukt logic here
    }
    
    private void handleGetPriceHistoryForProduktInput() {
    // Handle getPriceHistoryForProdukt logic here
    }
    
    private void handleSetPriceForProduktInput() {
    // Handle setPriceForProdukt logic here
    }
    
    private void handleGetStockAmountForProduktInput() {
    // Handle getStockAmountForProdukt logic here
    }
    
    private void handleAddProduktInput() {
    // Handle addProdukt logic here
    }
    
    private void handleGetProductInput() {
    // Handle getProduct logic here
    }
    
    private void handleAddBestellungInput() {
    // Handle addBestellung logic here
    }
    
    private void handleAddZahlungsvorgangInput() {
    // Handle addZahlungsvorgang logic here
    }

    private void printOption() {
        for (String command : commandMap.keySet()) {
            System.out.println(" - " + command);
        }
    }
}
