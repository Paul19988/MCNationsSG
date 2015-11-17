package net.mcnations.sg.gamestates;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.events.GamePreRegisterPlayerEvent;
import net.mcnations.engine.events.GameRegisterPlayerEvent;
import net.mcnations.engine.utils.xp.XP;
import net.mcnations.engine.utils.xp.XPLoader;
import net.mcnations.engine.utils.xp.manager.XPManager;
import net.mcnations.sg.API;
import net.mcnations.sg.Core;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by PaulCodesUK on 13/11/2015.
 */
public class LobbyState extends GameState {

    public LobbyState(Engine engine, String rawName, String displayName) {
        super(engine, rawName, displayName);
    }

    @Override
    public boolean onStateBegin() {
        regListener(this);
        Bukkit.broadcastMessage("LOBBY START");
        return true;
    }

    @Override
    public boolean onStateEnd() {
        unregListener(this);
        Bukkit.broadcastMessage("LOBBY END");
        return true;
    }

    int timer = 60;
    @Override
    public void tick() {

        for(Player p : Bukkit.getOnlinePlayers()) {
            API.sendBossBar(p);
            API.updateScoreboard(p, new String[]{
                    null,
                    "§6§lCurrent Map:",
                    "§oVoting",
                    "§6§lPlayers Online:",
                    ChatColor.ITALIC + "" + engine.getGamePlayers().size() + "/" + engine.getGameMaxPlayers(),
                    "§6§lYour XP:",
                    "§o" + XPManager.getXpProfile(p).getXp(),
                    "§6§lNation Coins:",
                    "§oComing Soon",
                    "",
                    "§e§lmcnations.net"
            });
        }

        timer--;
        if(timer == 0) {
            if(engine.getGamePlayers().size() >= engine.getGameMinPlayers()) {
                engine.nextState();
            }else{
                Bukkit.broadcastMessage(Core.prefix + "Not enough players, Restarting timer " + ChatColor.GREEN + "(" + engine.getGamePlayers().size() + "/" + engine.getGameMaxPlayers() + ")" );
                timer = 60;
            }
        } else {
            if(timer % 10 == 0) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 5){
                Bukkit.broadcastMessage(Core.prefix + "Map has now been chosen!");
                API.checkMapVotes();
                Bukkit.broadcastMessage(Core.prefix + "Chosen map is: " + API.getCurrentMap().getName());
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 4) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 3) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 2) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 1) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " second.");
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getItem().getType().equals(Material.ENDER_PEARL)) {
            Player p = e.getPlayer();
            p.sendMessage(Core.prefix + "You have been returned to the hub.");
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF("hub-1");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(Core.plugin, "BungeeCord", b.toByteArray());
            e.setCancelled(true);
        }else if(e.getItem().getType().equals(Material.SKULL_ITEM)) {
            e.setCancelled(true);
        }else if(e.getItem().getType().equals(Material.NETHER_STAR)) {
            e.setCancelled(true);
        }else{
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(GameRegisterPlayerEvent e) {
        Player p = e.getPlayer();
        e.getEngine().getGamePlayers().add(p.getUniqueId());
    }

    @EventHandler
    public void onPreRegisterPlayerEvent(GamePreRegisterPlayerEvent event) {

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

}
