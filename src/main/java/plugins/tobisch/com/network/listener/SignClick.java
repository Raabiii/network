package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.block.Sign;

public class SignClick implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null && clickedBlock.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign) clickedBlock.getState();

                String line1 = sign.getSide(Side.FRONT).getLine(0);

                if (line1.equalsIgnoreCase("skyblock")) {
                    teleportToSkyWorld(player);
                } else if (line1.equalsIgnoreCase("stoneblock")) {
                    teleportToStoneMap(player);
                } else if (line1.equalsIgnoreCase("netherblock")) {
                    teleportToNether(player);
                }
            }
        }
    }

    private void teleportToSkyWorld(Player player) {
        World skyWorld = Bukkit.getWorld("skyblock");
        if (skyWorld != null) {
            Location loc = skyWorld.getSpawnLocation();
            loc.add(0.5, 0, 0.5);
            player.teleport(loc);
        } else {
            player.sendMessage("The sky world is not available.");
        }
    }

    private void teleportToStoneMap(Player player) {
        World stoneMapWorld = Bukkit.getWorld("stoneblock");
        if (stoneMapWorld != null) {
            Location loc = stoneMapWorld.getSpawnLocation();
            loc.add(0.5, 0, 0.5);
            player.teleport(loc);
        } else {
            player.sendMessage("The stone map world is not available.");
        }
    }

    private void teleportToNether(Player player) {
        World netherWorld = Bukkit.getWorld("netherblock");
        if (netherWorld != null) {
            Location loc = netherWorld.getSpawnLocation();
            loc.add(0.5, 0, 0.5);
            player.teleport(loc);
        } else {
            player.sendMessage("The Nether world is not available.");
        }
    }

}
