package plugins.tobisch.com.network.talisman;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.guis.AccessoryBagGUI;

import java.util.*;

public class Utils {
    private static Talisman[] talismans= {
           new LifeSaver(), new ExtraHearts(), new FireResistance(), new Haste(), new JumpBoost(), new NightVision(), new NoFallDamage(), new Regeneration(), new Resistance(), new Saturation(), new Speed(), new Strength(), new WaterBreathing()
    };

    public static Talisman[] getTalismans(){
        return talismans;
    }

    public static boolean hasEveryRing(Inventory inv, AccessoryBagGUI bag, ItemStack currentlyUpgrading){
        ArrayList<Talisman> talismanLeft = new ArrayList<>();
        Collections.addAll(talismanLeft, talismans);
        Iterator<Talisman> i = talismanLeft.iterator();

        while (i.hasNext()){
            Talisman next = i.next();
            if(next.compare(currentlyUpgrading)>1){
                continue;
            }

            if(compareInventory(next, inv) > 1){
                i.remove();
                continue;
            }

            if(compareAccessoryBag(next, bag)>1){
                i.remove();
            }
        }

        Bukkit.getConsoleSender().sendMessage(talismanLeft.toString());

        return talismanLeft.size() == 0;
    }

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

    public static int getStage(ItemStack item){
        String name = item.getItemMeta().getDisplayName();

        return name.contains("Talisman") ? 1 : name.contains("Ring") ? 2: 3;
    }

    public static ItemStack upgrade(ItemStack item, int stage){
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> lore = (ArrayList<String>) meta.getLore();
        switch (stage){
            case 1: meta.setDisplayName(meta.getDisplayName().replace("Talisman", "Ring"));
                meta.setDisplayName(meta.getDisplayName().replace("§5", "§6"));
                lore.set(1, lore.get(1).replace("§5", "§6"));
                lore.set(1, lore.get(1).replace("EPIC", "LEGENDARY"));
                meta.setLore(lore);
                break;
            case 2: meta.setDisplayName(meta.getDisplayName().replace("Ring", "Artifact"));
                meta.setDisplayName(meta.getDisplayName().replace("§6", "§d"));
                lore.set(1, lore.get(1).replace("§6", "§d"));
                lore.set(1, lore.get(1).replace("LEGENDARY", "MYTHIC"));
                meta.setLore(lore); break;
        }
        item.setItemMeta(meta);

        return item;
    }
}
