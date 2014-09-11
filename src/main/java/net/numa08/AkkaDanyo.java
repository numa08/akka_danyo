package net.numa08;

import net.numa08.provider.DejikoProvider;
import net.numa08.provider.DejikoProviderFactory;

public class AkkaDanyo {

    public static void main(String... args) {
        try {
            if (args.length == 0) {
                throw new IllegalArgumentException("Invalid Number of Argument!!!!");
            }
            final DejikoProvider provider = DejikoProviderFactory.providerByName(args[0]);
            final int retval = provider.downLoadDejikoAt(".");
            System.out.println(retval);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
