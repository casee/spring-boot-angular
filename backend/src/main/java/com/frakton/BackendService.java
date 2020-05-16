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
        Package objPackage = this.getClass().getPackage();
        String name = objPackage.getImplementationTitle();
        String version = objPackage.getImplementationVersion();
        log.info("Package name: {}", name);
        log.info("Package version: {}", version);

        return version;
    }

}