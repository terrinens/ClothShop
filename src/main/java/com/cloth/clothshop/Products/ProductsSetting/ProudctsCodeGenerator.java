package com.cloth.clothshop.Products.ProductsSetting;

import org.hibernate.MappingException;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

public class ProudctsCodeGenerator extends SequenceStyleGenerator {

    public static final String KIND = "kind";
    public static final String ST_VALUE = "st_value";
    public static final String INCR_VALUE = "incr_value";

    private String kind;
    private int st_value;
    private int incr_value;

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {

        kind = ConfigurationHelper.getString(KIND, parameters);
        st_value = ConfigurationHelper.getInteger(ST_VALUE, parameters);
        incr_value = ConfigurationHelper.getInteger(INCR_VALUE, parameters);
    }
}
