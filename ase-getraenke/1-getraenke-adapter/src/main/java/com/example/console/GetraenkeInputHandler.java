package com.example.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GetraenkeInputHandler {
    private final Scanner scanner;
    private final Map<String,Runnable> getrankeCommandMap;

    public GetraenkeInputHandler(Scanner sc){
        this.scanner = sc;
        this.getrankeCommandMap = initializeCommandMapGetraenke();
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

    public void handleAcceptLieferungInput() {
        // Handle acceptLieferung logic here
    }
        
    public void handleAddPfandWertInput() {
    // Handle addPfandWert logic here
    }
    
    public void handleSetPfandwertInput() {
    // Handle setPfandwert logic here
    }
    
    public void handleSetPfandwertProduktInput() {
    // Handle setPfandwertProdukt logic here
    }
    
    public void handleGetAllPfandwerteInput() {
    // Handle getAllPfandwerte logic here
    }
    
    public void handleGetPfandWertInput() {
    // Handle getPfandWert logic here
    }
    
    public void handleGetAllProductsInput() {
    // Handle getAllProducts logic here
    }
    
    public void handleGetPriceForProduktInput() {
    // Handle getPriceForProdukt logic here
    }
    
    public void handleGetPriceHistoryForProduktInput() {
        
    }
    
    public void handleSetPriceForProduktInput() {
    // Handle setPriceForProdukt logic here
    }
    
    public void handleGetStockAmountForProduktInput() {
    // Handle getStockAmountForProdukt logic here
    }
    
    public void handleAddProduktInput() {
    // Handle addProdukt logic here
    }
    
    public void handleGetProductInput() {
    // Handle getProduct logic here
    }
    
    public void handleAddBestellungInput() {
    // Handle addBestellung logic here
    }
    
    public void handleAddZahlungsvorgangInput() {
    // Handle addZahlungsvorgang logic here
    }

    public Map<String,Runnable> getGetrankeCommandMap() {
        return this.getrankeCommandMap;
    }
}
