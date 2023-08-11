package plugins.tobisch.com.network.manager;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.QuiverGUI;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class QuiverManager {
    private static final HashMap<UUID, QuiverGUI> quiver = new HashMap<>();

    public void savePlayerInventories() {
        File file = new File("quiver/playerInventories.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(UUID id : quiver.keySet()) {
            Inventory inventory = this.getInventory(id);
            YamlConfiguration configYAML = new YamlConfiguration();
            ConfigurationSection inventorySection = configYAML.createSection("inventory");

            ItemStack[] contents = inventory.getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack item = contents[i];
                if (item != null) {
                    inventorySection.set(String.valueOf(i), item.serialize());
                }
            }

            config.set(id.toString(), configYAML);
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlayerInventories() {
        File file = new File("quiver/playerInventories.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            QuiverGUI temp = new QuiverGUI(36, "Quiver");
            UUID playerId = UUID.fromString(key);

            ConfigurationSection inventorySection = Objects.requireNonNull(config.getConfigurationSection(key)).getConfigurationSection("inventory");
            if (inventorySection != null) {
                for (String keyInv : inventorySection.getKeys(false)) {
                    int index = Integer.parseInt(keyInv);
                    ItemStack item = ItemStack.deserialize(Objects.requireNonNull(inventorySection.getConfigurationSection(keyInv)).getValues(true));
                    temp.getInventory().setItem(index, item);
                }
            }
            quiver.put(playerId, temp);
        }
    }

    public Inventory getInventory(Player p){
        if(quiver.get(p.getUniqueId()) == null){
            quiver.put(p.getUniqueId(), new QuiverGUI(36, "Quiver"));
        }
        return quiver.get(p.getUniqueId()).getInventory();
    }

    public Inventory getInventory(UUID p){
        if(quiver.get(p) == null){
            quiver.put(p, new QuiverGUI(36, "Quiver"));
        }
        return quiver.get(p).getInventory();
    }
}
