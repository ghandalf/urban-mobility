package ca.ghandalf.urban.mobility.restapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

  @Value("${server.port}")
  private int port;
   
  @Value("${application.name}")
  private String applicationName;
  
  @Value("${application.author}")
  private String applicationAuthor;

  @Value("${application.email}")
  private String applicationEmail;

  @Value("${application.modules}")
  private int applicationModules;


  public int getPort() {
      return port;
  }

  public void setPort(int port) {
      this.port = port;
  }

  public String getApplicationName() {
      return this.applicationName;
  }

  public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
  }

  public String getApplicationAuthor() {
      return this.applicationAuthor;
  }

  public void setApplicationAuthor(String applicationAuthor) {
      this.applicationAuthor = applicationAuthor;
  }

  public String getApplicationEmail() {
      return this.applicationEmail;
  }

  public void setApplicationEmail(String applicationEmail) {
      this.applicationEmail = applicationEmail;
  }

  public int getApplicationModules() {
      return this.applicationModules;
  }

  public void setApplicationModules(int applicationModules) {
      this.applicationModules = applicationModules;
  }

  public String toString() {
      return 
      "ApplicationProperties: [" 
      + "port : " + this.port
      + ", name : " + this.applicationName
      + ", author : " + this.applicationAuthor
      + ", email : " + this.applicationEmail
      + ", modules : " + this.applicationModules
      + "]";
  }
}
