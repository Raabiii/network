package plugins.tobisch.com.network.talisman;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public interface Talisman {
    ItemStack createTalisman();
    ItemStack createRing();
    ItemStack createArtifact();

    int compare(ItemStack item1);
}
