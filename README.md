# FoliaGram

**Connect your Folia server straight to your pocket.**

FoliaGram is a lightweight, multithread-safe Telegram bridge built exclusively for Minecraft Folia 1.21+. It transforms your Telegram app into a remote control panel and a 1:1 real-time console mirror.

![Example](https://i.imgur.com/j3UduIY.png)

### Features
* **Real-Time Console Mirror:** Watch your server logs live from your phone.
* **Remote Commands:** Execute console commands securely via Telegram.
* **Chat Bridge:** See player chats, joins, and leaves instantly.
* **Custom Log Levels:** Use `/fg level [1-4]` in-game to filter how much information gets sent to your phone (from just chats up to full debug logs).
* **Folia Native:** Built natively for Folia's `GlobalRegionScheduler` and `AsyncScheduler` to ensure zero TPS impact and strict thread safety.
* **Secure:** Locked strictly to your personal Telegram User ID. 

### Installation
1. Drop `FoliaGram-1.0.1.jar` into your `plugins` folder.
2. Restart the server.
3. Open `plugins/FoliaGram/config.yml` and paste your Bot Token and Telegram ID.
4. Restart once more and hit `/start` on your bot in Telegram!
