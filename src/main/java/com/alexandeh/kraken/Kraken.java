package com.alexandeh.kraken;

import com.alexandeh.kraken.tab.PlayerTab;
import com.alexandeh.kraken.tab.event.PlayerTabRemoveEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.HashSet;

@Getter
public class Kraken implements Listener {

    private static Kraken instance;
    private JavaPlugin plugin;
    private KrakenOptions options;

    public Kraken(JavaPlugin plugin) {
        this(plugin, KrakenOptions.getDefaultOptions());
    }

    public Kraken(JavaPlugin plugin, KrakenOptions options) {

        if (Bukkit.getMaxPlayers() < 60) {
            throw new NumberFormatException("Player limit must be at least 60!");
        }

        instance = this;

        this.plugin = plugin;
        this.options = options;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    checkPlayer(player);
                }
            }
        }.runTaskLaterAsynchronously(plugin, 4L);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                checkPlayer(player);
            }
        }.runTaskLaterAsynchronously(plugin, 4L);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerTab playerTab = PlayerTab.getByPlayer(player);
        if (playerTab != null) {

            for (Team team : new HashSet<>(playerTab.getScoreboard().getTeams())) {
                team.unregister();
            }

            PlayerTab.getPlayerTabs().remove(playerTab);
            Bukkit.getPluginManager().callEvent(new PlayerTabRemoveEvent(playerTab));
        }
    }

    private void checkPlayer(Player player) {
        PlayerTab playerTab = PlayerTab.getByPlayer(player);
        if (playerTab == null) {
            long time = System.currentTimeMillis();
            new PlayerTab(player);
            if (options.sendCreationMessage()) {
                player.sendMessage(ChatColor.BLUE + "Tab created in " + (System.currentTimeMillis() - time) + " ms.");
            }
        } else {
            playerTab.clear();
        }
    }

    public static Kraken getInstance() {
        return instance;
    }
}
