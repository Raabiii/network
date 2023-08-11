package plugins.tobisch.com.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.talisman.LifeSaver;
import plugins.tobisch.com.network.talisman.Strength;

public class GetLifeSaver implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        @SuppressWarnings("deprecation")
        OfflinePlayer p = Bukkit.getOfflinePlayer(sender.getName());

        LifeSaver lifeSaver = new LifeSaver();
        Strength strength = new Strength();
        ((Player)p).getInventory().addItem(lifeSaver.createTalisman());
        ((Player)p).getInventory().addItem(lifeSaver.createRing());
        ((Player)p).getInventory().addItem(lifeSaver.createArtifact());

        ((Player)p).getInventory().addItem(strength.createTalisman());

        return false;
    }
}
