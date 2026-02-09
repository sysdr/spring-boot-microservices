package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ThreadDemoController {

    @GetMapping("/platform-thread-task")
    public String platformThreadTask() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log.info("Platform Thread Task: Started on thread {}", threadName);
        Thread.sleep(2000); // Simulate blocking I/O
        log.info("Platform Thread Task: Finished on thread {}", threadName);
        return "Platform Thread Task Completed on " + threadName;
    }

    @GetMapping("/virtual-thread-task")
    public String virtualThreadTask() throws InterruptedException {
        String threadName = Thread.currentThread().getName();
        log.info("Virtual Thread Task: Started on thread {}", threadName);
        Thread.sleep(2000); // Simulate blocking I/O
        log.info("Virtual Thread Task: Finished on thread {}", threadName);
        return "Virtual Thread Task Completed on " + threadName;
    }

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String root() {
        return "<!DOCTYPE html>\n" +
               "<html>\n" +
               "<head>\n" +
               "    <title>Virtual Threads Demo</title>\n" +
               "    <style>\n" +
               "        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }\n" +
               "        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n" +
               "        h1 { color: #2c3e50; border-bottom: 3px solid #3498db; padding-bottom: 10px; }\n" +
               "        h2 { color: #34495e; margin-top: 30px; }\n" +
               "        .endpoint-list { list-style: none; padding: 0; }\n" +
               "        .endpoint-item { background: #ecf0f1; margin: 10px 0; padding: 15px; border-radius: 5px; border-left: 4px solid #3498db; }\n" +
               "        .method { display: inline-block; background: #3498db; color: white; padding: 3px 8px; border-radius: 3px; font-weight: bold; margin-right: 10px; }\n" +
               "        .path { color: #2c3e50; font-weight: bold; }\n" +
               "        .description { color: #7f8c8d; margin-top: 5px; }\n" +
               "        a { color: #3498db; text-decoration: none; }\n" +
               "        a:hover { text-decoration: underline; }\n" +
               "    </style>\n" +
               "</head>\n" +
               "<body>\n" +
               "    <div class=\"container\">\n" +
               "        <h1>🚀 Virtual Threads Demo Application</h1>\n" +
               "        <p>Welcome to the Spring Boot Virtual Threads demonstration application.</p>\n" +
               "        \n" +
               "        <h2>Available Endpoints</h2>\n" +
               "        <ul class=\"endpoint-list\">\n" +
               "            <li class=\"endpoint-item\">\n" +
               "                <span class=\"method\">GET</span>\n" +
               "                <a href=\"/info\" class=\"path\">/info</a>\n" +
               "                <div class=\"description\">Application information and system details</div>\n" +
               "            </li>\n" +
               "            <li class=\"endpoint-item\">\n" +
               "                <span class=\"method\">GET</span>\n" +
               "                <a href=\"/platform-thread-task\" class=\"path\">/platform-thread-task</a>\n" +
               "                <div class=\"description\">Platform thread demonstration (traditional threads)</div>\n" +
               "            </li>\n" +
               "            <li class=\"endpoint-item\">\n" +
               "                <span class=\"method\">GET</span>\n" +
               "                <a href=\"/virtual-thread-task\" class=\"path\">/virtual-thread-task</a>\n" +
               "                <div class=\"description\">Virtual thread demonstration (Java 21 virtual threads)</div>\n" +
               "            </li>\n" +
               "            <li class=\"endpoint-item\">\n" +
               "                <span class=\"method\">GET</span>\n" +
               "                <a href=\"/actuator/health\" class=\"path\">/actuator/health</a>\n" +
               "                <div class=\"description\">Application health check endpoint</div>\n" +
               "            </li>\n" +
               "            <li class=\"endpoint-item\">\n" +
               "                <span class=\"method\">GET</span>\n" +
               "                <a href=\"/actuator/metrics\" class=\"path\">/actuator/metrics</a>\n" +
               "                <div class=\"description\">Application metrics and monitoring data</div>\n" +
               "            </li>\n" +
               "        </ul>\n" +
               "    </div>\n" +
               "</body>\n" +
               "</html>";
    }

    @GetMapping("/info")
    public String info() {
        return "Application is running with " + Runtime.getRuntime().availableProcessors() + " CPU cores.";
    }
}
