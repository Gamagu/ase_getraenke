package com.asegetraenke.console.consolefunctionmapping;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {
    private Map<String, Runnable> commandMap = new HashMap<>();

    public void registerCommand(String commandName, Runnable commandHandler) {
        commandMap.put(commandName, commandHandler);
    }

    public Runnable getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }

    public Map<String, Runnable> getCommandMap() {
        return commandMap;
    }
}
