package net.krinsoft.privileges.commands;

import net.krinsoft.privileges.Privileges;
import net.krinsoft.privileges.groups.Group;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;

/**
 * @author krinsdeath
 */
public class DemoteCommand extends GroupCommand {

    public DemoteCommand(Privileges plugin) {
        super(plugin);
        setName("Privileges: Demote");
        setCommandUsage("/demote [user]");
        setArgRange(1, 1);
        addKey("privileges demote");
        addKey("priv demote");
        addKey("demote");
        addKey("dem");
        setPermission("privileges.demote", "Allows the sender to demote other users.", PermissionDefault.OP);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        Player target = plugin.getServer().getPlayer(args.get(0));
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "The target '" + ChatColor.DARK_RED + args.get(0) + ChatColor.RED + "' doesn't exist.");
            return;
        }
        Group group = groupManager.demote(sender, target);
        if (group != null) {
            sender.sendMessage(ChatColor.AQUA + target.getName() + ChatColor.GREEN + " has been demoted to '" + ChatColor.AQUA + group.getName() + ChatColor.GREEN + "'");
            plugin.log(">> " + sender.getName() + ": " + target.getName() + " has been demoted to '" + group.getName() + "'");
        }
    }
}
