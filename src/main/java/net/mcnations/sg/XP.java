package net.mcnations.sg;

import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * Created by PaulCodesUK on 16/11/2015.
 */
public class XP {

    private UUID uuid;
    private String playername;

    private int xp;
    private double multiplier;

    public XP(final UUID uuid)
    {
        this.uuid = uuid;
        Bukkit.getScheduler().runTaskLaterAsynchronously(Core.plugin, new Runnable() {
            public void run() {
                playername = Bukkit.getPlayer(uuid).getName();
            }
        }, 20L);
    }

    public UUID getUuid()
    {
        return uuid;
    }

    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getPlayername()
    {
        return playername;
    }

    public void setPlayername(String playername)
    {
        this.playername = playername;
    }

    public int getXp()
    {
        return xp;
    }

    public void setXp(int xp)
    {
        this.xp = xp;
    }

    public double getMultiplier()
    {
        return multiplier;
    }

    public void setMultiplier(double multiplier)
    {
        this.multiplier = multiplier;
    }

}
