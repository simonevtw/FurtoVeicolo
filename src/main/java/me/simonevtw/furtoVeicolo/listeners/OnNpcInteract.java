package me.simonevtw.furtoVeicolo.listeners;

import com.live.bemmamin.gps.api.GPSAPI;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import de.eisi05.npc.api.events.NpcInteractEvent;
import de.eisi05.npc.api.objects.NPC;
import me.simonevtw.furtoVeicolo.utils.FurtoVeicoloManager;
import nl.sbdeveloper.vehiclesplus.api.VehiclesPlusAPI;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.StorageVehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.parts.impl.seat.Seat;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class OnNpcInteract implements Listener {

    private static final String REGION_ID = "furtoveicolo";

    private final GPSAPI gps;

    public OnNpcInteract(JavaPlugin plugin) {
        this.gps = new GPSAPI(plugin);
    }

    @EventHandler
    public void onNpcInteract(NpcInteractEvent event) {
        NPC npc = event.getNpc();
        Player player = event.getPlayer();

        String tag = npc.getCustomData("furtoveicolo:npc");
        if (tag == null) return;

        if (FurtoVeicoloManager.FURTOVEICOLO.containsKey(player.getUniqueId())) return;

        StorageVehicle veicolo = VehiclesPlusAPI.createVehicle("fiat_grande_panda_hybrid_cinema");
        if (veicolo == null) return;

        veicolo.spawn(player, true);

        Seat driverSeat = veicolo.getPart(Seat.class);
        if (driverSeat != null) {
            driverSeat.enter(player);
        }

        player.sendRichMessage("<red><bold>FURTO VEICOLO</bold></red>\n\n"
                + "<gray>Hai avviato un furto veicolo.\nSegui il GPS e consegna il veicolo per ricevere il denaro.");

        Location consegna = getCentroRegion(player.getWorld());
        if (consegna != null) {
            gps.startCompass(player, consegna);
        }

        FurtoVeicoloManager.FURTOVEICOLO.put(player.getUniqueId(), veicolo.getUuid());
    }

    private Location getCentroRegion(World world) {
        RegionManager regions = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(BukkitAdapter.adapt(world));
        if (regions == null) return null;

        ProtectedRegion region = regions.getRegion(REGION_ID);
        if (region == null) return null;

        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();

        double x = (min.x() + max.x()) / 2.0;
        double z = (min.z() + max.z()) / 2.0;
        double y = world.getHighestBlockYAt((int) x, (int) z) + 1;

        return new Location(world, x, y, z);
    }
}