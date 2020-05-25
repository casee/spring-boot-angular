package com.frakton.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import java.text.NumberFormat;
import java.util.Locale;

public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationReadyEventListener.class);

    private final long startMillis = System.currentTimeMillis();

    public ApplicationReadyEventListener() {
        log.info("START");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationEvent) {
        log.info("**************************************************");
        log.info("    startup took: {} ms", NumberFormat.getNumberInstance(Locale.US).format(
                System.currentTimeMillis() - startMillis));
        log.info("**************************************************");
    }

}