package me.simonevtw.furtoVeicolo.listeners;

import me.simonevtw.furtoVeicolo.utils.FurtoVeicoloManager;
import nl.sbdeveloper.vehiclesplus.api.VehiclesPlusAPI;
import nl.sbdeveloper.vehiclesplus.api.events.impl.VehicleDespawnEvent;
import nl.sbdeveloper.vehiclesplus.api.vehicles.Vehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.SpawnedVehicle;
import nl.sbdeveloper.vehiclesplus.api.vehicles.impl.StorageVehicle;
import nl.sbdeveloper.vehiclesplus.storage.db.exceptions.DataStorageException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class OnPlayerQuit implements Listener {
    @EventHandler
    public void OnHotbarSwap(PlayerQuitEvent event) throws DataStorageException {
        Player player = event.getPlayer();

        if(FurtoVeicoloManager.FURTOVEICOLO.containsKey(player.getUniqueId())) {

            UUID uuid = FurtoVeicoloManager.FURTOVEICOLO.get(player.getUniqueId());

            Vehicle vehicle = VehiclesPlusAPI.getVehicle(uuid);

            SpawnedVehicle spawnedVehicle = vehicle.getSpawnedVehicle();

            if (spawnedVehicle == null) {
                return;
            }

            spawnedVehicle.despawn(VehicleDespawnEvent.DespawnReason.API);
            StorageVehicle storageVehicle = spawnedVehicle.getStorageVehicle();
            if (storageVehicle == null) {
                return;
            }
            storageVehicle.remove();

            FurtoVeicoloManager.FURTOVEICOLO.remove(player.getUniqueId());

        }

    }
}
