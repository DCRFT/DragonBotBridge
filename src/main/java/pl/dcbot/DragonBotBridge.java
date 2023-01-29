package pl.dcbot;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import pl.dcbot.Managers.DatabaseManager;
import pl.dcbot.Managers.DcLinkManager;

import java.util.concurrent.CompletableFuture;

public class DragonBotBridge
extends JavaPlugin
implements Listener {
    private DiscordApi api;
    private static DragonBotBridge instance;


    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);

        BukkitRunnable runnable = new BukkitRunnable(){

            public void run() {
                DatabaseManager.openConnection();
            }
        };
        runnable.runTaskAsynchronously(this);
        instance = this;
        ((CompletableFuture)new DiscordApiBuilder().setToken(this.getConfig().getString("token")).setAllNonPrivilegedIntents().login().thenAccept(this::onConnectToDiscord)).exceptionally(error -> {
            this.getLogger().warning("\u00a7cNie mo\u017cna zalogowa\u0107 si\u0119 do Discord! Wy\u0142aczanie...");
            this.getPluginLoader().disablePlugin(this);
            return null;
        });
        this.getCommand("zglos").setExecutor(new DcLinkManager(this));
        this.getCommand("discord").setExecutor(new DcLinkManager(this));
        this.getCommand("dclink").setExecutor(new DcLinkManager(this));
        this.getCommand("przeniesrange").setExecutor(new DcLinkManager(this));
        this.getCommand("anulujprzenoszenie").setExecutor(new DcLinkManager(this));
    }

    public static DragonBotBridge getInstance() {
        return instance;
    }

    public void onDisable() {
        if (this.api != null) {
            this.api.disconnect();
            this.api = null;
        }
        this.saveConfig();
        DatabaseManager.closeConnection();
    }

    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;
        this.getLogger().info("\u00a7aPo\u0142\u0105czono z Discordem jako " + api.getYourself().getDiscriminatedName());
    }

    public DiscordApi getApi() {
        return this.api;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (this.getConfig().getString(e.getPlayer().getName() + ".prangi") != null) {
            e.getPlayer().sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cHej! Jeste\u015b w trakcie przenoszenia rangi mi\u0119dzy kontami \u00a7e" + e.getPlayer().getName() + " \u00a7ca \u00a7e" + this.getConfig().getString(e.getPlayer().getName() + ".prangi") + "\u00a7c! Koniecznie doko\u0144cz przenoszenie u\u017cywaj\u0105c \u00a7e/przeniesrange nowynick zako\u0144cz \u00a7cze \u00a7estarego konta\u00a7c, aby nie zosta\u0107 \u00a7ezbanowanym!");
        }
    }
}

