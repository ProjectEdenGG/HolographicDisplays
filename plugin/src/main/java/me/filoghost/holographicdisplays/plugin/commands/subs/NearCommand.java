/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.plugin.commands.subs;

import me.filoghost.fcommons.command.sub.SubCommandContext;
import me.filoghost.fcommons.command.validation.CommandException;
import me.filoghost.fcommons.command.validation.CommandValidate;
import me.filoghost.holographicdisplays.plugin.commands.HologramSubCommand;
import me.filoghost.holographicdisplays.plugin.commands.InternalHologramEditor;
import me.filoghost.holographicdisplays.plugin.format.ColorScheme;
import me.filoghost.holographicdisplays.plugin.format.DisplayFormat;
import me.filoghost.holographicdisplays.plugin.hologram.base.HologramLocation;
import me.filoghost.holographicdisplays.plugin.hologram.internal.InternalHologram;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NearCommand extends HologramSubCommand {

    private final InternalHologramEditor hologramEditor;

    public NearCommand(InternalHologramEditor hologramEditor) {
        super("near");
        setMinArgs(1);
        setUsageArgs("<radius>");
        setDescription("Get a list of near holograms.");

        this.hologramEditor = hologramEditor;
    }

    @Override
    public void execute(CommandSender sender, String[] args, SubCommandContext context) throws CommandException {
        Player player = CommandValidate.getPlayerSender(sender);
        int radius = CommandValidate.parseInteger(args[0]);
        CommandValidate.check(radius > 0, "Radius must be at least 1.");

        World world = player.getWorld();
        List<InternalHologram> nearHolograms = new ArrayList<>();

        for (InternalHologram hologram : hologramEditor.getHolograms()) {
            HologramLocation location = hologram.getHologramLocation();
            if (location.getWorld().equals(world) && location.distance(player.getLocation()) <= radius) {
                nearHolograms.add(hologram);
            }
        }

        CommandValidate.check(!nearHolograms.isEmpty(), "There are no holograms in the given radius.");

        DisplayFormat.sendTitle(player, "Near holograms");
        for (InternalHologram nearHologram : nearHolograms) {
            HologramLocation location = nearHologram.getHologramLocation();
            player.sendMessage(ColorScheme.SECONDARY_DARKER + "- "
                    + ColorScheme.SECONDARY_BOLD + nearHologram.getName() + " " + ColorScheme.SECONDARY_DARKER + "at"
                    + " x: " + location.getBlockX() + ", y: " + location.getBlockY() + ", z: " + location.getBlockZ()
                    + " (lines: " + nearHologram.getLineCount() + ")");
        }
    }

}
