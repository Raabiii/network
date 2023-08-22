package plugins.tobisch.com.network.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.guis.villager.CompactorVillagerGUI;
import plugins.tobisch.com.network.guis.villager.SpawnEggVillagerGUI;
import plugins.tobisch.com.network.guis.villager.TalismanVillagerGUI;
import plugins.tobisch.com.network.skills.Skills;

public class SetVillager implements CommandExecutor {
    public Main plugin;

    public SetVillager(Main plugin){
        this.plugin = plugin;

    }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be executed by a player.");
                return true;
            }

            Player player = (Player) sender;

            String villager = args[0];
            switch (villager.toLowerCase()){
                case "talisman" : new TalismanVillagerGUI("Talisman").makeVillager(player); break;
                case "compactor":  new CompactorVillagerGUI("Compactor").makeVillager(player); break;
                case "spawn_egg": new SpawnEggVillagerGUI("Spawn Egg").makeVillager(player); break;
                case "slayer": break;
                default: player.sendMessage("§6 this is not a villager");
            }

            new Skills().setSkills(player, 10);

        return false;
    }
}
