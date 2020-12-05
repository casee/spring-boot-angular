package com.frakton;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api")
public class BackendService {

    @RequestMapping("version")
    public String getVersion() {
        final Package objPackage = this.getClass().getPackage();
        final String name = objPackage.getImplementationTitle();
        final String version = objPackage.getImplementationVersion();
        log.info("Package name: {}", name);
        log.info("Package version: {}", version);

        return version;
    }

}