package plugins.tobisch.com.network.talisman;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.AccessoryBagGUI;

import java.util.Arrays;

public class Utils {
    private static Talisman[] talismans= {
           new LifeSaver()
    };

    public static int compareInventory(Talisman talisman,Inventory  inv){
        for (ItemStack element: inv.getContents()) {
            if(element != null){
                if (talisman.compare(element) > 0)
                    return talisman.compare(element);
            }
        }

        return -1;
    }

    public static int compareAccessoryBag(Talisman talisman, AccessoryBagGUI bag){
        Inventory inv = bag.getInventory();
        return compareInventory(talisman, inv);
    }
}
