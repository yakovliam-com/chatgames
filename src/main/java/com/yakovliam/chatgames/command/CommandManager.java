package com.yakovliam.chatgames.command;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.MessageType;
import com.yakovliam.chatgames.ChatGamesPlugin;
import org.bukkit.ChatColor;

import java.util.Arrays;

public class CommandManager extends BukkitCommandManager {

    @SuppressWarnings("deprecation")
    public CommandManager(ChatGamesPlugin plugin) {
        super(plugin);

        enableUnstableAPI("help");
        enableUnstableAPI("brigadier");

        setFormat(MessageType.INFO, ChatColor.WHITE);
        setFormat(MessageType.HELP, ChatColor.GRAY);
        setFormat(MessageType.ERROR, ChatColor.RED);
        setFormat(MessageType.SYNTAX, ChatColor.GRAY);

        // TODO add more commands here, registering them
        Arrays.asList(
                new ChatGamesCommand(this, plugin)
        ).forEach(c -> {
            c.registerCompletions();
            c.registerContexts();

            this.registerCommand(c);
        });
    }
}
