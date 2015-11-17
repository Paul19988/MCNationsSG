package net.mcnations.sg.gamestates;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.events.GameRegisterPlayerEvent;
import net.mcnations.engine.utils.xp.manager.XPManager;
import net.mcnations.sg.API;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by PaulCodesUK on 13/11/2015.
 */
public class RestartState extends GameState {

    public RestartState(Engine engine, String rawName, String displayName) {
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

    int timer = 10;

    @Override
    public void tick() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            API.sendBossBar(p);
            API.updateScoreboard(p, new String[]{
                    null,
                    "§6§lCurrent Map:",
                    "§oNone",
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
        switch(timer) {
            case 10:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Game resetting in 10 seconds");
                break;
            case 5:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Game resetting in 5 seconds");
                break;
            case 4:
                break;
            case 3:
                break;
            case 2:
                break;
            case 1:
                break;
            case 0:
                Bukkit.broadcastMessage(ChatColor.RED + "SG > Resetting Game.");
                API.resetServer();
                break;
        }
        timer--;
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
        e.setCancelled(true);
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
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }
}