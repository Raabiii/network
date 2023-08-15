package plugins.tobisch.com.network.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.AccessoryBagGUI;
import plugins.tobisch.com.network.guis.QuiverGUI;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AccessoryBagManager {
    private static final HashMap<UUID, AccessoryBagGUI> abag = new HashMap<>();

    public void saveAccessoryBag() {
        File file = new File("accessoryBag/access.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for(UUID id : abag.keySet()) {
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

    public void loadAccessoryBag() {
        File file = new File("accessoryBag/access.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            AccessoryBagGUI temp = new AccessoryBagGUI(36, "Accessory bag");
            UUID playerId = UUID.fromString(key);

            ConfigurationSection inventorySection = Objects.requireNonNull(config.getConfigurationSection(key)).getConfigurationSection("inventory");
            if (inventorySection != null) {
                for (String keyInv : inventorySection.getKeys(false)) {
                    int index = Integer.parseInt(keyInv);
                    ItemStack item = ItemStack.deserialize(Objects.requireNonNull(inventorySection.getConfigurationSection(keyInv)).getValues(true));
                    temp.getInventory().setItem(index, item);
                }
            }
            abag.put(playerId, temp);
        }
    }

    public Inventory getInventory(Player p){
        if(abag.get(p.getUniqueId()) == null){
            abag.put(p.getUniqueId(), new AccessoryBagGUI(36, "Accessory bag"));
        }
        return abag.get(p.getUniqueId()).getInventory();
    }

    public Inventory getInventory(UUID p){
        if(abag.get(p) == null){
            abag.put(p, new AccessoryBagGUI(36, "Accessory bag"));
        }
        return abag.get(p).getInventory();
    }

    public AccessoryBagGUI getAccessoryGUI(Player p){
        if(abag.get(p.getUniqueId()) == null){
            abag.put(p.getUniqueId(), new AccessoryBagGUI(36, "Accessory bag"));
        }
        return abag.get(p.getUniqueId());
    }

    public AccessoryBagGUI getAccessoryGUI(UUID p){
        if(abag.get(p) == null){
            abag.put(p, new AccessoryBagGUI(36, "Accessory bag"));
        }
        return abag.get(p);
    }
}
