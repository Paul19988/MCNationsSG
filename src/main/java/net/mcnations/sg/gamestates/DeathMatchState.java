package net.mcnations.sg.gamestates;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.events.GameRegisterPlayerEvent;
import net.mcnations.sg.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by PaulCodesUK on 13/11/2015.
 */
public class DeathMatchState extends GameState {

    public DeathMatchState(Engine engine, String rawName, String displayName) {
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

    int timer = 300;

    @Override
    public void tick() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            API.sendBossBar(p);
        }

        switch(timer) {
            case 300:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 5 Minutes till end of DeathMatch");
                break;
            case 240:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 4 Minutes till end of DeathMatch");
                break;
            case 180:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 3 Minutes till end of DeathMatch");
                break;
            case 120:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 2 Minutes till end of DeathMatch");
                break;
            case 60:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 1 Minute till end of DeathMatch");
                break;
            case 50:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 50 Seconds till end of DeathMatch");
                break;
            case 40:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 40 Seconds till end of DeathMatch");
                break;
            case 30:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 30 Seconds till end of DeathMatch");
                break;
            case 20:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 20 Seconds till end of DeathMatch");
                break;
            case 10:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 10 Seconds till end of DeathMatch");
                break;
            case 5:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 5 Seconds till end of DeathMatch");
                break;
            case 4:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 4 Seconds till end of DeathMatch");
                break;
            case 3:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 3 Seconds till end of DeathMatch");
                break;
            case 2:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 2 Seconds till end of DeathMatch");
                break;
            case 1:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > 1 Second till end of DeathMatch");
                break;
            case 0:
                engine.nextState();
                break;
        }
        timer--;
    }

    @EventHandler
    public void onJoin(GameRegisterPlayerEvent e) {
        engine.getGameSpectators().add(e.getPlayer().getUniqueId());
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
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
}
