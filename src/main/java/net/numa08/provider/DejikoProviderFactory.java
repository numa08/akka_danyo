package net.numa08.provider;

import net.numa08.pixiv.PixivProvider;

import java.util.HashMap;

public class DejikoProviderFactory {

    private static final HashMap<String, DejikoProvider> providers = new HashMap<String, DejikoProvider>(){{
        put("pixiv", new PixivProvider());
    }};

    public static class DejikoProviderNotFoundException extends Exception{
        public DejikoProviderNotFoundException(String s) {
            super(s);
        }
    }

    public static DejikoProvider providerByName(String name) throws DejikoProviderNotFoundException {
        final DejikoProvider provider = providers.get(name);
        if (provider == null) {
            throw new DejikoProviderNotFoundException(name + " Provider Not Found");
        }
        return provider;
    }
}
