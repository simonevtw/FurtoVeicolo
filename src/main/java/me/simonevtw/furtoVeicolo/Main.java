package me.simonevtw.furtoVeicolo;

import de.eisi05.npc.api.NpcApi;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.simonevtw.furtoVeicolo.commands.FurtoVeicoloCommand;
import me.simonevtw.furtoVeicolo.listeners.OnPlayerQuit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(FurtoVeicoloCommand.create());
        });
        this.getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        NpcApi.disable();
    }
}
