package dev.aarow.hardcore.commands;

import dev.aarow.hardcore.HardcorePlugin;
import dev.aarow.hardcore.utility.chat.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class BaseCommand implements CommandExecutor {

    public HardcorePlugin plugin = HardcorePlugin.INSTANCE;
    private final CommandInfo commandInfo;

    public BaseCommand(){
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);

        plugin.getCommand(commandInfo.name()).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!commandInfo.permission().isEmpty() && !commandSender.hasPermission(commandInfo.permission())){
            commandSender.sendMessage(CC.translate("&cNo permission."));
            return true;
        }
        if(commandInfo.playerOnly()){
            if(commandSender instanceof ConsoleCommandSender){
                commandSender.sendMessage(CC.translate("&cOnly players can execute this command!"));
                return true;
            }
            execute((Player) commandSender, strings);
            return true;
        }
        execute(commandSender, strings);
        return true;
    }

    public void execute(CommandSender sender, String args[]){}
    public void execute(Player player, String args[]){}
}