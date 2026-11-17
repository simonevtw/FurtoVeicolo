package me.simonevtw.furtoVeicolo.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import de.eisi05.npc.api.objects.NPC;
import de.eisi05.npc.api.objects.NpcName;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NpcFurtoVeicoloCommand {
    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("npcfurtoveicolo")
                .executes(context -> {
                    var source = context.getSource();
                    Player player = (Player) source.getSender();
                    Location location = player.getLocation();

                    if (!(source.getSender() instanceof Player p)) {
                        source.getSender().sendMessage("Solo i giocatori possono usare questo comando!");
                        return 0;
                    }

                    NPC npc = new NPC(player.getLocation(), NpcName.empty());
                    npc.setEnabled(true);
                    npc.showNpcToAllPlayers();
                    npc.addCustomData("furtoveicolo:npc", "furtoveicolo");


                    return 1;
                })
                .build();
    }
}
