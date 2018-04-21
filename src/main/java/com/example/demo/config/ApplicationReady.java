package com.example.demo.config;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReady implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private Jdbi jdbi;

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        System.out.println("Running hack to avoid race condition");
        //System.out.println(jdbi.onDemand(CarDao.class).getCars(";").size());
        System.out.println("Hack executed successfully");
    }
}
