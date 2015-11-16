package net.mcnations.sg;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.logger.GameLogger;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

public class SGGame extends Engine {

    public SGGame(GameLogger logger, String displayName, Plugin plugin, String[] credentials) {
        super(logger, displayName, plugin, credentials);
    }

    @Override
    public List<GameState> getAllStates() {
        return null;
    }

    @Override
    public List<UUID> getGamePlayers() {
        return null;
    }

    @Override
    public List<UUID> getGameSpectators() {
        return null;
    }

    @Override
    public List<UUID> getJoinedSpectators() {
        return null;
    }

    @Override
    public int getGameMaxPlayers() {
        return 0;
    }

    @Override
    public int getGameMinPlayers() {
        return 0;
    }

    @Override
    public int getMaxJoinedSpectators() {
        return 0;
    }

    @Override
    public Location getLobbyLocation() {
        return null;
    }
}
