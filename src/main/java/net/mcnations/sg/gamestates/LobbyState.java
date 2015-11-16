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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

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
        return true;
    }

    @Override
    public boolean onStateEnd() {
        unregListener(this);
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
        if(--timer == 0) {
            // Stop the state
        } else {
            if(timer % 10 == 0) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer <= 5) {
            }
            if(timer == 5){
                Bukkit.broadcastMessage(Core.prefix + "Map has now been chosen!");
                API.checkMapVotes();
                Bukkit.broadcastMessage(Core.prefix + "Chosen map is: " + API.getCurrentMap());
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 4) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 3) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 2) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " seconds.");
            }else if(timer == 1) {
                Bukkit.broadcastMessage(Core.prefix + "Game starts in " + timer + " second.");
            }else if(timer == 0) {
                if(Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers() / 2) {
                    engine.nextState();
                }else{
                    Bukkit.broadcastMessage(Core.prefix + "Not enough players, Restarting timer " + ChatColor.GREEN + "(" + Bukkit.getOnlinePlayers() + "/" + Bukkit.getMaxPlayers() + ")" );
                }
            }
        }
        timer--;
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(GameRegisterPlayerEvent e) {
        Player p = e.getPlayer();
        e.getEngine().getGamePlayers().add(p.getUniqueId());
        XP xp = new XP(e.getPlayer());
        XPManager.getXpProfiles().put(e.getPlayer().getUniqueId(), xp);
    }

    @EventHandler
    public void onPreRegisterPlayerEvent(GamePreRegisterPlayerEvent event) {

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

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
        try {
            e.setCancelled(true);
        }catch(Exception ex) {

        }
    }

}
