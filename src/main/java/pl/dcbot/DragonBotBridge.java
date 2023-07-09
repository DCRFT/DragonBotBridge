package pl.dcbot;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import pl.dcbot.Managers.DatabaseManager;
import pl.dcbot.Managers.DcLinkManager;

import java.util.concurrent.CompletableFuture;

public class
DragonBotBridge
extends JavaPlugin
implements Listener, PluginMessageListener {
    private DiscordApi api;
    private static DragonBotBridge instance;


    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);


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
            this.getLogger().warning("§cNie można zalogować się do Discord! Wyłaczanie...");
            this.getPluginLoader().disablePlugin(this);
            return null;
        });
        this.getCommand("zglos").setExecutor(new DcLinkManager(this));
        this.getCommand("discord").setExecutor(new DcLinkManager(this));
        this.getCommand("dclink").setExecutor(new DcLinkManager(this));
        this.getCommand("przeniesrange").setExecutor(new DcLinkManager(this));
        this.getCommand("anulujprzenoszenie").setExecutor(new DcLinkManager(this));
        this.getCommand("pluginmsgtest").setExecutor(new DcLinkManager(this));


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
        this.getLogger().info("§aPołączono z Discordem jako " + api.getYourself().getDiscriminatedName());
    }

    public DiscordApi getApi() {
        return this.api;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (this.getConfig().getString(e.getPlayer().getName() + ".prangi") != null) {
            e.getPlayer().sendMessage("§e§lDragon§6§lCraft §e» §cHej! Jesteś w trakcie przenoszenia rangi między kontami §e" + e.getPlayer().getName() + " §ca §e" + this.getConfig().getString(e.getPlayer().getName() + ".prangi") + "§c! Koniecznie dokończ przenoszenie używając §e/przeniesrange nowynick zakończ §cze §estarego konta§c, aby nie zostać §ezbanowanym!");
        }
    }
    public void sendMessage(String message, Player p) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DragonCraftBridge");
        out.writeUTF(message);
        p.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        getLogger().info("1: " + channel);
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        getLogger().info(subchannel);

        if (subchannel.equalsIgnoreCase("DragonCraftBridge")) {
            getLogger().info(channel + " " + subchannel + " " + " success");
            String utf = in.readUTF();
            getLogger().warning(utf);
            getServer().dispatchCommand(getServer().getConsoleSender(), utf);
        }
    }
}

