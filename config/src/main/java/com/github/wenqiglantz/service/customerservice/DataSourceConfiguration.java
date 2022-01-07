package com.github.wenqiglantz.service.customerservice;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.codec.binary.Base64InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Configuration
@EnableConfigurationProperties({DataSourceTruststoreProperties.class})
public class DataSourceConfiguration {
    private final boolean tlsEnabled;

    public DataSourceConfiguration(@Value("${spring.datasource.tlsEnabled:false}") boolean tlsEnabled) {
        this.tlsEnabled = tlsEnabled;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource dataSource(DataSourceTruststoreProperties trustStoreProperties) throws Exception {
        HikariDataSource ds = DataSourceBuilder.create().type(HikariDataSource.class).build();
        if (tlsEnabled) {
            try (InputStream in = new Base64InputStream(new ByteArrayInputStream(trustStoreProperties.getContent().getBytes(StandardCharsets.UTF_8.name())))) {
                Files.copy(in,
                        Paths.get(trustStoreProperties.getLocation()),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            ds.addDataSourceProperty("javax.net.ssl.trustStore", trustStoreProperties.getLocation());
            ds.addDataSourceProperty("javax.net.ssl.trustStoreType", trustStoreProperties.getType());
            ds.addDataSourceProperty("javax.net.ssl.trustStorePassword", trustStoreProperties.getPassword());
        }
        return ds;
    }
}