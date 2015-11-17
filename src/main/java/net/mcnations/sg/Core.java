package net.mcnations.sg;

import com.zaxxer.hikari.HikariDataSource;
import net.mcnations.engine.logger.GameLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Core extends JavaPlugin {

    private SGGame game;

    public static Core plugin;

    public static File spawnsYml = new File("plugins/MCNationsSG", "spawns.yml");
    public static FileConfiguration SpawnsConf = YamlConfiguration.loadConfiguration(spawnsYml);
    public static File worldsYml = new File("plugins/MCNationsSG", "worlds.yml");
    public static FileConfiguration WorldsConf = YamlConfiguration.loadConfiguration(worldsYml);
    public static File mysqlYml = new File("plugins/MCNationsSG", "mysql.yml");
    public static FileConfiguration MySQLConf = YamlConfiguration.loadConfiguration(mysqlYml);

    public static String prefix;
    public static String bossbar;

    public void onEnable() {
        if(WorldsConf.get("Map1") == null) {
            try {
                WorldsConf.set("Map1", "World1");
                WorldsConf.set("Map2", "World2");
                WorldsConf.set("Map3", "World3");
                WorldsConf.set("Map4", "World4");
                WorldsConf.set("Lobby", "Lobby");
                WorldsConf.set("DeathMatch", "DeathMatch");
                WorldsConf.save(worldsYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(MySQLConf.get("host") == null) {
            try {
                MySQLConf.set("host", "");
                MySQLConf.set("port", "");
                MySQLConf.set("username", "");
                MySQLConf.set("password", "");
                MySQLConf.set("database", "");
                MySQLConf.save(mysqlYml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            SpawnsConf.save(spawnsYml);
            SpawnsConf.load(spawnsYml);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        game = new SGGame(new GameLogger(), "Survival Games", this, new String[] {
                MySQLConf.getString("host"), MySQLConf.getInt("port") + "", MySQLConf.getString("database")
                , MySQLConf.getString("username"), MySQLConf.getString("password")
        });
        plugin = this;
        try{
            if(getConfig().getString("prefix") == null) {
                getConfig().set("prefix", "&aDefault Prefix > &c");
                saveConfig();
            }else{
                prefix = ChatColor.translateAlternateColorCodes('&' ,getConfig().getString("prefix"));
            }
            if(getConfig().getString("bossbar") == null) {
                getConfig().set("bossbar", "&aDefault BossBar Message.");
                saveConfig();
            }else{
                bossbar = ChatColor.translateAlternateColorCodes('&' ,getConfig().getString("bossbar"));
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        try{
            worldborder = new WorldBorder();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onDisable() {

    }

    public static WorldBorder worldborder;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(label.equalsIgnoreCase("setspawn")) {
            try {
                SpawnsConf.set(p.getWorld().getName() + ".spawn" + args[0] + ".x", Double.valueOf(p.getLocation().getX()));
                SpawnsConf.set(p.getWorld().getName() + ".spawn" + args[0] + ".y", Double.valueOf(p.getLocation().getY()));
                SpawnsConf.set(p.getWorld().getName() + ".spawn" + args[0] + ".z", Double.valueOf(p.getLocation().getZ()));
                SpawnsConf.set(p.getWorld().getName() + ".spawn" + args[0] + ".yaw", Float.valueOf(p.getLocation().getYaw()));
                SpawnsConf.set(p.getWorld().getName() + ".spawn" + args[0] + ".pitch", Float.valueOf(p.getLocation().getPitch()));
                SpawnsConf.save(spawnsYml);
                p.sendMessage(prefix + "Spawn saved: " + ChatColor.GREEN + args[0]);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                p.sendMessage(prefix + "Failed to save.");
            }
        }else if(label.equalsIgnoreCase("setlobby")) {
            getConfig().set("LobbyPoint.x", p.getLocation().getX());
            getConfig().set("LobbyPoint.y", p.getLocation().getY());
            getConfig().set("LobbyPoint.z", p.getLocation().getZ());
            getConfig().set("LobbyPoint.world", p.getLocation().getWorld().getName());
            getConfig().set("LobbyPoint.pitch", p.getLocation().getPitch());
            getConfig().set("LobbyPoint.yaw", p.getLocation().getYaw());
            p.sendMessage(prefix + "You have set Lobby Spawn!");
        }else if(label.equalsIgnoreCase("vote")) {
            if(!API.voted.contains(p)) {
                if(args[0].equalsIgnoreCase("1")) {
                    API.votes.put(API.Map1.getName(), API.votes.get(API.Map1.getName() + 1));
                    API.setCurrentMap(API.Map1);
                    p.sendMessage(prefix + "You have voted for " + API.Map1.getName());
                }else if(args[0].equalsIgnoreCase("2")) {
                    API.votes.put(API.Map2.getName(), API.votes.get(API.Map2.getName() + 1));
                    API.setCurrentMap(API.Map2);
                    p.sendMessage(prefix + "You have voted for " + API.Map2.getName());
                }else if(args[0].equalsIgnoreCase("3")) {
                    API.votes.put(API.Map3.getName(), API.votes.get(API.Map3.getName() + 1));
                    API.setCurrentMap(API.Map3);
                    p.sendMessage(prefix + "You have voted for " + API.Map3.getName());
                }else if(args[0].equalsIgnoreCase("4")) {
                    API.votes.put(API.Map4.getName(), API.votes.get(API.Map4.getName() + 1));
                    API.setCurrentMap(API.Map4);
                    p.sendMessage(prefix + "You have voted for " + API.Map4.getName());
                }
                API.voted.add(p);
            }
        }else if(label.equalsIgnoreCase("next")) {
            game.nextState();
            p.sendMessage(prefix + "You have forced the game start.");
        }
        return false;
    }
}
