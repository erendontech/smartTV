package com.stvc.persistence;

import org.springframework.context.ConfigurableApplicationContext;

public class FacadeDao<T> {

    public T contentDao;

    public FacadeDao(ConfigurableApplicationContext context, String beanName){
        contentDao = (T) context.getBean(beanName);
    }

}
