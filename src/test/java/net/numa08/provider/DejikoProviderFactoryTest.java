package net.numa08.provider;

import org.junit.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
public class DejikoProviderFactoryTest {

    @Test
    public void createValidProvider() throws Exception{
        final DejikoProvider provider = DejikoProviderFactory.providerByName("pixiv");
        assertThat(provider, notNullValue());
    }

    @Test(expected = DejikoProviderFactory.DejikoProviderNotFoundException.class)
    public void thorwExceptionWhenInvalidName() throws Exception{
        DejikoProviderFactory.providerByName("hogehoge");
    }
}
