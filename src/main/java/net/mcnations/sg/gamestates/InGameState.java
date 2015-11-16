package net.mcnations.sg.gamestates;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.events.GameRegisterPlayerEvent;
import net.mcnations.sg.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by PaulCodesUK on 13/11/2015.
 */
public class InGameState extends GameState {

    public InGameState(Engine engine, String rawName, String displayName) {
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

    int timer = 900;

    int spawnpoint = 4;

    @Override
    public void tick() {
        if(API.getCurrentMap().getTime() == 18000) {
            API.fillAllChests();
            Bukkit.broadcastMessage(ChatColor.RED + "SG > Chests now restocked!");
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            API.sendBossBar(p);
        }
        switch (timer) {
            case 900:
                API.getCurrentMap().setTime(6000);
                break;
            case 600:
                break;
            case 300:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 5 + ChatColor.RED + " minutes.");
                break;
            case 240:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 4 + ChatColor.RED + " minutes.");
                break;
            case 180:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 3 + ChatColor.RED + " minutes.");
                break;
            case 120:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 2 + ChatColor.RED + " minutes.");
                break;
            case 60:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 1 + ChatColor.RED + " minutes.");
                break;
            case 50:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 50 + ChatColor.RED + " seconds.");
                break;
            case 40:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 40 + ChatColor.RED + " seconds.");
                break;
            case 30:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 30 + ChatColor.RED + " seconds.");
                break;
            case 20:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 20 + ChatColor.RED + " seconds.");
                break;
            case 10:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 10 + ChatColor.RED + " seconds.");
                break;
            case 5:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 5 + ChatColor.RED + " seconds.");
                break;
            case 4:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 4 + ChatColor.RED + " seconds.");
                break;
            case 3:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 3 + ChatColor.RED + " seconds.");
                break;
            case 2:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 2 + ChatColor.RED + " seconds.");
                break;
            case 1:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Death Match starts in " + ChatColor.GREEN + 1 + ChatColor.RED + " seconds.");
                break;
            case 0:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Deathmatch is now starting");
                try {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        API.callDeathMatch(p, spawnpoint);
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
                engine.nextState();
                break;
        }
        timer--;
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
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getBlock().getType().equals(Material.LEAVES)) {
            e.setCancelled(false);
        }else if(e.getBlock().getType().equals(Material.LEAVES_2)) {
            e.setCancelled(false);
        }else{
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
