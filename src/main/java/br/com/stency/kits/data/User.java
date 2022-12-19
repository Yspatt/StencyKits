package br.com.stency.kits.data;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data public class User {

    private UUID uuid;
    private Map<String,Long> kits = Maps.newHashMap();

    public User(UUID uuid){
        this.uuid = uuid;
    }

    public boolean hasDelay(String kit){
        return kits.containsKey(kit);
    }

}
