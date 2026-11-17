package me.simonevtw.furtoVeicolo.commands;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FurtiVeicoloCommand {
    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("furtiveicolo")
                .executes(context -> {
                    var source = context.getSource();

                    if (!(source.getSender() instanceof Player player)) {
                        source.getSender().sendMessage("Solo i giocatori possono usare questo comando!");
                        return 0;
                    }

                    ChestGui gui = new ChestGui(5, "Furti veicolo");
                    StaticPane pane = new StaticPane(1, 1);
                    pane.addItem(new GuiItem(new ItemStack(Material.STONE)), 0, 0);
                    gui.addPane(pane);

                    gui.show(player);

                    return 1;
                })
        .build();
    }
}