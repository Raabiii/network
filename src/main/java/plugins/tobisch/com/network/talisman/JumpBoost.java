package plugins.tobisch.com.network.talisman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class JumpBoost implements Talisman{

    @Override
    public ItemStack createTalisman(){
        ItemStack lifeSaver = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = lifeSaver.getItemMeta();

        // Set the display name and lore

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("  §5§oEPIC§r§5");

        SkullMeta skullMeta = (SkullMeta) lifeSaver.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("triKUMA")); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
        skullMeta.setDisplayName("§5§kr§r§5 Jump Boost Talisman §kf§r");
        skullMeta.setLore(lore);
        lifeSaver.setItemMeta(skullMeta);

        return lifeSaver;
    }

    @Override
    public ItemStack createRing(){
        ItemStack lifeSaver = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = lifeSaver.getItemMeta();

        // Set the display name and lore

        ItemStack test = new ItemStack(Material.ACACIA_BOAT);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("  §6§oLEGENDARY§r§6");

        SkullMeta skullMeta = (SkullMeta) lifeSaver.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("triKUMA")); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
        skullMeta.setDisplayName("§6§kr§r§6 Jump Boost Ring §kf§r");
        skullMeta.setLore(lore);
        lifeSaver.setItemMeta(skullMeta);

        return lifeSaver;
    }

    @Override
    public ItemStack createArtifact(){
        ItemStack lifeSaver = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta meta = lifeSaver.getItemMeta();

        // Set the display name and lore

        ItemStack test = new ItemStack(Material.ACACIA_BOAT);
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("  §d§oMYTHIC§r§d");

        SkullMeta skullMeta = (SkullMeta) lifeSaver.getItemMeta(); // Get the created item's ItemMeta and cast it to SkullMeta so we can access the skull properties
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("triKUMA")); // Set the skull's owner so it will adapt the skin of the provided username (case sensitive).
        skullMeta.setDisplayName("§d§kr§r§d Jump Boost Artifact §kf§r");
        skullMeta.setLore(lore);
        lifeSaver.setItemMeta(skullMeta);

        return lifeSaver;
    }

    @Override
    public int compare(ItemStack item1){
        ItemStack item2 = this.createTalisman();

        if (item1.getType() != item2.getType()) return -1;

        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();

        // Compare display name (if set)
        assert meta1 != null;
        assert meta2 != null;
        if (meta1.hasDisplayName() != meta2.hasDisplayName()) return -1;
        if (meta1.hasDisplayName() && meta1.getDisplayName().contains("Jump Boost Talisman") ){
            return 1;
        } else if (meta1.hasDisplayName() && meta1.getDisplayName().contains("Jump Boost Ring") ) {
            return 2;
        } else if (meta1.hasDisplayName() && meta1.getDisplayName().contains("Jump Boost Artifact") )
            return 3;

        return -1;
    }
}
