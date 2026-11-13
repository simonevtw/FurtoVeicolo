package me.simonevtw.furtoVeicolo.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import me.simonevtw.furtoVeicolo.utils.FurtoVeicoloManager;
import nl.sbdeveloper.vehiclesplus.api.VehiclesPlusAPI;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.StorageVehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.parts.impl.seat.Seat;
import org.bukkit.entity.Player;

public class FurtoVeicoloCommand {
    public static LiteralCommandNode<CommandSourceStack> create() {
        return Commands.literal("furtoveicolo")
                .then(Commands.argument("target", ArgumentTypes.player())
                        .executes(context -> {
                            var source = context.getSource();
                            Player player = (Player) source.getSender();

                            if (!(source.getSender() instanceof Player p)) {
                                source.getSender().sendMessage("Solo i giocatori possono usare questo comando!");
                                return 0;
                            }

                            PlayerSelectorArgumentResolver resolver = context.getArgument("target", PlayerSelectorArgumentResolver.class);
                            var resolvedPlayers = resolver.resolve(source);
                            Player target = resolvedPlayers.iterator().next();

                            StorageVehicle veicolo = VehiclesPlusAPI.createVehicle("fiat_grande_panda_hybrid_cinema");
                            if (veicolo == null) {
                                return 0;
                            }
                            veicolo.spawn(target, true);

                            Seat driverSeat = (veicolo.getPart(
                                    Seat.class
                            ));

                            assert driverSeat != null;
                            driverSeat.enter(player);

                            FurtoVeicoloManager.FURTOVEICOLO.put(player.getUniqueId(), veicolo.getUuid());

                            return 1;
                        }))
                .build();
    }
}
