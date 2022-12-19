package br.com.stency.kits;

import br.com.stency.common.helpers.CommonPlugin;
import br.com.stency.kits.command.KitCommand;
import br.com.stency.kits.command.KitCreateCommand;
import br.com.stency.kits.command.KitDeleteCommand;
import br.com.stency.kits.service.KitService;
import br.com.stency.kits.service.UserService;
import br.com.stency.kits.service.impl.KitServiceImpl;
import br.com.stency.kits.service.impl.UserServiceImpl;
import lombok.Getter;

public class Kits extends CommonPlugin {

    // Permissão padrão dos kits: stency.kits.(nome)
    // Permissão bypass: stency.bypass.kits

    public Kits(){
        instance = this;
        provideService(KitService.class,new KitServiceImpl());
        provideService(UserService.class,new UserServiceImpl());
    }

    @Getter
    private static Kits instance;

    @Override
    public void enable() {
        getService(KitService.class).init();
        getService(UserService.class).init();
        register(this,new KitCommand(),new KitCreateCommand(),new KitDeleteCommand());
    }

    @Override
    public void disable() {
        getService(KitService.class).stop();
        getService(UserService.class).stop();
    }

    @Override
    public void load() {

    }
}
