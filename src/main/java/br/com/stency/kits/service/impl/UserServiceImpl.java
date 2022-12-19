package br.com.stency.kits.service.impl;

import br.com.stency.common.util.file.CommonConfig;
import br.com.stency.kits.Kits;
import br.com.stency.kits.data.User;
import br.com.stency.kits.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private Map<UUID, User> userMap = Maps.newHashMap();
    private CommonConfig config = new CommonConfig(Kits.getInstance(),"data.yml");

    @Override
    public User get(UUID uuid) {
        return userMap.get(uuid);
    }

    @Override
    public User create(UUID uuid) {
        if (get(uuid) == null){
            return add(uuid);
        }
        return get(uuid);
    }

    @Override
    public User add(UUID uuid) {
        User user = new User(uuid);
        userMap.put(uuid,user);
        return user;
    }

    @Override
    public List<User> all() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public CommonConfig config() {
        return config;
    }

    @Override
    public void init() {
        ConfigurationSection section = config.getConfigurationSection("Users");
        if (section == null)return;
        for (String value : section.getKeys(false)){
            User user = add(UUID.fromString(value));
            List<String> kits = config.getStringList("Users." + user.getUuid() + ".kits");
            for (String kit : kits){
                user.getKits().put(kit.split(";")[0], Long.valueOf(kit.split(";")[1]));
            }
        }
    }

    @Override
    public void stop() {
        for (User user : all()){
            List<String> kits = Lists.newArrayList();
            user.getKits().forEach((kit, aLong) -> {
                kits.add(kit + ";" + aLong);
            });
            config.set("Users." + user.getUuid().toString() + ".kits",kits);
        }
        config.save();
    }
}
