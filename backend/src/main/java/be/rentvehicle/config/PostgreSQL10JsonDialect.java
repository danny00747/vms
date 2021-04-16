package be.rentvehicle.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.dialect.PostgreSQL10Dialect;
import org.hibernate.dialect.PostgreSQL95Dialect;
import org.springframework.context.annotation.Configuration;

import java.sql.Types;
@Configuration
public class PostgreSQL10JsonDialect extends PostgreSQL10Dialect  {

    public PostgreSQL10JsonDialect() {
        super();
        registerHibernateType(Types.OTHER, StringArrayType.class.getName());
        registerHibernateType(Types.OTHER, IntArrayType.class.getName());
        registerHibernateType(Types.OTHER, JsonStringType.class.getName());
        registerHibernateType(Types.OTHER, JsonBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeBinaryType.class.getName());
        registerHibernateType(Types.OTHER, JsonNodeStringType.class.getName());
        registerColumnType(Types.JAVA_OBJECT, "jsonb");
        registerColumnType(Types.ARRAY, "json_build_array");
        registerColumnType(Types.ARRAY, "json");
        registerColumnType(Types.JAVA_OBJECT, "json");

    }
}
