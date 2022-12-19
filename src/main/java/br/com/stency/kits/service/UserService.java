package br.com.stency.kits.service;

import br.com.stency.common.util.file.CommonConfig;
import br.com.stency.kits.data.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User get(UUID uuid);
    User create(UUID uuid);
    User add(UUID uuid);

    List<User> all();
    CommonConfig config();

    void init();
    void stop();
}
