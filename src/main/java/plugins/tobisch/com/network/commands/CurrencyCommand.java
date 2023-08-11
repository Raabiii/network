package plugins.tobisch.com.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.utils.Utils;

public class CurrencyCommand implements CommandExecutor {
    public Main plugin;

    public CurrencyCommand(Main plugin){
        this.plugin = plugin;

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
                sender.sendMessage(Utils.chat("&e/currency <add:remove:set> <player> <amount>"));
            }else if(args.length == 1){
                if(args[0].equalsIgnoreCase("add")){
                    sender.sendMessage(Utils.chat("&e/currency add <player> <amount>"));
                } else if (args[0].equalsIgnoreCase("remove")) {
                    sender.sendMessage(Utils.chat("&e/currency remove <player> <amount>"));
                } else if (args[0].equalsIgnoreCase("set")) {
                    sender.sendMessage(Utils.chat("&e/currency set <player> <amount>"));
                } else if (args[0].equalsIgnoreCase("get")) {
                    sender.sendMessage(Utils.chat("&e/currency get <player>"));
                }else {
                    sender.sendMessage(Utils.chat("&e/currency <add:remove:set> <player>"));
                }
            }
            else if(args.length == 2){
                CurrencyManager manager = new CurrencyManager();
                @SuppressWarnings("deprecation")
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);

                if (p != null){
                    if(args[0].equalsIgnoreCase("get")){
                        sender.sendMessage(Utils.chat("&3The player &6" + p.getName() + " &3currently has &e" + manager.getPlayerCurrency(p) + " &3coins"));
                        return true;
                    }else {
                        sender.sendMessage(Utils.chat("&e/currency add " + p.getName() + " <amount>"));
                    }
                }else{
                    sender.sendMessage(Utils.chat("&ePlayer &c" + args[1] + "&ecould not be found"));
                }
            }else if (args.length == 3){
                @SuppressWarnings("deprecation")
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
                int amount = Integer.parseInt(args[2]);
                CurrencyManager manager = new CurrencyManager();

                if(args[0].equalsIgnoreCase("add")){
                    if(p!=null){
                        manager.addCurrencyToPlayer(p, amount);
                        sender.sendMessage(Utils.chat("&aYou have successfully added &6€" + amount + " &ato the player " + p.getName()));
                    }else{
                        sender.sendMessage(Utils.chat("&ePlayer &c" + args[1] + "&ecould not be found"));
                    }
                } else if(args[0].equalsIgnoreCase("remove")){
                    if(p!=null){
                        manager.removeCurrencyFromPlayer(p, amount);
                        sender.sendMessage(Utils.chat("&aYou have successfully removed &6€" + amount + " &a from the player " + p.getName()));
                    }else{
                        sender.sendMessage(Utils.chat("&ePlayer &c" + args[1] + "&ecould not be found"));
                    }
                } else if(args[0].equalsIgnoreCase("set")){
                    if(p!=null){
                        manager.setPlayerCurrency(p, amount);
                        sender.sendMessage(Utils.chat("&aYou have successfully set the coins from " + p.getName() + " to &6€" + amount));
                    }else{
                        sender.sendMessage(Utils.chat("&ePlayer &c" + args[1] + "&ecould not be found"));
                    }
                }
        }else{
            sender.sendMessage(Utils.chat("&cYou do not have permission to execute this command"));
        }

        return false;
    }
}
