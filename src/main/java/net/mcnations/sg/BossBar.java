package net.mcnations.sg;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by PaulCodesUK on 15/11/2015.
 */
public class BossBar {
    private static int enderdragonId;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
    private static HashMap<String, BukkitRunnable> toHide = new HashMap<String, BukkitRunnable>();

    static {
        try {
            Field field = Class.forName(
                    "net.minecraft.server." + Bukkit.getServer().getClass().getName().split("\\.")[3] + ".Entity")
                    .getDeclaredField("entityCount");
            field.setAccessible(true);
            enderdragonId = field.getInt(null);
            field.set(null, enderdragonId + 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeBar(Player player) {
        removeBar(player, 2);
    }

    public static void removeBar(final Player player, int afterTicks) {
        if (player.hasMetadata("SeesWither") && !toHide.containsKey(player.getName())) {
            BukkitRunnable runnable = new BukkitRunnable() {
                public void run() {
                    player.removeMetadata("SeesWither", plugin);
                    sendRemovePacket(player);
                    toHide.remove(player.getName());
                }
            };
            runnable.runTaskLater(plugin, afterTicks);
            toHide.put(player.getName(), runnable);
        }
    }

    private static void sendRemovePacket(Player player) {
        try {
            PacketContainer spawnPacket = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            spawnPacket.getIntegerArrays().write(0, new int[]{enderdragonId});
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, spawnPacket, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendSpawnPacket(Player player, String message, float health) throws Exception {
        PacketContainer spawnPacket = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
        StructureModifier<Object> spawnPacketModifier = spawnPacket.getModifier();
        Location toSpawn = player.getEyeLocation().add(player.getEyeLocation().getDirection().normalize().multiply(23));
        spawnPacketModifier.write(0, enderdragonId);
        spawnPacketModifier.write(1, (byte) 64); // EntityID of wither
        spawnPacketModifier.write(2, toSpawn.getBlockX() * 32);
        spawnPacketModifier.write(3, toSpawn.getBlockY() * 32);
        spawnPacketModifier.write(4, toSpawn.getBlockZ() * 32);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        watcher.setObject(0, (byte) 32);
        watcher.setObject(2, message);
        watcher.setObject(6, health, true); // Set health
        watcher.setObject(10, message);
        watcher.setObject(20, 881);
        spawnPacket.getDataWatcherModifier().write(0, watcher);
        ProtocolLibrary.getProtocolManager().sendServerPacket(player, spawnPacket, false);
    }

    public static void setName(Player player, String message, float health) {
        try {
            if (!player.hasMetadata("SeesWither")) {
                player.setMetadata("SeesWither", new FixedMetadataValue(plugin, true));
            }
            if (toHide.containsKey(player.getName())) {
                toHide.remove(player.getName()).cancel();
            }
            sendSpawnPacket(player, message, health);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}