package com.heavn.foliagram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class FoliaGram extends JavaPlugin {

    private TelegramHandler telegramHandler;
    private ConsoleAppender consoleAppender;
    private int logLevel;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.logLevel = getConfig().getInt("log-level", 4);

        if (getConfig().getString("bot-token", "").equals("YOUR_BOT_TOKEN_HERE") ||
                getConfig().getString("bot-username", "").equals("YOUR_BOT_USERNAME") ||
                getConfig().getLong("admin-user-id", 0) == 1234567890) {

            getLogger().severe("Please fully configure your config.yml! (Token, Username, or Admin ID is missing)");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            DefaultBotOptions botOptions = new DefaultBotOptions();
            botOptions.setMaxThreads(2);

            telegramHandler = new TelegramHandler(this, botOptions);
            botsApi.registerBot(telegramHandler);
            getLogger().info("Telegram bot connected successfully!");

            consoleAppender = new ConsoleAppender(this, telegramHandler);
            consoleAppender.start();
            Logger rootLogger = (Logger) LogManager.getRootLogger();
            rootLogger.addAppender(consoleAppender);

            getCommand("foliagram").setExecutor(new FoliaGramCommand(this));

            telegramHandler.sendToAdmin("Server started successfully!");

        } catch (Exception e) {
            getLogger().severe("Failed to initialize Telegram Bot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        if (consoleAppender != null) {
            Logger rootLogger = (Logger) LogManager.getRootLogger();
            rootLogger.removeAppender(consoleAppender);
            consoleAppender.stop();
        }

        if (telegramHandler != null) {
            telegramHandler.sendToAdmin("Server is shutting down!");
        }
        getLogger().info("FoliaGram disabled.");
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int level) {
        this.logLevel = level;
        getConfig().set("log-level", level);
        saveConfig();
    }
}
