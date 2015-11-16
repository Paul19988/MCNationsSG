package net.mcnations.sg;

import net.mcnations.engine.utils.itembuilder.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.*;

/**
 * Created by PaulCodesUK on 14/11/2015.
 */
public class API {

    public static World currentMap = null;

    public static void setCurrentMap(World world) {
        currentMap = world;
    }

    public static World getCurrentMap() {
        World world = currentMap;
        return world;
    }

    static List<ItemStack> mats = Arrays.asList(
            new ItemBuilder(Material.BOW).build(), new ItemBuilder(Material.DIAMOND_CHESTPLATE).build(), new ItemBuilder(Material.COOKED_BEEF).setAmount(16).build(), new ItemBuilder(Material.BREAD).setAmount(5).build(),
            new ItemBuilder(Material.ARROW).setAmount(16).build(), new ItemBuilder(Material.CHAINMAIL_BOOTS).build(), new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).build(), new ItemBuilder(Material.CHAINMAIL_LEGGINGS).build(),
            new ItemBuilder(Material.CHAINMAIL_HELMET).build(), new ItemBuilder(Material.TNT).build(), new ItemBuilder(Material.TNT).build(), new ItemBuilder(Material.TNT).build(), new ItemBuilder(Material.LEATHER_CHESTPLATE).build(), new ItemBuilder(Material.IRON_CHESTPLATE).build(),
            new ItemBuilder(Material.LEATHER_LEGGINGS).build(), new ItemBuilder(Material.IRON_BOOTS).build(), new ItemBuilder(Material.LEATHER_BOOTS).build(), new ItemBuilder(Material.TNT).build(),
            new ItemBuilder(Material.APPLE).build(), new ItemBuilder(Material.ARROW).setAmount(6).build(), new ItemBuilder(Material.DIAMOND_PICKAXE).build(), new ItemBuilder(Material.DIAMOND_PICKAXE).build(), new ItemBuilder(Material.DIAMOND_PICKAXE).build(), new ItemBuilder(Material.DIAMOND_LEGGINGS).build(), new ItemBuilder(Material.IRON_LEGGINGS).build(),
            new ItemBuilder(Material.COOKED_CHICKEN).setAmount(5).build(), new ItemBuilder(Material.RABBIT_STEW).setAmount(2).build(), new ItemBuilder(Material.STONE_SWORD).build(), new ItemBuilder(Material.IRON_SWORD).build(),
            new ItemBuilder(Material.COOKED_FISH).setAmount(8).build(), new ItemBuilder(Material.MELON).setAmount(16).build(), new ItemBuilder(Material.GOLDEN_APPLE).build(), new ItemBuilder(Material.GOLDEN_CARROT).build(),
            new ItemBuilder(Material.CHAINMAIL_LEGGINGS).build(), new ItemBuilder(Material.PUMPKIN_PIE).setAmount(5).build()
    );

    public static void fillAllChests()
    {
        for (Chunk c : getCurrentMap().getLoadedChunks())
        {
            for (BlockState b : c.getTileEntities())
            {
                if (b instanceof Chest)
                {
                    Chest chest = (Chest) b;
                    Inventory inventory = Bukkit.getServer().createInventory(null, InventoryType.CHEST);
                    chest.getBlockInventory().clear();
                    chest.update(true, true);
                    Random randomvariable = new Random();

                    for (int cs = 0; cs < 4; cs++)
                    {
                        Collections.shuffle(mats);
                        int x = randomvariable.nextInt(24);
                        int y = randomvariable.nextInt(mats.size());

                        inventory.setItem(x, mats.get(y));
                    }

                    chest.getBlockInventory().setContents(inventory.getContents());
                    chest.update(true, true);
                }
            }
        }
    }

    public static void sendBossBar(Player p) {
        BossBar.setName(p, Core.bossbar, 100);
    }

    public static void updateScoreboard(Player p, String[] elements)
    {
        if(elements[0] == null)
            elements[0] = "§e§lSurvival Games";

        if(elements[0].length() > 32)
            elements[0] = elements[0].substring(0, 32);

        for(int i = 1; i < elements.length; i++) {
            if (elements[i] != null) {
                if (elements[i].length() > 40)
                    elements[i] = elements[i].substring(0, 40);
            }
        }

        try
        {
            if (p.getScoreboard() == null || p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)) == null)
            {
                p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                p.getScoreboard().registerNewObjective(p.getUniqueId().toString().substring(0, 16), "dummy");
                p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
            }

            p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements[0]);

            for (int i = 1; i < elements.length; i++)
            {
                if (elements[i] != null)
                {
                    if (p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).getScore() != 16 - i)
                    {
                        p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).setScore(16 - i);
                        for (String string : p.getScoreboard().getEntries())
                        {
                            if (p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(string).getScore() == 16 - i)
                            {
                                if (string != elements[i])
                                    p.getScoreboard().resetScores(string);
                            }
                        }
                    }
                }
            }


            for (String entry : p.getScoreboard().getEntries())
            {
                boolean toErase = true;
                for (String element : elements)
                {
                    if (element != null && element.equals(entry) && p.getScoreboard().getObjective(p.getUniqueId().toString().substring(0, 16)).getScore(entry).getScore() == 16 - Arrays.asList(elements).indexOf(element)) {
                        toErase = false;
                        break;
                    }
                }

                if (toErase)
                    p.getScoreboard().resetScores(entry);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void spawnPlayers(Player p, int i) {
        double spawnx = Core.SpawnsConf.getDouble(getCurrentMap().getName() + ".spawn" + i + ".x");
        double spawny = Core.SpawnsConf.getDouble(getCurrentMap().getName() + ".spawn" + i + ".y");
        double spawnz = Core.SpawnsConf.getDouble(getCurrentMap().getName() + ".spawn" + i + ".z");
        float spawnyaw = Core.SpawnsConf.getInt(getCurrentMap().getName() + ".spawn" + i + ".yaw");
        float spawnpitch = Core.SpawnsConf.getInt(getCurrentMap().getName() + ".spawn" + i + ".pitch");
        Location spawnLocation = new Location(getCurrentMap(), spawnx, spawny, spawnz, spawnyaw, spawnpitch);
        p.teleport(spawnLocation);
        p.getInventory().clear();
        p.setHealth(p.getMaxHealth());
        p.setHealthScale(20);
        p.updateInventory();
    }

    public static void resetServer() {
        try {
            Bukkit.unloadWorld(Core.WorldsConf.getString("Map1"), false);
            Bukkit.unloadWorld(Core.WorldsConf.getString("Map2"), false);
            Bukkit.unloadWorld(Core.WorldsConf.getString("Map3"), false);
            Bukkit.unloadWorld(Core.WorldsConf.getString("Map4"), false);
            Bukkit.unloadWorld(Core.WorldsConf.getString("Lobby"), false);
            Bukkit.unloadWorld(Core.WorldsConf.getString("DeathMatch"), false);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setFoodLevel(20);
            p.setSaturation(20);
            p.setHealth(p.getMaxHealth());
        }

        try{
            prepareLobby();
            prepareWorlds();
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            Location l = new Location(Bukkit.getWorld(Core.plugin.getConfig().getString("LobbyPoint.world")), Integer.parseInt(Core.plugin.getConfig().getString("LobbyPoint.x")), Integer.parseInt(Core.plugin.getConfig().getString("LobbyPoint.y")), Integer.parseInt(Core.plugin.getConfig().getString("LobbyPoint.z")), Integer.parseInt(Core.plugin.getConfig().getString("LobbyPoint.yaw")), Integer.parseInt(Core.plugin.getConfig().getString("LobbyPoint.pitch")));
            p.teleport(l);
        }
    }

    public static World Map1;
    public static World Map2;
    public static World Map3;
    public static World Map4;
    public static World dmatch;
    public static World lobby;

    public static HashMap<String, Integer> votes = new HashMap<String, Integer>();
    public static Map<UUID, Integer> xp = new HashMap<UUID, Integer>();

    public static int MapInt1 = 0;
    public static int MapInt2 = 0;
    public static int MapInt3 = 0;
    public static int MapInt4 = 0;

    public static ArrayList<Player> voted = new ArrayList<Player>();

    public static Map.Entry<String,Integer> maxEntry = null;

    public static Map.Entry checkMapVotes() {
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
                return maxEntry;
            }
        }
        return null;
    }


    public static void prepareWorlds() {
        try{
            Map1 = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("Map1")));
            Map1.setAutoSave(false);
            Map2 = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("Map2")));
            Map2.setAutoSave(false);
            Map3 = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("Map3")));
            Map3.setAutoSave(false);
            Map4 = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("Map4")));
            Map4.setAutoSave(false);
            dmatch = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("DeathMatch")));
            dmatch.setAutoSave(false);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void callDeathMatch(Player p ,int i) {
        spawnPlayers(p, i);
        i--;
    }

    public static void prepareLobby() {
        try{
            lobby = Bukkit.getServer().createWorld(new WorldCreator(Core.WorldsConf.getString("Lobby")));
            lobby.setAutoSave(false);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
