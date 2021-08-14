package com.yakovliam.chatgames.command;

import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.api.message.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("chatgames")
@CommandPermission("chatgames.command")
public class ChatGamesCommand extends AbstractChatGamesCommand {

    /**
     * Ecoporium command
     *
     * @param manager manager
     * @param plugin  plugin
     */
    public ChatGamesCommand(CommandManager manager, ChatGamesPlugin plugin) {
        super(manager, plugin);
    }

    @Subcommand("reload")
    @CommandPermission("chatgames.command.reload")
    public void onReload(Player player) {
        // reload config
        plugin.getChatGamesConfig().reload();
        // reload question manager
        plugin.loadQuestionManager();
        // reload active question manager
        plugin.getActiveQuestionManager().stopRepeatingQuestionTask();
        plugin.getActiveQuestionManager().startRepeatingQuestionTask();
    }

    @HelpCommand
    @Default
    @CatchUnknown
    public void doHelp(CommandSender sender, CommandHelp help) {
        Message.builder()
                .addLine("&7ChatGames Help")
                .build().message(sender);
        help.showHelp();
    }

    @Override
    protected void registerCompletions() {

    }

    @Override
    protected void registerContexts() {

    }
}
