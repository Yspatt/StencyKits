package br.com.stency.kits.command;

import br.com.stency.common.util.command.CommonCommand;
import br.com.stency.common.util.command.annotation.Command;
import br.com.stency.common.util.command.annotation.Permission;
import br.com.stency.kits.Kits;
import br.com.stency.kits.data.Kit;
import br.com.stency.kits.service.KitService;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitCreateCommand extends CommonCommand {

    @Command("createkit")
    @Permission("stency.commands.createkit")
    @Override
    public void command(CommandSender commandSender, Player player, String[] arguments) {
        if (arguments.length < 2){
            player.sendMessage("");
            player.sendMessage("§7Obs: Os itens que estiverem em seu inventário serão os mesmos do kit.");
            player.sendMessage("§cUse /createkit (nome) (delay (#Delay em minutos)).");
            player.sendMessage("");
        }else{
            Kit kit = Kits.getInstance().getService(KitService.class).get(arguments[0]);
            if (kit != null){
                player.sendMessage("§cJá existe um kit com este nome.");
            }else{
                if (isLong(arguments[1])) {
                    kit = Kits.getInstance().getService(KitService.class).create(arguments[0]);
                    kit.setDelay(Long.parseLong(arguments[1]));
                    for (ItemStack content : player.getInventory().getContents()) {
                        if (content == null || content.getType() == Material.AIR)continue;
                        kit.getContents().add(content);
                    }
                    player.sendMessage("§aVocê criou o kit §f'" + kit.getName() + "'§a com sucesso.");
                }else{
                    player.sendMessage("§cO delay deve ser um número.");
                }
            }
        }
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }
}
