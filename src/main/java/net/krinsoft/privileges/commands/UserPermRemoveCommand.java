package net.krinsoft.privileges.commands;

import java.util.ArrayList;
import java.util.List;
import net.krinsoft.privileges.Privileges;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

/**
 *
 * @author krinsdeath
 */
public class UserPermRemoveCommand extends UserPermCommand {

    public UserPermRemoveCommand(Privileges plugin) {
        super(plugin);
        this.setName("Privileges User Perm Remove");
        this.setCommandUsage("/privileges user perm remove [user] [world:]node");
        this.addCommandExample("/priv user perm remove Player example.node");
        this.addCommandExample("/pups Player world:example.node");
        this.setArgRange(2, 2);
        this.addKey("privileges user perm remove");
        this.addKey("priv user perm remove");
        this.addKey("pu perm remove");
        this.addKey("pup remove");
        this.addKey("pupr");
        this.setPermission("privileges.user.perm.remove", "Allows this user to remove permissions nodes.", PermissionDefault.OP);
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        String user = (plugin.getUsers().getNode("users." + args.get(0)) != null ? args.get(0) : null);
        if (user == null) {
            sender.sendMessage("I don't know about that user.");
            return;
        }
        String node = args.get(1);
        String world = null;
        if (node.contains(":")) {
            try {
                world = node.split(":")[0];
                node = node.split(":")[1];
                if (plugin.getServer().getWorld(world) == null) {
                    sender.sendMessage("Invalid world.");
                    return;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                sender.sendMessage("Invalid node string.");
                return;
            }
        }
        List<String> nodes = new ArrayList<String>();
        if (world == null) {
            nodes = plugin.getUserNode(user).getStringList("permissions", null);
            nodes.remove(node);
            nodes.remove("-" + node);
            plugin.getUserNode(user).setProperty("permissions", nodes);
        } else {
            nodes = plugin.getUserNode(user).getStringList("worlds." + world, null);
            nodes.remove(node);
            nodes.remove("-" + node);
            plugin.getUserNode(user).setProperty("worlds." + world, nodes);
        }
        plugin.getUsers().save();
        sender.sendMessage("Node '" + colorize(ChatColor.GREEN, node) + "' has been removed from " + user + (world == null ? "" : " on " + ChatColor.GREEN + world));
        permManager.reload();
    }

}