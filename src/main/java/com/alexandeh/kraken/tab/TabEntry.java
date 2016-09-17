package com.alexandeh.kraken.tab;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerInteractManager;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class TabEntry {

    private PlayerTab playerTab;
    private int x, y;
    private String text;
    private EntityPlayer nms;
    private Team team;
    private boolean setup;

    public TabEntry(PlayerTab playerTab, String text, int x, int y) {
        this.playerTab = playerTab;
        this.text = text;
        this.x = x;
        this.y = y;

        playerTab.getEntries().add(this);
    }

    private TabEntry setup() {
        setup = true;

        Player player = playerTab.getPlayer();
        CraftPlayer craftplayer = (CraftPlayer) player;

        nms = new EntityPlayer(MinecraftServer.getServer(), ((CraftWorld) player.getWorld()).getHandle(), new GameProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', text)), new PlayerInteractManager(((CraftWorld) player.getWorld()).getHandle()));

        PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.updateDisplayName(nms);
        craftplayer.getHandle().playerConnection.sendPacket(packet);

        team = playerTab.getScoreboard().registerNewTeam(UUID.randomUUID().toString().substring(0, 16));
        team.addEntry(nms.getName());

        return this;
    }

    public TabEntry send() {
        if (!(setup)) {
            return setup();
        }

        text = ChatColor.translateAlternateColorCodes('&', text);

        if (text.length() > 16) {
            team.setPrefix(text.substring(0, 16));
            String suffix = ChatColor.getLastColors(team.getPrefix()) + text.substring(16, text.length());
            if (suffix.length() > 16) {
                if (suffix.length() <= 16) {
                    suffix = text.substring(16, text.length());
                    team.setSuffix(suffix.substring(0, suffix.length()));
                } else {
                    team.setSuffix(suffix.substring(0, 16));
                }
            } else {
                team.setSuffix(suffix);
            }
        } else {
            team.setPrefix(text);
            team.setSuffix("");
        }

        return this;
    }

}
