package br.com.stency.kits.command;

import br.com.stency.common.util.command.CommonCommand;
import br.com.stency.common.util.command.annotation.Aliases;
import br.com.stency.common.util.command.annotation.Command;
import br.com.stency.common.util.command.annotation.Permission;
import br.com.stency.common.util.string.Time;
import br.com.stency.kits.Kits;
import br.com.stency.kits.data.Kit;
import br.com.stency.kits.data.User;
import br.com.stency.kits.service.KitService;
import br.com.stency.kits.service.UserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitCommand extends CommonCommand {

    @Command("kit")
    @Permission("stency.commands.kit")
    @Override
    public void command(CommandSender commandSender, Player player, String[] arguments) {
        if (arguments.length == 0){
            player.sendMessage("§cUse /kit (kit).");
        }else {
            Kit kit = Kits.getInstance().getService(KitService.class).get(arguments[0]);
            if (kit != null) {
                if (player.hasPermission("stency.kits." + kit.getName().toLowerCase())) {
                    User user = Kits.getInstance().getService(UserService.class).create(player.getUniqueId());
                    if (!player.hasPermission("stency.bypass.kits")) {
                        if (user.hasDelay(kit.getName())) {
                            if (System.currentTimeMillis() < user.getKits().get(kit.getName())) {
                                player.sendMessage("§cVocê precisa aguardar §f" + Time.getTime(user.getKits().get(kit.getName())) + "§c para pegar este kit novamente.");
                                return;
                            } else {
                                user.getKits().remove(kit.getName());
                            }
                        }
                    }
                    if (player.getInventory().firstEmpty() == -1 || avaliableSlots(player) < kit.getContents().size()){
                        player.sendMessage("§cVocê não tem espaço suficiente para pegar o kit.");
                    }else {
                        kit.give(user,player);
                        player.sendMessage("§aVocê pegou o kit §f'" + kit.getName() + "' §acom sucesso.");
                    }
                }else{
                    player.sendMessage("§cVocê não tem permissão para pegar este kit.");
                }
            } else {
                player.sendMessage("§cO kit §f'" + arguments[0] + "'§c não existe.");
            }
        }
    }

    private int avaliableSlots(Player player){
        int sum = 0;
        for (ItemStack item : player.getInventory().getContents()){
            if (item == null){
                sum++;
            }
        }
        return sum;
    }
}
