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
            grupa = "Właściciel";
        }
        return grupa;
    }

    public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("pluginmsgtest")) {
            String argsSend = String.join(" ", args);
            plugin.sendMessage(cmd.getName() + " " + argsSend, (Player) sender);
        }
        if (cmd.getName().equalsIgnoreCase("przeniesrange")) {
            Player p = (Player)sender;
            if (args.length == 0) {
                sender.sendMessage("§e§lDragon§6§lCraft §e» §3Przenoszenie rangi\n§e» §eUwaga§c. §eKoniecznie§c przeczytaj §ewszystkie informacje§c przed przeniesieniem rangi. §eNadużycie§c tego systemu może wiązać się z §ebanem§c.\n§e» §cSystem przenoszenia rangi jest używany do zmiany nicku.\n§e» §cRanga §e\"+\"§c nie zostanie przeniesiona.\n§e» §ePamiętaj, by w trakcie procesu być jednocześnie na starym i nowym nicku.\n§e» §cDziałki, wyspy i domy (z obecnego serwera) zostaną przeniesione automatycznie na twoje nowe konto.\n§e» Pamiętaj§c, aby §ezakończyć przenoszenie odpowiednią komendą§c oraz o wpisaniu §e/unregister§c na §e/lobby§c. Niezrobienie tego może grozić §ebanem§c, nawet na nowym koncie.\n§e» §cPo zatwierdzeniu nie będzie możliwości powrotu, a rzeczy z ekwipunku znikną.\n§e» §cSystem przenoszenia rangi §enie służy§c do dawania komuś rangi.\n§e» §cPrzeczytałeś instrukcję? §3Użyj §e/przeniesrange nick§3, aby rozpocząć proces przenoszenia konta. O wszystkich ważnych informacjach oraz jak poprawnie zakończyć proces będę cię informował w trakcie przenoszenia.");
                return true;
            }
            if (args[0].equalsIgnoreCase("anuluj")) {
                if (!plugin.getConfig().getBoolean(sender.getName() + ".prangipotwierdz")) {
                    plugin.getConfig().set(sender.getName() + ".prangi", null);
                    plugin.getConfig().set(sender.getName() + ".prangipotwierdz", null);
                    plugin.getConfig().set(sender.getName() + ".blokada", null);
                    plugin.saveConfig();
                    sender.sendMessage("§e§lDragon§6§lCraft§e » §aAnulowałem przenoszenie rangi, jeśli byłeś w jego trakcie przed potwierdzeniem.");
                    return true;
                }
                sender.sendMessage("§e§lDragon§6§lCraft§e » §cPrzenoszenie rangi nie zostało anulowane, ponieważ zacząłeś przenoszenie rangi i potwierdziłeś to na nowym koncie, lecz nie zakończyłeś przenoszenia. Użyj §e/przeniesrange nick zakończ§c na starym koncie na aktualnym trybie, aby dokończyć przenoszenie. Pamiętaj także o §e/unregister§c na §e/lobby§c!");
                return false;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                if (args[0].equalsIgnoreCase("NickNickerYT") || args[0].equalsIgnoreCase("JaneQ") || args[0].equalsIgnoreCase("MikiIgi192") || args[0].equalsIgnoreCase("kalkulator888")) {
                    sender.sendMessage("§e§lDragon§6§lCraft§e » §cNo... Raczej nie.");
                    return false;
                }
                if (args.length > 1 && !args[1].equalsIgnoreCase("tak")) {
                    if (args[1].equalsIgnoreCase("zakoncz") || args[1].equalsIgnoreCase("zakończ")) {
                        if (!args[0].equalsIgnoreCase(plugin.getConfig().getString(sender.getName() + ".prangi"))) {
                            return false;
                        }
                        if (plugin.getConfig().getBoolean(sender.getName() + ".prangipotwierdz")) {
                            p.getInventory().clear();
                            sender.sendMessage("§e§lDragon§6§lCraft §e» §aZakończono. Wyczyszczono twój ekwipunek. Teraz wejdź na §e/lobby§c i użyj §e/unregister§c. To ostatni krok - potem możesz już spokojnie grać na nowym koncie §e" + args[0] + "§c. Administracja została powiadomiona o pomyślnym przeniesieniu twojej rangi.\n§e§lDragon§6§lCraft §e» §eUwaga§c. Niewyrejestrowanie za pomocą §e/unregister§c na §e/lobby§c może skutkować §ebanem§c.");
                            Server server = DragonBotBridge.getInstance().getApi().getServerById("483595594653499402").get();
                            Optional<TextChannel> przenoszenie_rang = server.getApi().getTextChannelById("733289180741763104");
                            Optional<TextChannel> zmiana_nicku = server.getApi().getTextChannelById("695291631175204934");
                            if (przenoszenie_rang.isPresent()) {
                                przenoszenie_rang.get().sendMessage("**Survival** » **" + p.getName() + "** zakończył przenoszenie rangi na konto **" + plugin.getConfig().getString(p.getName() + ".prangi") + "**. Informowanie o zmianie nicku na kanałe #zmiana-nicku. Powinien teraz się wyrejestrować.");
                            }
                            if (zmiana_nicku.isPresent()) {
                                zmiana_nicku.get().sendMessage(p.getName() + " » " + plugin.getConfig().getString(p.getName() + ".prangi"));
                            }
                            plugin.getConfig().set(p.getName(), null);
                            plugin.getConfig().set(args[0] + ".prangi", null);
                            plugin.getConfig().set(args[0] + ".zmiananicku", System.currentTimeMillis() / 1000L);
                            plugin.saveConfig();
                            return true;
                        }
                        sender.sendMessage("§e§lDragon§6§lCraft§e » §cNie potwierdziłeś przenoszenia rangi. Użyj §e/przeniesrange " + p.getName() + "§e potwierdz §cna §enowym koncie§c, aby kontynuować.");
                        return false;
                    }
                    if (args.length > 1 && args[1].equalsIgnoreCase("potwierdz") || args.length > 1 && args[1].equalsIgnoreCase("potwierdź")) {
                        if (plugin.getConfig().getString(args[0] + ".prangi") == null || !plugin.getConfig().getString(args[0] + ".prangi").equalsIgnoreCase(p.getName())) {
                            sender.sendMessage("§e§lDragon§6§lCraft§e » §cTen gracz nie wysłał prośby do ciebie o przeniesienie rangi lub ją anulował. §cUpewnij się, że jesteś na tym trybie, na którym rozpoczęto przenoszenie rangi.");
                            return false;
                        }
                        if (Bukkit.getPlayer(args[0]) == null) {
                            sender.sendMessage("§e§lDragon§6§lCraft§e » §cGracz nie jest online!");
                            return false;
                        }
                        plugin.getConfig().set(args[0] + ".prangipotwierdz", true);
                        plugin.getConfig().set(p.getName() + ".prangi", args[0]);
                        plugin.saveConfig();
                        sender.sendMessage("§e§lDragon§6§lCraft§e » §aPotwierdzono. §cWywołaj ponownie komendę §e/przeniesrange " + p.getName() + "§e tak§c ponownie z konta §e" + args[0] + "§c.");
                        Bukkit.getPlayer(args[0]).sendMessage("§e§lDragon§6§lCraft§e » §aPotwierdzono. §cWywołaj ponownie komendę §e/przeniesrange " + p.getName() + "§c.");
                        return true;
                    }
                    return false;
                }
                if (args.length > 1 && args[1].equalsIgnoreCase("tak")) {
                    if (Bukkit.getPlayer(args[0]) == sender) {
                        sender.sendMessage("§e§lDragon§6§lCraft §e» §cNo co ty, nie przenoś rangi na swoje aktualne konto. Pff.");
                        return false;
                    }
                    if (plugin.getConfig().getString(p.getName() + ".zmiananicku") != null && System.currentTimeMillis() / 1000L - (long)Integer.parseInt(plugin.getConfig().getString(p.getName() + ".zmiananicku")) < 2678400L) {
                        sender.sendMessage("§e§lDragon§6§lCraft§e » §cNie możesz zmieniać nicku częściej niż raz na miesiąc.");
                        plugin.getServer().getLogger().info("[Przenoszenie rangi, " + p.getName() + " -> " + args[0] + ", E:] " + System.currentTimeMillis() / 1000L + " - " + plugin.getConfig().getString(p.getName() + ".zmiananicku") + " !> 2678400 (1 miesiąc)");
                        return false;
                    }
                    if (plugin.getConfig().getString(p.getName() + ".prangi") != null) {
                        if (!plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                            sender.sendMessage("§e§lDragon§6§lCraft §e» §cCzekaj! Już jesteś w trakcie przenoszenia rangi na konto §e" + plugin.getConfig().getString(p.getName() + ".prangi") + "§c, ale nie potwierdziłeś tego na nowym koncie. Użyj §e/przeniesrange " + p.getName() + "§e potwierdz§c na koncie §e" + plugin.getConfig().getString(p.getName() + ".prangi") + "§c lub użyj §e/przeniesrange anuluj§c, aby anulować przenoszenie rangi.");
                            return false;
                        }
                        if (!args[0].equalsIgnoreCase(plugin.getConfig().getString(p.getName() + ".prangi"))) {
                            sender.sendMessage("§e§lDragon§6§lCraft §e» §cNie przenosisz rangi, podałeś zły nowy nick konta lub próbujesz wykonać tę komendę z nowego konta. Popraw podane informacje §e<nick>§c.\n§e§lDragon§6§lCraft §e» §eUwaga§c. Jeśli naprawdę przenosisz teraz rangę na nowe konto, to przenoszenie rangi §enie§c zostało zakończone. Niewykonanie poleceń może wiązać się z §ebanem§c. Pamiętaj o wywołaniu §e/przeniesrange nowy nick zakończ§c na aktualnym trybie oraz o §c/unregister §cna §c/lobby§c. Jeśli jest to błąd, zgłoś go.");
                            return false;
                        }
                    }
                    if (plugin.getConfig().getString(args[0] + ".prangi") != null && !plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                        sender.sendMessage("§e§lDragon§6§lCraft §e» §cTen gracz już jest w trakcie przenoszenia rangi. Czy na pewno wywołujesz komendę z dobrego konta?");
                        return false;
                    }
                    sender.sendMessage("§e§lDragon§6§lCraft §e» §aRozpoczynam proces przenoszenia rangi...");
                    plugin.getConfig().set(p.getName() + ".prangi", args[0]);
                    plugin.saveConfig();
                    if (plugin.getConfig().getBoolean(p.getName() + ".blokada")) {
                        sender.sendMessage("§e§lDragon§6§lCraft §e» §cCzekaj! Już jesteś w trakcie przenoszenia rangi na konto §e" + plugin.getConfig().getString(p.getName() + ".prangi") + "§c i to potwierdziłeś. Użyj §e/przeniesrange " + plugin.getConfig().getString(p.getName() + ".prangi") + " zakończ§c na aktualnym trybie, aby zakończyć przenoszenie rangi.");
                        return false;
                    }
                    plugin.saveConfig();
                    if (!plugin.getConfig().getBoolean(p.getName() + ".prangipotwierdz")) {
                        Bukkit.getPlayer(args[0]).sendMessage("§e§lDragon§6§lCraft§e » §e" + p.getName() + "§c prosi o przeniesienie rangi na twoje konto. §cAby potwierdzić, użyj §e/przeniesrange " + p.getName() + "§e potwierdz");
                        p.sendMessage("§e§lDragon§6§lCraft§e » §cTeraz potwierdź na nowym koncie §e/przeniesrange " + p.getName() + "§e potwierdz§c, a potem wywołaj tę komendę §e(/przeniesrange " + args[0] + " §etak)§c na koncie §e" + p.getName() + "§c jeszcze raz.");
                        return false;
                    }
                    String grupa = DcLinkManager.getPlayerGroup(p);
                    sender.sendMessage("§e» §3Przenosisz rangę na konto: §e" + args[0] + "§3.");
                    plugin.getConfig().set(p.getName() + ".blokada", true);
                    plugin.saveConfig();
                    Server server = DragonBotBridge.getInstance().getApi().getServerById("483595594653499402").get();
                    Optional<TextChannel> przenoszenie_rang = server.getApi().getTextChannelById("733289180741763104");
                    if (grupa.equalsIgnoreCase("gracz")) {
                        sender.sendMessage("§e» §3Wygląda na to, że twoja ranga to §eGracz§3!\n§e» §cW takim razie nie przeniosę ci rangi. Zaraz nastąpi przeniesienie działek, wysp i domów z obecnego serwera. Pamiętaj jednak o §e/przeniesrange §e" + args[0] + " §ezakończ§c na aktualnym trybie po przeniesieniu wszystkich rzeczy. Jeśli tego nie zrobisz, możesz dostać §ebana§c. Uwaga. Komenda §e/przeniesrange " + args[0] + " §ezakończ§c usunie §ewszystkie przedmioty§c z ekwipunku, więc użyj jej, gdy przeniesiesz już wszystko§c.");
                        if (przenoszenie_rang.isPresent()) {
                            przenoszenie_rang.get().sendMessage("**Survival** » **" + p.getName() + "** rozpoczyna przenoszenie rangi **Gracz** na nowe konto **" + args[0] + "**. Procesu jeszcze nie zakończył.");
                        }
                        //return false;
                    }
                    if (grupa.equalsIgnoreCase("gracz") || grupa.equalsIgnoreCase("vip") || grupa.equalsIgnoreCase("svip") || grupa.equalsIgnoreCase("mvip") || grupa.equalsIgnoreCase("evip")) {
                        if (this.czyMaPlusa(p)) {
                            sender.sendMessage("§e» §3Twoja ranga to §e" + grupa + "+ §3. §cPomijanie rangi +.");
                        } else {
                            sender.sendMessage("§e» §3Twoja ranga to §e" + grupa + "§3.");
                        }
                        if (przenoszenie_rang.isPresent()) {
                            przenoszenie_rang.get().sendMessage("**Survival** » **" + p.getName() + "** rozpoczyna przenoszenie rangi **" + grupa + "** na nowe konto **" + args[0] + "**. Procesu jeszcze nie zakończył.");
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
                        sender.sendMessage("§e» " + args[0] + " §adodano: §e" + grupa);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " parent set default");
                        sender.sendMessage("§e» " + p.getName() + " §austawiono: §eGracz");
                        sender.sendMessage("§e» " + p.getName() + " §anaprawiono dostęp do zestawów");

                        boolean skyblock = plugin.getConfig().getBoolean("skyblock");

                        if(skyblock){
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is admin add " + p.getName() + " " + args[0]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is admin setleader " + p.getName() + " " + args[0]);
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "is admin kick " + p.getName());
                            sender.sendMessage("§e» " + p.getName() + " §aprzeniesiono wyspę na nowe konto");

                            plugin.sendMessage("ps migrate " + p.getName() + " " + args[0] , p);
                            sender.sendMessage("§e» " + p.getName() + " §aprzeniesiono działki na nowe konto");

                        } else {

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ps migrate " + p.getName() + " " + args[0]);
                            sender.sendMessage("§e» " + p.getName() + " §aprzeniesiono działki na nowe konto");

                            plugin.sendMessage("is admin add " + p.getName() + " " + args[0], p);
                            plugin.sendMessage("is admin setleader " + p.getName() + " " + args[0], p);
                            plugin.sendMessage("is admin kick " + p.getName(), p);
                            sender.sendMessage("§e» " + p.getName() + " §aprzeniesiono wyspę na nowe konto");

                        }
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
                        sender.sendMessage("§e» " + p.getName() + " §aprzeniesiono domy z obecnego podserwera na nowe konto");
                        sender.sendMessage("§e§lDragon§6§lCraft §e» §aPierwszy etap przenoszenia rangi zakończony. Przeniesiono rangę, wyspy i działki na nowe konto. Sprawdź poprawność operacji i Przenieś teraz swoje przedmioty i inne potrzebne rzeczy, a następnie zakończ przenoszenie komendą §e/przeniesrange " + args[0] + " §ezakończ§a na aktualnym trybie. §cUwaga. Komenda §e/przeniesrange " + args[0] + " §ezakończ§c usunie §ewszystkie przedmioty§c z ekwipunku, więc użyj jej dopiero, gdy przeniesiesz już wszystko§c.\n§e§lDragon§6§lCraft §e» §eUwaga!§c. Proces nie został ukończony - pamiętaj koniecznie o zakończeniu §e/przeniesrange " + args[0] + " §ezakończ§c na aktualnym trybie. Pominięcie tego kroku może wiązać się z §ebanem§c.");
                        return true;
                    }
                    sender.sendMessage("§e§lDragon§6§lCraft §e» §cWystąpił błąd. Zgłoś go administacji. Zaraz, zaraz... Czy ty nie jesteś z administracji? [Err.0 g:" + grupa + "§c]");
                    przenoszenie_rang.get().sendMessage("**Survival** » **" + p.getName() + "** napotkał błąd podczas przenoszenia rangi **" + grupa + "** na nowe konto **" + args[0] + "**. [Err.0 g:" + grupa + "]");
                    plugin.getConfig().set(p.getName(), null);
                    plugin.getConfig().set(args[0], null);
                    plugin.saveConfig();
                    return false;
                }
                sender.sendMessage("§e§lDragon§6§lCraft §e» §cCzy na pewno? Potwierdź §e/przeniesrange " + args[0] + " §etak§c. Pamiętaj, że procesu nie można potem anulować.");
                return true;
            }
            sender.sendMessage("§e§lDragon§6§lCraft §e» §cTen gracz nie jest online.\nSprawdź, czy jesteś aktualnie na nowym nicku na serwerze (na tym samym trybie).\nJeśli nie, wejdź jednocześnie na nowym koncie §e" + args[0] + "§c i spróbuj ponownie, wywołując jeszcze raz komendę z aktualnego  konta §e" + p.getName() + "§c.");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("anulujprzenoszenie")) {
            if (!sender.hasPermission("cb.adm")) {
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage("§e§lDragon§6§lCraft §e» §cPodaj nick gracza.");
                return false;
            }
            plugin.getConfig().set(args[0], null);
            plugin.saveConfig();
            sender.sendMessage("§e§lDragon§6§lCraft §e» §cAnulowano przenoszenie rangi graczowi §e" + args[0]);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("discord")) {
            sender.sendMessage("§e§lDragon§6§lCraft §e» §6Nasz serwer §eDiscord §6to §ediscord.gg/7pPNbUU§6. Znajdują się tam wszelkie ogłoszenia dotyczące serwera, możesz tam uzyskać pomoc bądź zgłosić błąd.\n§6Aby połączyć konto §eMinecraft §6z §eDiscordem §ena naszym serwerze §eDiscord§6, wpisz §e/dclink§6.");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("dclink")) {
            final Integer kod = DcLinkManager.generujKod();
            sender.sendMessage("§e§lDragon§6§lCraft §e» §aGeneruję kod...");
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

                    sender.sendMessage("§e§lDragon§6§lCraft §e» §6Twój kod do połączenia konta Discord to §e" + kod + "§6. Wpisz /dclink §e" + kod + " §6na serwerze Discord.");
                }
            });
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("zglos")) {
            if (args.length == 0) {
                sender.sendMessage("§e§lDragon§6§lCraft §e» §cPoprawne użycie: §e/zglos <treść zgłoszenia>");
                return false;
            }
            sender.sendMessage("§e§lDragon§6§lCraft §e» §aWiadomość wysłano do administracji!");
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
                    TextComponent message = new TextComponent(TextComponent.fromLegacyText("§e§lDragon§6§lCraft §e» §cZgłoszenie§e #" + numer + " §cod §e" + nick + "§c:§e " + allArgs + "\n§e» §6Kliknij, aby się teleportować."));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tppos " + x + " " + y + " " + z));
                    o.spigot().sendMessage(message);
                    if (!(o instanceof Player)) continue;
                    o.playSound(o.getLocation(), Sound.BLOCK_ANVIL_LAND, 100.0f, 1.0f);
                }
            } else {
                for (Player o : Bukkit.getOnlinePlayers()) {
                    if (!o.hasPermission("panel.adm")) continue;
                    TextComponent message = new TextComponent(TextComponent.fromLegacyText("§e§lDragon§6§lCraft §e» §cZgłoszenie§e #" + numer + " §cod §e" + nick + "§c:§e " + allArgs));
                    o.spigot().sendMessage(message);
                    if (!(o instanceof Player)) continue;
                    o.playSound(o.getLocation(), Sound.BLOCK_ANVIL_LAND, 100.0f, 1.0f);
                }
            }
            plugin.getServer().getLogger().info("§e§lDragon§6§lCraft §e» §cZgłoszenie§e #" + numer + " od " + nick + ": " + allArgs);
            if (channel.isPresent()) {
                EmbedBuilder embed = new EmbedBuilder().setAuthor("Zgłoszenie #" + numer).addField(nick + ":", allArgs).setColor(Color.CYAN).setFooter("DragonBot " + plugin.getDescription().getVersion() + " • " + time, "http://dcrft.pl/uploads/5bb8d859bb152e08470e24f2/dclogo3_shadow_alpha.png");
                channel.get().sendMessage(embed);
                plugin.getConfig().set("numer", String.valueOf(numer));
                plugin.saveConfig();
                return true;
            }
            sender.sendMessage("§e§lDragon§6§lCraft §e» §cWystąpił błąd. Zgłoś go administracji §e[Err.0 c:brak]§c.");
            return false;
        }
        return false;
    }
}

