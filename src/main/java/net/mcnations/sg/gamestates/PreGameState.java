package net.mcnations.sg.gamestates;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.events.GameRegisterPlayerEvent;
import net.mcnations.sg.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Weather;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by PaulCodesUK on 13/11/2015.
 */
public class PreGameState extends GameState {

    public PreGameState(Engine engine, String rawName, String displayName) {
        super(engine, rawName, displayName);
    }

    @Override
    public boolean onStateBegin() {
        regListener(this);
        return true;
    }

    @Override
    public boolean onStateEnd() {
        unregListener(this);
        return true;
    }

    int timer = 15;

    @Override
    public void tick() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            API.sendBossBar(p);
        }
        if(--timer == 0) {
            // Stop the state
        } else {
            if(timer == 15) {
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " seconds.");
                API.fillAllChests();
            }else if(timer == 5){
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " seconds.");
            }else if(timer == 4) {
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " seconds.");
            }else if(timer == 3) {
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " seconds.");
            }else if(timer == 2) {
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " seconds.");
            }else if(timer == 1) {
                Bukkit.broadcastMessage(ChatColor.RED + "SG > You can move in " + ChatColor.GREEN + timer + ChatColor.RED + " second.");
            }else if(timer == 0) {
                engine.nextState();
            }
        }
        timer--;
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        if(e.getTo().getX() - e.getFrom().getX() != 0 || e.getTo().getZ() - e.getFrom().getZ() != 0){
            p.teleport(e.getFrom());
        }
    }

    @EventHandler
    public void onJoin(GameRegisterPlayerEvent e) {
        Player p = e.getPlayer();
        e.getEngine().getGameSpectators().add(p.getUniqueId());
    }

    @EventHandler
    public void onPJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
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

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
