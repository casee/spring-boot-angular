# spring-boot-angular
Cloned and upgraded from  
[UTILIZING MAVEN FRONT-END PLUGIN FOR ANGULAR & SPRING BOOT](https://frakton.com/utilizing-maven-front-end-plugin-for-angular-spring-boot/)  
[FraktonDevelopers/spring-boot-angular-maven-build](https://github.com/FraktonDevelopers/spring-boot-angular-maven-build)


## Architecture Overview / Prerequisites

Java 8 - Download and install the JDK 8.  
Node.js - Download and install  
Angular CLI - used to bootstrap the Angular application. You can install it using npm as a default package manager for the JavaScript runtime environment Node.js

In the example that I’m detailing below, we used Spring Boot 2.2.1  RELEASE version.


## Define project modules

The first step of the implementation is to create a multi-module Spring Boot 
application. Initially we create a parent module **spring-boot-angular-maven-build**
which will contain the **backend** module and the **frontend** module.

Assuming that you created the **backend** and **frontend** projects in the 
specified modules.
 * **backend** - will contain the backend project
 * **frontend** - will contain the frontend project

Then, we need to create the modules using Maven Build Tool. We add the modules in the main **pom.xml**

```xml
<modules>
   <module>backend</module>
   <module>frontend</module>
</modules>
```
Also here we have to specify, the packaging to serve as a container for our sub-modules

```xml
<packaging>pom</packaging>
```

## Implementing the back-end side

In the **backend** module we implement the parent section

```xml
<parent>
   <groupId>com</groupId>
   <artifactId>frakton</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</parent>

<artifactId>backend</artifactId>
```
Next, we add some useful plugins:
* maven-failsafe-plugin - is used and designed for integration tests
* maven-surefire-plugin - is used to run unit tests
* spring-boot-maven-plugin - This plugin provides several usages allowing us to package executable JAR or WAR archives and run the application.

```xml
<build>
   <plugins>
       <plugin>
           <groupId>org.apache.maven.plugins</groupId>
           <artifactId>maven-failsafe-plugin</artifactId>
       </plugin>
       <plugin>
           <groupId>org.apache.maven.plugins</groupId>
           <artifactId>maven-surefire-plugin</artifactId>
       </plugin>
       <plugin>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-maven-plugin</artifactId>
       </plugin>
   </plugins>
</build>
```

After implementing the plugins section, we need to include the **frontend** dependency.    
Our **backend** module will use **frontend** html resources.

```xml
<dependencies>
   <dependency>
       <groupId>com</groupId>
       <artifactId>frontend</artifactId>
       <version>${project.version}</version>
       <scope>runtime</scope>
   </dependency>
 <!-- Other Dependencies -->
</dependencies>
```

## Implement front-end side

Next, we need to implement the pom.xml in **frontend** by adding the parent section:

```xml
<parent>
   <groupId>com</groupId>
   <artifactId>frakton</artifactId>
   <version>0.0.1-SNAPSHOT</version>
</parent>

<artifactId>frontend</artifactId>
```

To execute some of npm commands we need the **frontend-maven-plugin**.
This plugin comes with a set of built-in commands which we can use for triggering npm commands.

```xml 
<properties>
    <frontend-maven-plugin.version>1.7.6</frontend-maven-plugin.version>
    <node.version>v12.16.3</node.version>
    <npm.version>6.14.4</npm.version>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>com.github.eirslett</groupId>
            <artifactId>frontend-maven-plugin</artifactId>
            <version>${frontend-maven-plugin.version}</version>
            <configuration>
                <workingDirectory>./</workingDirectory>
                <nodeVersion>${node.version}</nodeVersion>
                <npmVersion>${npm.version}</npmVersion>
            </configuration>
            <executions>
                <execution>
                    <id>install node and npm</id>
                    <goals>
                        <goal>install-node-and-npm</goal>
                    </goals>
                </execution>
                <execution>
                    <id>npm install</id>
                    <goals>
                        <goal>npm</goal>
                    </goals>
                </execution>
                <execution>
                    <id>npm run build</id>
                    <goals>
                        <goal>npm</goal>
                    </goals>
                    <configuration>
                        <arguments>run build</arguments>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

In the configuration tag, we specify the working directory, and we select the Node and Npm versions.

We need to define output directory in angular.json
```json
{
  "projects": {
    "frakton": {
      "architect": {
        "build": {
          "options": {
            "outputPath": "target/classes/static"
          }
        }
      }
    }
  }
}
```
Spring bu default will look for html resources in classpath:/static/ directory

## Testing back-end and front-end

After completing the configurations steps, we make sure our instances are working correctly before the build. First we can run the backend project by using:
**mvn spring-boot:run**

*Also make sure you execute the command inside the backend module. 

Next. we can start our Angular project using:
**ng serve**

## Build project

If everything seems to work correctly we can build the project using:
**mvn clean install**

*Make sure you are executing the command in the spring-boot-angular parent module*

After building the application two jars generated:
 * backend-0.0.1-SNAPSHOT.jar
 * frontend-0.0.1-SNAPSHOT.jar

If you want to run the executable JAR, open terminal and run:

java -jar backend-0.0.1-SNAPSHOT.jar

## Allow Angular to handle routes

After running the JAR file, when accessing addresses like https://localhost:8080/demo directly  
we will have a Whitelabel Error Page.

This happens because Angular by default all paths are supported and accessible
but Spring Boot tries to manage paths by itself. 
To fix this, we need to add some configurations. Create a package **config/**
and create **WebConfig.java**. This class has to implement **WebMvcConfigurer** and 
to use **ResourceHandlers**.

```java
@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
    }

}
```
We need to mark the class with @Component annotation. Spring will instantiate the class at runtime.
 
The /** pattern is matched by **AntPathMatcher** to directories in the path,
so the configuration will be applied to our project routes. 
Also the **PathResourceResolver** will try to find any resource under the
location given, so all the requests that are not handled by Spring Boot 
will be redirected to *static/index.html* giving access to Angular to manage them.

## Add proxy.config.json to develop frontend separately

To develop **frontend** independently we will add **proxy.conf.json**
```json
{
  "/api": {
    "target": "http://localhost:8080",
    "secure": false
  }
}
```
This config says that all requests from our **frontend** which start with "/api"
will be redirected to our backend.

Also we should update our **start** script in **package.json** 
```json
{
  "scripts": {
    "start": "ng serve --proxy-config proxy.conf.json",
  }
}
```

Now we can just run our backend like this
```shell script
npm start
```
And modify our frontend components.  
With each modification node will recompile our frontend and we will see changes in browser immediately. 

Once the application has started, we can see the main page with navbar and footer.  
Angular Demo page is available under **Demo** navbar item.
