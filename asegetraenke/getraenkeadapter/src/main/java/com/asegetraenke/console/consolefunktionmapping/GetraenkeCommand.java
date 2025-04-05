package com.asegetraenke.console.consolefunktionmapping;

import java.util.Arrays;
import java.util.Optional;

import com.asegetraenke.console.GetraenkeInputHandler;

public enum GetraenkeCommand {
    ACCEPT_LIEFERUNG("acceptlieferung", (handler) -> handler.handleAcceptLieferungInput()),
    ADD_PFANDWERT("addpfandwert", (handler) -> handler.handleAddPfandWertInput()),
    SET_PFANDWERT("setpfandwert", (handler) -> handler.handleSetPfandwertInput()),
    SET_PFANDWERT_PRODUKT("setpfandwertprodukt", (handler) -> handler.handleSetPfandwertProduktInput()),
    GET_ALL_PFANDWERTE("getallpfandwerte", (handler) -> handler.handleGetAllPfandwerteInput()),
    GET_PFANDWERT("getpfandwert", (handler) -> handler.handleGetPfandWertInput()),
    GET_ALL_PRODUCTS("getallproducts", (handler) -> handler.handleGetAllProductsInput()),
    GET_PRICE_FOR_PRODUKT("getpriceforprodukt", (handler) -> handler.handleGetPriceForProduktInput()),
    GET_PRICE_HISTORY_FOR_PRODUKT("getpricehistoryforprodukt", (handler) -> handler.handleGetPriceHistoryForProduktInput()),
    SET_PRICE_FOR_PRODUKT("setpriceforprodukt", (handler) -> handler.handleSetPriceForProduktInput()),
    GET_STOCK_AMOUNT("getstockamountforprodukt", (handler) -> handler.handleGetStockAmountForProduktInput()),
    ADD_PRODUKT("addprodukt", (handler) -> handler.handleAddProduktInput()),
    GET_PRODUCT("getproduct", (handler) -> handler.handleGetProductInput()),
    ADD_BESTELLUNG("addbestellung", (handler) -> handler.handleAddBestellungInput()),
    ADD_ZAHLUNGSVORGANG("addzahlungsvorgang", (handler) -> handler.handleAddZahlungsvorgangInput());

    private final String name;
    private final CommandExecutor executor;

    GetraenkeCommand(String name, CommandExecutor executor) {
        this.name = name;
        this.executor = executor;
    }

    public String getName() {
        return name;
    }

    public static Optional<GetraenkeCommand> fromString(String input) {
        return Arrays.stream(values())
                .filter(cmd -> cmd.name.equalsIgnoreCase(input))
                .findFirst();
    }

    public void execute(GetraenkeInputHandler handler) {
        executor.execute(handler);
    }

    // Functional interface to execute the commands with handler
    @FunctionalInterface
    public interface CommandExecutor {
        void execute(GetraenkeInputHandler handler);
    }
}


