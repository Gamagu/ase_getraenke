package com.asegetraenke.console.consolefunctionmapping;

import java.util.Map;

public interface ICommandRegistrar {
    void registerCommands(Object handler);
    Runnable getCommand(String category, String commandName);
    public boolean hasCommand(String category, String commandName);
    public Map<String, CommandRegistry> getCommandRegistry();
}
