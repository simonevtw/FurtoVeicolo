package me.simonevtw.furtoVeicolo.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.simonevtw.furtoVeicolo.utils.FurtoVeicoloManager;
import nl.sbdeveloper.vehiclesplus.api.VehiclesPlusAPI;
import nl.sbdeveloper.vehiclesplus.api.events.impl.KeyPressEvent;
import nl.sbdeveloper.vehiclesplus.api.events.impl.VehicleDespawnEvent;
import nl.sbdeveloper.vehiclesplus.api.vehicles.Vehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.SpawnedVehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.StorageVehicle;
import nl.sbdeveloper.vehiclesplus.storage.db.exceptions.DataStorageException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnKeyPress implements Listener {
    @EventHandler
    public void onKeyPress(KeyPressEvent event) throws DataStorageException {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Vehicle veicolo = VehiclesPlusAPI.getVehicle(FurtoVeicoloManager.FURTOVEICOLO.get(player.getUniqueId()));

        if (veicolo == null){
            return;
        }

        SpawnedVehicle spawnedVehicle = veicolo.getSpawnedVehicle();

        if (spawnedVehicle == null){
            return;
        }

        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(player.getWorld()));
        if (regions == null) return;

        ProtectedRegion region = regions.getRegion("furtoveicolo");
        if (region != null && region.contains(BukkitAdapter.asBlockVector(location)) && FurtoVeicoloManager.FURTOVEICOLO.containsKey(player.getUniqueId()) && player.getVehicle() == spawnedVehicle) {
            spawnedVehicle.despawn(VehicleDespawnEvent.DespawnReason.API);
            StorageVehicle storageVehicle = spawnedVehicle.getStorageVehicle();
            storageVehicle.remove();

            player.sendRichMessage("<red>Hai venduto il veicolo rubato");

            FurtoVeicoloManager.FURTOVEICOLO.remove(player.getUniqueId());
        }
    }
}
