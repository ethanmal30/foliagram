package com.heavn.foliagram;

import org.bukkit.Bukkit;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramHandler extends TelegramLongPollingBot {

    private final FoliaGram plugin;
    private final String botUsername;
    private final String botToken;
    private final long adminUserId;

    public TelegramHandler(FoliaGram plugin) {
        super(plugin.getConfig().getString("bot-token"));
        this.plugin = plugin;
        this.botUsername = plugin.getConfig().getString("bot-username");
        this.botToken = plugin.getConfig().getString("bot-token");
        this.adminUserId = plugin.getConfig().getLong("admin-user-id");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long senderId = update.getMessage().getFrom().getId();

            if (senderId != adminUserId) {
                plugin.getLogger().warning("Unauthorized Telegram access attempt from ID: " + senderId);
                return;
            }

            String text = update.getMessage().getText();

            if (text.startsWith("/")) {
                String mcCommand = text.substring(1);

                Bukkit.getGlobalRegionScheduler().run(plugin, task -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), mcCommand);
                });
            } else {
                Bukkit.getGlobalRegionScheduler().run(plugin, task -> {
                    Bukkit.broadcastMessage("[Server] " + text);
                });
            }
        }
    }

    public void sendToAdmin(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(adminUserId));
        message.setText(messageText);
        try {
            execute(message);
        } catch (Exception e) {
            plugin.getLogger().severe("Could not send message to Telegram: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}