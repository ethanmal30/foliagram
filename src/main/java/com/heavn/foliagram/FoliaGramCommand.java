package com.heavn.foliagram;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FoliaGramCommand implements CommandExecutor, TabCompleter {

    private final FoliaGram plugin;

    public FoliaGramCommand(FoliaGram plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("foliagram.admin")) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("level")) {
            if (args.length < 2) {
                sender.sendMessage("§cUsage: /" + label + " level [1-4]");
                return true;
            }

            try {
                int level = Integer.parseInt(args[1]);
                if (level < 1 || level > 4) {
                    sender.sendMessage("§cLevel must be between 1 and 4.");
                    return true;
                }

                plugin.setLogLevel(level);
                sender.sendMessage("§aFoliaGram log level set to " + level + ".");
                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage("§cInvalid number. Usage: /" + label + " level [1-4]");
                return true;
            }
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§8======== §bFoliaGram Help §8========");
        sender.sendMessage("§e/fg level 1 §7- Chat, and joins/leaves");
        sender.sendMessage("§e/fg level 2 §7- Chat, joins/leaves, and commands");
        sender.sendMessage("§e/fg level 3 §7- Chat, joins/leaves, commands, warnings, and errors");
        sender.sendMessage("§e/fg level 4 §7- Everything");
        sender.sendMessage("§eCurrent Level: §a" + plugin.getLogLevel());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "level");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("level")) {
            return Arrays.asList("1", "2", "3", "4");
        }
        return new ArrayList<>();
    }
}