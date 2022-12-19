package br.com.stency.kits.service;

import br.com.stency.common.util.file.CommonConfig;
import br.com.stency.kits.data.Kit;
import org.bukkit.entity.Player;

import java.util.List;

public interface KitService {

    Kit get(String name);
    void delete(String name);
    Kit create(String name);
    Kit add(String name);

    List<Kit> all();
    CommonConfig config();

    void init();
    void stop();
}
