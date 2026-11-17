package me.simonevtw.furtoVeicolo;

import com.live.bemmamin.gps.api.GPSAPI;
import de.eisi05.npc.api.NpcApi;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.simonevtw.furtoVeicolo.commands.FurtiVeicoloCommand;
import me.simonevtw.furtoVeicolo.commands.NpcFurtoVeicoloCommand;
import me.simonevtw.furtoVeicolo.listeners.OnKeyPress;
import me.simonevtw.furtoVeicolo.listeners.OnNpcInteract;
import me.simonevtw.furtoVeicolo.listeners.OnPlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(NpcFurtoVeicoloCommand.create());
            event.registrar().register(FurtiVeicoloCommand.create());
        });
        this.getServer().getPluginManager().registerEvents(new OnPlayerQuit(), this);
        this.getServer().getPluginManager().registerEvents(new OnNpcInteract(this), this);
        this.getServer().getPluginManager().registerEvents(new OnKeyPress(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        NpcApi.disable();
    }
}
