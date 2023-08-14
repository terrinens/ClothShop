package com.cloth.clothshop.Products.ProductsSetting;

import org.hibernate.MappingException;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

public class ProudctsGenerator extends TableGenerator {

    @Override
    public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {

        super.configure(type, parameters, serviceRegistry);
    }
}
