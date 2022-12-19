package br.com.stency.kits.service.impl;

import br.com.stency.common.util.file.CommonConfig;
import br.com.stency.kits.Kits;
import br.com.stency.kits.data.Kit;
import br.com.stency.kits.service.KitService;
import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KitServiceImpl implements KitService {

    private Map<String,Kit> kitMap = Maps.newHashMap();
    private CommonConfig config = new CommonConfig(Kits.getInstance(),"kits.yml");

    @Override
    public Kit get(String name) {
        return kitMap.get(name);
    }

    @Override
    public void delete(String name) {
      kitMap.remove(name);
    }

    @Override
    public Kit add(String name) {
        Kit kit = new Kit(name);
        kitMap.put(name,kit);
        return kit;
    }

    @Override
    public Kit create(String name) {
        if (get(name) == null){
            return add(name);
        }
        return get(name);
    }

    @Override
    public List<Kit> all() {
        return new ArrayList<>(kitMap.values());
    }

    @Override
    public CommonConfig config() {
        return config;
    }

    @Override
    public void init() {
        ConfigurationSection section = config.getConfigurationSection("Kits");
        if (section == null)return;
        for (String value : section.getKeys(false)){
            Kit kit = add(value);
            kit.setDelay(config.getLong("Kits." + value + ".delay"));
            List<ItemStack> items = (List<ItemStack>) config.getList("Kits." + value + ".contents");
            kit.setContents(items);
        }
    }

    @Override
    public void stop() {
        for (Kit kit : all()){
            config.set("Kits." + kit.getName() + ".delay",kit.getDelay());
            config.set("Kits." + kit.getName() + ".contents",kit.getContents());
        }
        config.save();
    }
}
