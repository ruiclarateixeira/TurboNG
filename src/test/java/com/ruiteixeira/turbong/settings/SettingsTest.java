package com.ruiteixeira.turbong.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class SettingsTest {
    @Test
    public void instantiatingSettingsFromJsonWithSSL() throws Exception {
        String jsonSettings = IOUtils.toString(SettingsTest.class.getResourceAsStream("/test_ssl_config.json"));
        Settings settings = new ObjectMapper().readValue(jsonSettings, Settings.class);
        assertThat(settings, is(not(nullValue())));
        assertThat(settings.getListeningPort(), is(123));
        assertThat(settings.getNumberOfThreads(), is(5));
        assertThat(settings.isSslConnection(), is(true));
        assertThat(settings.getSslKeysPath(), is("path"));
    }

    @Test
    public void instantiatingSettingsFromJsonNoSSL() throws Exception {
        String jsonSettings = IOUtils.toString(SettingsTest.class.getResourceAsStream("/test_no_ssl_config.json"));
        Settings settings = new ObjectMapper().readValue(jsonSettings, Settings.class);
        assertThat(settings, is(not(nullValue())));
        assertThat(settings.getListeningPort(), is(123));
        assertThat(settings.getNumberOfThreads(), is(5));
        assertThat(settings.isSslConnection(), is(false));
        assertThat(settings.getSslKeysPath(), is(nullValue()));
    }
}