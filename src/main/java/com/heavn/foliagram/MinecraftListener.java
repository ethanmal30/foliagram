package com.heavn.foliagram;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MinecraftListener implements Listener {

    private final FoliaGram plugin;
    private final TelegramHandler telegramHandler;

    public MinecraftListener(FoliaGram plugin, TelegramHandler telegramHandler) {
        this.plugin = plugin;
        this.telegramHandler = telegramHandler;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());

        telegramHandler.sendToAdmin("<" + playerName + "> " + message);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        telegramHandler.sendToAdmin(event.getPlayer().getName() + " joined the server");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        telegramHandler.sendToAdmin(event.getPlayer().getName() + " left the server");
    }
}