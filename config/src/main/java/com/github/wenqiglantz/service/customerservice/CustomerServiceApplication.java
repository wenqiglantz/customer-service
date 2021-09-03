package com.github.wenqiglantz.service.customerservice;

import com.github.wenqiglantz.service.customerservice.appconfig.AppConfigContext;
import com.github.wenqiglantz.service.customerservice.persistence.PersistenceContext;
import com.github.wenqiglantz.service.customerservice.restcontroller.RestControllerContext;
import com.github.wenqiglantz.service.customerservice.service.ServiceContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

@SpringBootApplication
@Import({
        AppConfigContext.class,
        PersistenceContext.class,
        ServiceContext.class,
        RestControllerContext.class
})
public class CustomerServiceApplication {

  public static void main(String[] args) throws Exception {
    Options options = new Options();
    options.addRequiredOption("p", "port", true, "The port this app will listen on");

    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    // If port string is not valid, it will throw an exception.
    int port = Integer.parseInt(cmd.getOptionValue("port"));

    // Start Dapr's callback endpoint by passing in the port.
    SpringApplication app = new SpringApplication(CustomerServiceApplication.class);
    app.run(String.format("--server.port=%d", port));
  }
}
