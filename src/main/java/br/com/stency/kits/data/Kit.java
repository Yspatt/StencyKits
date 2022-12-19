package br.com.stency.kits.data;

import com.google.common.collect.Lists;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data public class Kit {

    private String name;
    private long delay;
    private List<ItemStack> contents = Lists.newArrayList();

    public Kit(String name){
        this.name = name;
    }

    public void give(User user,Player player){
        for (ItemStack item : this.getContents()) {
            if (item == null) continue;
            player.getInventory().addItem(item);
        }
        user.getKits().put(this.getName(), System.currentTimeMillis() + (this.getDelay() * 60000));
    }
}
