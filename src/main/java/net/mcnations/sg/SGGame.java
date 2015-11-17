package net.mcnations.sg;

import net.mcnations.engine.Engine;
import net.mcnations.engine.GameState;
import net.mcnations.engine.logger.GameLogger;
import net.mcnations.sg.gamestates.*;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SGGame extends Engine {

    private List<GameState> gameStates = new ArrayList<GameState>();
    private List<UUID> gamePlayers = new ArrayList<UUID>();
    private List<UUID> gameSpectators = new ArrayList<UUID>();
    private List<UUID> gameJoinedSpectators = new ArrayList<UUID>();

    public SGGame(GameLogger logger, String displayName, Plugin plugin, String[] credentials) {
        super(logger, displayName, plugin, credentials);
    }

    @Override
    public List<GameState> getAllStates() {
        gameStates.add(new LobbyState(this, "IN_LOBBY", "Lobby"));
        gameStates.add(new PreGameState(this, "pre-game", "Pre-Game"));
        gameStates.add(new InGameState(this, "ingame", "Ingame"));
        gameStates.add(new DeathMatchState(this, "deathmatch", "Deathmatch"));
        gameStates.add(new RestartState(this, "restart", "Restart"));
        return gameStates;
    }

    @Override
    public List<UUID> getGamePlayers() {
        return gamePlayers;
    }

    @Override
    public List<UUID> getGameSpectators() {
        return gameSpectators;
    }

    @Override
    public List<UUID> getJoinedSpectators() {
        return gameJoinedSpectators;
    }

    @Override
    public int getGameMaxPlayers() {
        return 24;
    }

    @Override
    public int getGameMinPlayers() {
        return 2;
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
