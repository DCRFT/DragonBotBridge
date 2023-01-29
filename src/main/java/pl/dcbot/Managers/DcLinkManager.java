/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.ess3.api.IEssentials
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.ClickEvent
 *  net.md_5.bungee.api.chat.ClickEvent$Action
 *  net.md_5.bungee.api.chat.TextComponent
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.Sound
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package pl.dcbot.Managers;

import net.ess3.api.IEssentials;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.channel.server.ServerChannelCreateEvent;
import org.javacord.api.listener.channel.server.ServerChannelCreateListener;
import pl.dcbot.DragonBotBridge;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DcLinkManager implements CommandExecutor {
    private DragonBotBridge plugin = DragonBotBridge.getInstance();


    public DcLinkManager(DragonBotBridge plugin) {
        this.plugin = plugin;
    }

    public static int generujKod() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return Integer.parseInt(String.format("%06d", number));
    }

    public boolean czyMaPlusa(Player player) {
        return player.hasPermission("essentials.kits.plus");
    }

    public static String getPlayerGroup(Player player) {
        String grupa = null;
        if (player.hasPermission("group.default")) {
            grupa = "Gracz";
        }
        if (player.hasPermission("group.vip")) {
            grupa = "VIP";
        }
        if (player.hasPermission("group.svip")) {
            grupa = "SVIP";
        }
        if (player.hasPermission("group.mvip")) {
            grupa = "MVIP";
        }
        if (player.hasPermission("group.evip")) {
            grupa = "EVIP";
        }
        if (player.hasPermission("group.pomocnik")) {
            grupa = "Pomocnik";
        }
        if (player.hasPermission("group.moderator")) {
            grupa = "Moderator";
        }
        if (player.hasPermission("group.viceadministrator")) {
            grupa = "ViceAdministrator";
        }
        if (player.hasPermission("group.Administrator")) {
            grupa = "Administrator";
        }
        if (player.hasPermission("group.w?a?ciciel")) {
            grupa = "W\u0142a\u015bciciel";
        }
        return grupa;
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("przeniesrange")) {
            Player p = (Player)sender;
            if (args.length == 0) {
                sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a73Przenoszenie rangi\n\u00a7e\u00bb \u00a7eUwaga\u00a7c. \u00a7eKoniecznie\u00a7c przeczytaj \u00a7ewszystkie informacje\u00a7c przed przeniesieniem rangi. \u00a7eNadu\u017cycie\u00a7c tego systemu mo\u017ce wi\u0105za\u0107 si\u0119 z \u00a7ebanem\u00a7c.\n\u00a7e\u00bb \u00a7cSystem przenoszenia rangi jest u\u017cywany do zmiany nicku.\n\u00a7e\u00bb \u00a7cRanga \u00a7e\"+\"\u00a7c nie zostanie przeniesiona.\n\u00a7e\u00bb \u00a7ePami\u0119taj, by w trakcie procesu by\u0107 jednocze\u015bnie na starym i nowym nicku.\n\u00a7e\u00bb \u00a7cDzia\u0142ki i domy zostaj\u0105 przeniesione automatycznie na twoje nowe konto.\n\u00a7e\u00bb Pami\u0119taj\u00a7c, aby \u00a7ezako\u0144czy\u0107 przenoszenie odpowiedni\u0105 komend\u0105\u00a7c oraz o wpisaniu \u00a7e/unregister\u00a7c na \u00a7e/lobby\u00a7c. Niezrobienie tego mo\u017ce grozi\u0107 \u00a7ebanem\u00a7c, nawet na nowym koncie.\n\u00a7e\u00bb \u00a7cPo zatwierdzeniu nie b\u0119dzie mo\u017cliwo\u015bci powrotu, a rzeczy z ekwipunku znikn\u0105.\n\u00a7e\u00bb \u00a7cSystem przenoszenia rangi \u00a7enie s\u0142u\u017cy\u00a7c do dawania komu\u015b rangi.\n\u00a7e\u00bb \u00a7cPrzeczyta\u0142e\u015b instrukcj\u0119? \u00a73U\u017cyj \u00a7e/przeniesrange nick\u00a73, aby rozpocz\u0105\u0107 proces przenoszenia konta. O wszystkich wa\u017cnych informacjach oraz jak poprawnie zako\u0144czy\u0107 proces b\u0119d\u0119 ci\u0119 informowa\u0142 w trakcie przenoszenia.");
                return true;
            }
            if (args[0].equalsIgnoreCase("anuluj")) {
                if (!plugin.getConfig().getBoolean(sender.getName() + ".prangipotwierdz")) {
                    plugin.getConfig().set(sender.getName() + ".prangi", null);
                    plugin.getConfig().set(sender.getName() + ".prangipotwierdz", null);
                    plugin.getConfig().set(sender.getName() + ".blokada", null);
                    plugin.saveConfig();
                    sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7aAnulowa\u0142em przenoszenie rangi, je\u015bli by\u0142e\u015b w jego trakcie przed potwierdzeniem.");
                    return true;
                }
                sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cPrzenoszenie rangi nie zosta\u0142o anulowane, poniewa\u017c zacz\u0105\u0142e\u015b przenoszenie rangi i potwierdzi\u0142e\u015b to na nowym koncie, lecz nie zako\u0144czy\u0142e\u015b przenoszenia. U\u017cyj \u00a7e/przeniesrange nick zakoncz\u00a7c na starym koncie, aby doko\u0144czy\u0107 przenoszenie. Pami\u0119taj tak\u017ce o \u00a7e/unregister\u00a7c na \u00a7e/lobby\u00a7c!");
                return false;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                if (args[0].equalsIgnoreCase("NickNickerYT") || args[0].equalsIgnoreCase("JaneQ") || args[0].equalsIgnoreCase("MikiIgi192") || args[0].equalsIgnoreCase("kalkulator888")) {
                    sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cNo... Raczej nie.");
                    return false;
                }
                if (args.length > 1 && !args[1].equalsIgnoreCase("tak")) {
                    if (args[1].equalsIgnoreCase("zakoncz") || args[1].equalsIgnoreCase("zako\u0144cz")) {
                        if (!args[0].equalsIgnoreCase(plugin.getConfig().getString(sender.getName() + ".prangi"))) {
                            return false;
                        }
                        if (plugin.getConfig().getBoolean(sender.getName() + ".prangipotwierdz")) {
                            p.getInventory().clear();
                            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7aZako\u0144czono. Wyczyszczono tw\u00f3j ekwipunek. Teraz wejd\u017a na \u00a7e/lobby\u00a7c i u\u017cyj \u00a7e/unregister\u00a7c. To ostatni krok - potem mo\u017cesz ju\u017c spokojnie gra\u0107 na nowym koncie \u00a7e" + args[0] + "\u00a7c. Administracja zosta\u0142a powiadomiona o pomy\u015blnym przeniesieniu twojej rangi.\n\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7eUwaga\u00a7c. Niewyrejestrowanie za pomoc\u0105 \u00a7e/unregister\u00a7c na \u00a7e/lobby\u00a7c mo\u017ce skutkowa\u0107 \u00a7ebanem\u00a7c.");
                            Server server = DragonBotBridge.getInstance().getApi().getServerById("483595594653499402").get();
                            Optional<TextChannel> przenoszenie_rang = server.getApi().getTextChannelById("733289180741763104");
                            Optional<TextChannel> zmiana_nicku = server.getApi().getTextChannelById("695291631175204934");
                            if (przenoszenie_rang.isPresent()) {
                                przenoszenie_rang.get().sendMessage("**Survival** \u00bb **" + p.getName() + "** zako\u0144czy\u0142 przenoszenie rangi na konto **" + plugin.getConfig().getString(p.getName() + ".prangi") + "**. Informowanie o zmianie nicku na kana\u0142e #zmiana-nicku. Powinien teraz si\u0119 wyrejestrowa\u0107.");
                            }
                            if (zmiana_nicku.isPresent()) {
                                zmiana_nicku.get().sendMessage(p.getName() + " \u00bb " + plugin.getConfig().getString(p.getName() + ".prangi"));
                            }
                            plugin.getConfig().set(p.getName(), null);
                            plugin.getConfig().set(args[0] + ".prangi", null);
                            plugin.getConfig().set(args[0] + ".zmiananicku", System.currentTimeMillis() / 1000L);
                            plugin.saveConfig();
                            return true;
                        }
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cNie potwierdzi\u0142e\u015b przenoszenia rangi. U\u017cyj \u00a7e/przeniesrange " + p.getName() + "\u00a7e potwierdz \u00a7cna \u00a7enowym koncie\u00a7c, aby kontynuowa\u0107.");
                        return false;
                    }
                    if (args.length > 1 && args[1].equalsIgnoreCase("potwierdz") || args.length > 1 && args[1].equalsIgnoreCase("potwierd\u017a")) {
                        if (plugin.getConfig().getString(args[0] + ".prangi") == null || !plugin.getConfig().getString(args[0] + ".prangi").equalsIgnoreCase(p.getName())) {
                            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cTen gracz nie wys\u0142a\u0142 pro\u015bby do ciebie o przeniesienie rangi lub j\u0105 anulowa\u0142.");
                            return false;
                        }
                        if (Bukkit.getPlayer(args[0]) == null) {
                            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cGracz nie jest online!");
                            return false;
                        }
                        plugin.getConfig().set(args[0] + ".prangipotwierdz", true);
                        plugin.getConfig().set(p.getName() + ".prangi", args[0]);
                        plugin.saveConfig();
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7aPotwierdzono. \u00a7cWywo\u0142aj ponownie komend\u0119 \u00a7e/przeniesrange " + p.getName() + "\u00a7e tak\u00a7c ponownie z konta \u00a7e" + args[0] + "\u00a7c.");
                        Bukkit.getPlayer(args[0]).sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7aPotwierdzono. \u00a7cWywo\u0142aj ponownie komend\u0119 \u00a7e/przeniesrange " + p.getName() + "\u00a7c.");
                        return true;
                    }
                    return false;
                }
                if (args.length > 1 && args[1].equalsIgnoreCase("tak")) {
                    if (Bukkit.getPlayer(args[0]) == sender) {
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cNo co ty, nie przeno\u015b rangi na swoje aktualne konto. Pff.");
                        return false;
                    }
                    if (plugin.getConfig().getString(p.getName() + ".zmiananicku") != null && System.currentTimeMillis() / 1000L - (long)Integer.parseInt(plugin.getConfig().getString(p.getName() + ".zmiananicku")) < 2678400L) {
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cNie mo\u017cesz zmienia\u0107 nicku cz\u0119\u015bciej ni\u017c raz na miesi\u0105c.");
                        plugin.getServer().getLogger().info("[Przenoszenie rangi, " + p.getName() + " -> " + args[0] + ", E:] " + System.currentTimeMillis() / 1000L + " - " + plugin.getConfig().getString(p.getName() + ".zmiananicku") + " !> 2678400 (1 miesi\u0105c)");
                        return false;
                    }
                    if (plugin.getConfig().getString(p.getName() + ".prangi") != null) {
                        if (!plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cCzekaj! Ju\u017c jeste\u015b w trakcie przenoszenia rangi na konto \u00a7e" + plugin.getConfig().getString(p.getName() + ".prangi") + "\u00a7c, ale nie potwierdzi\u0142e\u015b tego na nowym koncie. U\u017cyj \u00a7e/przeniesrange " + p.getName() + "\u00a7e potwierdz\u00a7c na koncie \u00a7e" + plugin.getConfig().getString(p.getName() + ".prangi") + "\u00a7c lub u\u017cyj \u00a7e/przeniesrange anuluj\u00a7c, aby anulowa\u0107 przenoszenie rangi.");
                            return false;
                        }
                        if (!args[0].equalsIgnoreCase(plugin.getConfig().getString(p.getName() + ".prangi"))) {
                            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cNie przenosisz rangi, poda\u0142e\u015b z\u0142y nowy nick konta lub pr\u00f3bujesz wykona\u0107 t\u0119 komend\u0119 z nowego konta. Popraw podane informacje \u00a7e<nick>\u00a7c.\n\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7eUwaga\u00a7c. Je\u015bli naprawd\u0119 przenosisz teraz rang\u0119 na nowe konto, to przenoszenie rangi \u00a7enie\u00a7c zosta\u0142o zako\u0144czone. Niewykonanie polece\u0144 mo\u017ce wi\u0105za\u0107 si\u0119 z \u00a7ebanem\u00a7c. Pami\u0119taj o \u00a7e/przeniesrange nowy nick zako\u0144cz\u00a7c oraz o \u00a7c/unregister \u00a7cna \u00a7c/lobby\u00a7c. Je\u015bli jest to b\u0142\u0105d, zg\u0142o\u015b go.");
                            return false;
                        }
                    }
                    if (plugin.getConfig().getString(args[0] + ".prangi") != null && !plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cTen gracz ju\u017c jest w trakcie przenoszenia rangi. Czy na pewno wywo\u0142ujesz komend\u0119 z dobrego konta?");
                        return false;
                    }
                    sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7aRozpoczynam proces przenoszenia rangi...");
                    plugin.getConfig().set(p.getName() + ".prangi", args[0]);
                    plugin.saveConfig();
                    if (plugin.getConfig().getBoolean(p.getName() + ".blokada")) {
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cCzekaj! Ju\u017c jeste\u015b w trakcie przenoszenia rangi na konto \u00a7e" + plugin.getConfig().getString(p.getName() + ".prangi") + "\u00a7c i to potwierdzi\u0142e\u015b. U\u017cyj \u00a7e/przeniesrange " + plugin.getConfig().getString(p.getName() + ".prangi") + " zako\u0144cz\u00a7c, aby zako\u0144czy\u0107 przenoszenie rangi.");
                        return false;
                    }
                    plugin.saveConfig();
                    if (!plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                        Bukkit.getPlayer(args[0]).sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7e" + p.getName() + "\u00a7c prosi o przeniesienie rangi na twoje konto. \u00a7cAby potwierdzi\u0107, u\u017cyj \u00a7e/przeniesrange " + p.getName() + "\u00a7e potwierdz");
                        p.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft\u00a7e \u00bb \u00a7cTeraz potwierd\u017a na nowym koncie \u00a7e/przeniesrange " + p.getName() + "\u00a7e potwierdz\u00a7c, a potem wywo\u0142aj t\u0119 komend\u0119 \u00a7e(/przeniesrange " + args[0] + " \u00a7etak)\u00a7c na koncie \u00a7e" + p.getName() + "\u00a7c jeszcze raz.");
                        return false;
                    }
                    String grupa = DcLinkManager.getPlayerGroup(p);
                    sender.sendMessage("\u00a7e\u00bb \u00a73Przenosisz rang\u0119 na konto: \u00a7e" + args[0] + "\u00a73.");
                    plugin.getConfig().set(p.getName() + ".blokada", true);
                    plugin.saveConfig();
                    Server server = DragonBotBridge.getInstance().getApi().getServerById("483595594653499402").get();
                    Optional<TextChannel> przenoszenie_rang = server.getApi().getTextChannelById("733289180741763104");
                    if (grupa.equalsIgnoreCase("gracz")) {
                        sender.sendMessage("\u00a7e\u00bb \u00a73Wygl\u0105da na to, \u017ce twoja ranga to \u00a7eGracz\u00a73!\n\u00a7e\u00bb \u00a7cNie przenios\u0119 ci rangi - to nie ma sensu. W twoim wypadku wystarczy, \u017ce zalogujesz si\u0119 na drugie konto, przeniesiesz \u00a7eKuboidy\u00a7c, \u00a7e/home\u00a7c i wyrejestrujesz si\u0119 ze starego za pomoc\u0105 \u00a7e/unregister\u00a7c na \u00a7e/lobby\u00a7c. Pami\u0119taj jednak o \u00a7e/przeniesrange \u00a7e" + args[0] + " \u00a7ezako\u0144cz\u00a7c po przeniesieniu \u00a7eKuboid \u00a7ci \u00a7e/home\u00a7c. Je\u015bli tego nie zrobisz, mo\u017cesz dosta\u0107 \u00a7ebana\u00a7c. Uwaga. Komenda \u00a7e/przeniesrange " + args[0] + " \u00a7ezako\u0144cz\u00a7c usunie \u00a7ewszystkie przedmioty\u00a7c z ekwipunku, wi\u0119c u\u017cyj jej, gdy przeniesiesz ju\u017c wszystko\u00a7c.");
                        if (przenoszenie_rang.isPresent()) {
                            przenoszenie_rang.get().sendMessage("**Survival** \u00bb **" + p.getName() + "** rozpoczyna przenoszenie rangi **Gracz** na nowe konto **" + args[0] + "**. Procesu jeszcze nie zako\u0144czy\u0142.");
                        }
                        return false;
                    }
                    if (grupa.equalsIgnoreCase("vip") || grupa.equalsIgnoreCase("svip") || grupa.equalsIgnoreCase("mvip") || grupa.equalsIgnoreCase("evip")) {
                        if (this.czyMaPlusa(p)) {
                            sender.sendMessage("\u00a7e\u00bb \u00a73Twoja ranga to \u00a7e" + grupa + "+ \u00a73. \u00a7cPomijanie rangi +.");
                        } else {
                            sender.sendMessage("\u00a7e\u00bb \u00a73Twoja ranga to \u00a7e" + grupa + "\u00a73.");
                        }
                        if (przenoszenie_rang.isPresent()) {
                            przenoszenie_rang.get().sendMessage("**Survival** \u00bb **" + p.getName() + "** rozpoczyna przenoszenie rangi **" + grupa + "** na nowe konto **" + args[0] + "**. Procesu jeszcze nie zako\u0144czy\u0142.");
                        }
                        if (grupa.equalsIgnoreCase("vip")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add vip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.vip false");
                        }
                        if (grupa.equalsIgnoreCase("svip")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add vip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add svip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.vip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.svip false");
                        }
                        if (grupa.equalsIgnoreCase("mvip")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add vip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add svip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add mvip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.vip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.svip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.mvip false");
                        }
                        if (grupa.equalsIgnoreCase("evip")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add vip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add svip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add mvip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " parent add evip");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.vip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.svip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.mvip false");
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + args[0] + " permission set essentials.kits.evip false");
                        }
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset essentials.kits.vip");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset essentials.kits.svip");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset essentials.kits.mvip");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission unset essentials.kits.evip");
                        sender.sendMessage("\u00a7e\u00bb " + args[0] + " \u00a7adodano: \u00a7e" + grupa);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent set default");
                        sender.sendMessage("\u00a7e\u00bb " + p.getName() + " \u00a7austawiono: \u00a7eGracz");
                        sender.sendMessage("\u00a7e\u00bb " + p.getName() + " \u00a7anaprawiono dost\u0119p do zestaw\u00f3w");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ps migrate " + p.getName() + " " + args[0]);
                        sender.sendMessage("\u00a7e\u00bb " + p.getName() + " \u00a7aprzeniesiono dzia\u0142ki na nowe konto");
                        IEssentials essentials = (IEssentials)Bukkit.getPluginManager().getPlugin("Essentials");
                        List<String> domy = essentials.getUser(p).getHomes();
                        for (String s : domy) {
                            try {
                                Location dom = essentials.getUser(p).getHome(s);
                                essentials.getUser(p).delHome(s);
                                essentials.getUser(p).setHome(s, dom);
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        sender.sendMessage("\u00a7e\u00bb " + p.getName() + " \u00a7aprzeniesiono domy na nowe konto");
                        sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7aPierwszy etap przenoszenia rangi zako\u0144czony. Przeniesiono rang\u0119 na nowe konto. \u00a7cPrzenie\u015b teraz swoje przedmioty i inne potrzebne rzeczy, a nast\u0119pnie zako\u0144cz przenoszenie komend\u0105 \u00a7e/przeniesrange " + args[0] + " \u00a7ezako\u0144cz\u00a7c. Uwaga. Komenda \u00a7e/przeniesrange " + args[0] + " \u00a7ezako\u0144cz\u00a7c usunie \u00a7ewszystkie przedmioty\u00a7c z ekwipunku, wi\u0119c u\u017cyj jej dopiero, gdy przeniesiesz ju\u017c wszystko\u00a7c.\n\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7eUwaga!\u00a7c. Proces nie zosta\u0142 uko\u0144czony - pami\u0119taj koniecznie o zako\u0144czeniu \u00a7e/przeniesrange " + args[0] + " \u00a7ezako\u0144cz\u00a7c. Pomini\u0119cie tego kroku mo\u017ce wi\u0105za\u0107 si\u0119 z \u00a7ebanem\u00a7c.");
                        return true;
                    }
                    sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cWyst\u0105pi\u0142 b\u0142\u0105d. Zg\u0142o\u015b go administacji. Zaraz, zaraz... Czy ty nie jeste\u015b z administracji? [Err.0 g:" + grupa + "\u00a7c]");
                    przenoszenie_rang.get().sendMessage("**Survival** \u00bb **" + p.getName() + "** napotka\u0142 b\u0142\u0105d podczas przenoszenia rangi **" + grupa + "** na nowe konto **" + args[0] + "**. [Err.0 g:" + grupa + "]");
                    plugin.getConfig().set(p.getName(), null);
                    plugin.getConfig().set(args[0], null);
                    plugin.saveConfig();
                    return false;
                }
                sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cCzy na pewno? Potwierd\u017a \u00a7e/przeniesrange " + args[0] + " \u00a7etak\u00a7c. Pami\u0119taj, \u017ce procesu nie mo\u017cna potem anulowa\u0107.");
                return true;
            }
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cNie poda\u0142e\u015b poprawnego nicku gracza. Sprawd\u017a te\u017c, czy jeste\u015b aktualnie na nowym nicku na serwerze. Je\u015bli nie, wejd\u017a jednocze\u015bnie na nowym koncie \u00a7e" + args[0] + "\u00a7c i spr\u00f3buj ponownie, wykonuj\u0105c komend\u0119 z tego konta \u00a7e" + p.getName() + "\u00a7c.");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("anulujprzenoszenie")) {
            if (!sender.hasPermission("cb.adm")) {
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cPodaj nick gracza.");
                return false;
            }
            plugin.getConfig().set(args[0], null);
            plugin.saveConfig();
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cAnulowano przenoszenie rangi graczowi \u00a7e" + args[0]);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("discord")) {
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a76Nasz serwer \u00a7eDiscord \u00a76to \u00a7ediscord.gg/7pPNbUU\u00a76. Znajduj\u0105 si\u0119 tam wszelkie og\u0142oszenia dotycz\u0105ce serwera, mo\u017cesz tam uzyska\u0107 pomoc b\u0105d\u017a zg\u0142osi\u0107 b\u0142\u0105d.\n\u00a76Aby po\u0142\u0105czy\u0107 konto \u00a7eMinecraft \u00a76z \u00a7eDiscordem \u00a7ena naszym serwerze \u00a7eDiscord\u00a76, wpisz \u00a7e/dclink\u00a76.");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("dclink")) {
            final Integer kod = DcLinkManager.generujKod();
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7aGeneruj\u0119 kod...");
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new Runnable(){

                @Override
                public void run() {
                    DatabaseManager.openConnection();
                    String usun = "DELETE FROM `" + DatabaseManager.tabela + "` WHERE nick='" + sender.getName() + "'";
                    String dodaj = "INSERT INTO `" + DatabaseManager.tabela + "` (nick, kod) VALUES ('" + sender.getName() + "', " + kod + ")";

                    plugin.getServer().getLogger().info(usun);
                    plugin.getServer().getLogger().info(dodaj);

                    DatabaseManager.get().executeStatement(usun);
                    DatabaseManager.get().executeStatement(dodaj);

                    sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a76Tw\u00f3j kod do po\u0142\u0105czenia konta Discord to \u00a7e" + kod + "\u00a76. Wpisz /dclink \u00a7e" + kod + " \u00a76na serwerze Discord.");
                }
            });
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("zglos")) {
            if (args.length == 0) {
                sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cPoprawne u\u017cycie: \u00a7e/zglos <tre\u015b\u0107 zg\u0142oszenia>");
                return false;
            }
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7aWiadomo\u015b\u0107 wys\u0142ano do administracji!");
            Integer numer = Integer.parseInt(plugin.getConfig().getString("numer")) + 1;
            StringBuilder sb = new StringBuilder();
            for (String arg : args) {
                sb.append(arg).append(" ");
            }
            String allArgs = sb.toString().trim();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            String time = dateFormat.format(date);
            Server server = DragonBotBridge.getInstance().getApi().getServerById("483595594653499402").get();
            Optional<TextChannel> channel = server.getApi().getTextChannelById(plugin.getConfig().getString("kanal"));
            String nick = sender.getName();
            if (nick.equalsIgnoreCase("CONSOLE")) {
                nick = "Sklep";
            }
            if (sender instanceof Player) {
                Player p = (Player)sender;
                Location loc = p.getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();

                if (org.apache.commons.lang3.StringUtils.containsIgnoreCase("grief", allArgs) || org.apache.commons.lang3.StringUtils.containsIgnoreCase("grif", allArgs)) {
                    allArgs = allArgs + " Lokalizacja gracza: " + x + " " + y + " " + z;
                }
                for (Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("panel.adm")) continue;
                    TextComponent message = new TextComponent(TextComponent.fromLegacyText("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cZg\u0142oszenie\u00a7e #" + numer + " \u00a7cod \u00a7e" + nick + "\u00a7c:\u00a7e " + allArgs + "\n\u00a7e\u00bb \u00a76Kliknij, aby si\u0119 teleportowa\u0107."));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tppos " + x + " " + y + " " + z));
                    o.spigot().sendMessage(message);
                    if (!(o instanceof Player)) continue;
                    o.playSound(o.getLocation(), Sound.BLOCK_ANVIL_LAND, 100.0f, 1.0f);
                }
            } else {
                for (Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("panel.adm")) continue;
                    TextComponent message = new TextComponent(TextComponent.fromLegacyText("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cZg\u0142oszenie\u00a7e #" + numer + " \u00a7cod \u00a7e" + nick + "\u00a7c:\u00a7e " + allArgs));
                    o.spigot().sendMessage(message);
                    if (!(o instanceof Player)) continue;
                    o.playSound(o.getLocation(), Sound.BLOCK_ANVIL_LAND, 100.0f, 1.0f);
                }
            }
            plugin.getServer().getLogger().info("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cZg\u0142oszenie\u00a7e #" + numer + " od " + nick + ": " + allArgs);
            if (channel.isPresent()) {
                EmbedBuilder embed = new EmbedBuilder().setAuthor("Zg\u0142oszenie #" + numer).addField(nick + ":", allArgs).setColor(Color.CYAN).setFooter("DragonBot " + plugin.getDescription().getVersion() + " \u2022 " + time, "http://dcrft.pl/uploads/5bb8d859bb152e08470e24f2/dclogo3_shadow_alpha.png");
                channel.get().sendMessage(embed);
                plugin.getConfig().set("numer", String.valueOf(numer));
                plugin.saveConfig();
                return true;
            }
            sender.sendMessage("\u00a7e\u00a7lDragon\u00a76\u00a7lCraft \u00a7e\u00bb \u00a7cWyst\u0105pi\u0142 b\u0142\u0105d. Zg\u0142o\u015b go administracji \u00a7e[Err.0 c:brak]\u00a7c.");
            return false;
        }
        return false;
    }
}

