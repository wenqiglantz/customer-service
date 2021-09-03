package com.github.wenqiglantz.service.customerservice.bdd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import org.springframework.beans.factory.annotation.Configurable;

import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.ENGLISH;

/**
 * Class used to convert DataTable to Java Object using Jackson.
 * It assumes that you will use field names in Java POJO as Cucumber DataTable column headers.
 */
@Configurable
public class CucumberTypeRegistryConfigurer implements TypeRegistryConfigurer {

    private ObjectMapper mapper;

    public CucumberTypeRegistryConfigurer() {
        mapper = new ObjectMapper();

        // To serialize and deserialize java.time.LocalDate, LocalDateTime etc.
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
    }

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        Transformer transformer = new Transformer();
        typeRegistry.setDefaultDataTableCellTransformer(transformer);
        typeRegistry.setDefaultDataTableEntryTransformer(transformer);
        typeRegistry.setDefaultParameterTransformer(transformer);
    }

    private class Transformer implements ParameterByTypeTransformer, TableEntryByTypeTransformer,
            TableCellByTypeTransformer {

        @Override
        public Object transform(String s, Type type) {
            return mapper.convertValue(s, mapper.constructType(type));
        }

        @Override
        public Object transform(Map<String, String> map, Type type, TableCellByTypeTransformer tableCellByTypeTransformer) throws Throwable {
            return mapper.convertValue(map, mapper.constructType(type));
        }
    }
}