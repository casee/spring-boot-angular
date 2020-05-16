package com.frakton;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class BackendService {

    @RequestMapping("version")
    public String getVersion() {
        Package objPackage = this.getClass().getPackage();
        String name = objPackage.getImplementationTitle();
        String version = objPackage.getImplementationVersion();
        System.out.println("Package name: " + name);
        System.out.println("Package version: " + version);

        return version;
    }

}