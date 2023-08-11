package plugins.tobisch.com.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.utils.Utils;

public class PayCommand implements CommandExecutor {
    public Main plugin;

    public PayCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(Utils.chat("&e/pay <user> <amount>"));
        }else if(args.length == 1){
            sender.sendMessage(Utils.chat("&e/pay " + args[0] + " <amount>"));
        }
        else if(args.length == 2){
            CurrencyManager cm = new CurrencyManager();
            Player payer = Bukkit.getPlayer(sender.getName());
            Player receive = Bukkit.getPlayer(args[0]);
            double amount = Double.parseDouble(args[1]);

            assert payer != null;
            if(cm.getPlayerCurrency(payer) < amount){
                payer.sendMessage("§4You have too less money");
                return false;
            }

            cm.removeCurrencyFromPlayer(payer, amount);
            assert receive != null;
            cm.addCurrencyToPlayer(receive, amount);

            sender.sendMessage("§aYou paid " + receive.getName() + "§6 €" + amount);
        } else {
            sender.sendMessage(Utils.chat("&e/pay <user> <amount>"));
        }

        return false;
    }
}
