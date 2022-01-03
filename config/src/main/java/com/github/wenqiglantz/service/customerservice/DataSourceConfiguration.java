package com.github.wenqiglantz.service.customerservice;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.codec.binary.Base64InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Configuration
@EnableConfigurationProperties({DataSourceTruststoreProperties.class})
@EnableEnversRepositories
@EnableTransactionManagement
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

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSourceTruststoreProperties trustStoreProperties)
            throws Exception{
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.arisglobal.service.configservice.persistence.entity");
        factory.setDataSource(dataSource(trustStoreProperties));
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}