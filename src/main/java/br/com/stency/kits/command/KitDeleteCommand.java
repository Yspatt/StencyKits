package br.com.stency.kits.command;

import br.com.stency.common.util.command.CommonCommand;
import br.com.stency.common.util.command.annotation.Aliases;
import br.com.stency.common.util.command.annotation.Command;
import br.com.stency.common.util.command.annotation.Permission;
import br.com.stency.kits.Kits;
import br.com.stency.kits.data.Kit;
import br.com.stency.kits.service.KitService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitDeleteCommand extends CommonCommand {

    @Command("deletekit")
    @Permission("stency.commands.deletekit")
    @Override
    public void command(CommandSender commandSender, Player player, String[] arguments) {
        if (arguments.length == 0){
            player.sendMessage("§cUse /deletekit (nome).");
        }else{
            Kit kit = Kits.getInstance().getService(KitService.class).get(arguments[0]);
            if (kit == null){
                player.sendMessage("§cEste kit não existe.");
            }else{
                Kits.getInstance().getService(KitService.class).delete(kit.getName());
                Kits.getInstance().getService(KitService.class).config().set("Kits." + kit.getName(),null);
                Kits.getInstance().getService(KitService.class).config().save();
                player.sendMessage("§aVocê deletou o kit §f'" + kit.getName() + "'§a com sucesso.");
            }
        }
    }
}
