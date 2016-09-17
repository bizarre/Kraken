package com.alexandeh.kraken.tab;


import com.alexandeh.kraken.Kraken;
import com.alexandeh.kraken.tab.event.PlayerTabCreateEvent;
import lombok.Getter;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class PlayerTab {

    private static Set<PlayerTab> playerTabs = new HashSet<>();
    private Player player;
    private Scoreboard scoreboard;
    private List<TabEntry> entries;

    public PlayerTab(Player player) {
        this.player = player;

        entries = new ArrayList<>();

        clear();

        if (!player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            scoreboard = player.getScoreboard();
            assemble();
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
                    player.setScoreboard(scoreboard);
                    assemble();
                }
            }.runTask(Kraken.getInstance().getPlugin());
        }

        playerTabs.add(this);
    }

    public void clear() {
        for (TabEntry entry : entries) {
            if (entry.nms() != null) {
                PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.removePlayer(entry.nms());
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
        }

        for (Player online : Bukkit.getOnlinePlayers()) {
            PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.removePlayer(((CraftPlayer)online).getHandle());
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }

        entries.clear();
    }

    private void assemble() {

        for (int i = 0; i < 60; i++) {
            int x = i % 3;
            int y = i / 3;
            new TabEntry(this, getNextBlank(), x, y).send();
        }

        Bukkit.getPluginManager().callEvent(new PlayerTabCreateEvent(this));
    }

    public TabEntry getByPosition(int x, int y) {
        for (TabEntry tabEntry : entries) {
            if (tabEntry.x() == x && tabEntry.y() == y) {
                return tabEntry;
            }
        }
        return null;
    }

    public String getNextBlank() {
        outer: for (String string : getAllBlanks()) {
            for (TabEntry tabEntry : entries) {
                if (tabEntry.text() != null && tabEntry.text().startsWith(string)) {
                    continue outer;
                }
            }
            return string;
        }
        return null;
    }

    private static List<String> getAllBlanks() {
        List<String> toReturn = new ArrayList<>();
        for (ChatColor chatColor : ChatColor.values()) {
            toReturn.add(chatColor + "" + ChatColor.RESET);
            for (ChatColor chatColor1 : ChatColor.values()) {

                if (toReturn.size() >= 60) {
                    return toReturn;
                }

                toReturn.add(chatColor + "" + chatColor1 + ChatColor.RESET);
            }
        }

        return toReturn;
    }

    public static PlayerTab getByPlayer(Player player) {
        for (PlayerTab playerTab : playerTabs) {
            if (playerTab.getPlayer().getName().equals(player.getName())) {
                return playerTab;
            }
        }
        return null;
    }

    public static Set<PlayerTab> getPlayerTabs() {
        return playerTabs;
    }
}
