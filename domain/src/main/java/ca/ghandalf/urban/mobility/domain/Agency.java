package ca.ghandalf.urban.mobility.domain;

public class Agency {

  public int id;
  public String name;
  public String url;
  public String timezone;
  public String language;
  public String phone;
  public String fareUrl;
  
  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTimezone() {
    return this.timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getFareUrl() {
    return this.fareUrl;
  }

  public void setFareUrl(String fareUrl) {
    this.fareUrl = fareUrl;
  }

  public String toString() {
    return 
    "Agency: ["
    + "id : " + this.id
    + ", name : " + this.name
    + ", url : " + this.url
    + ", timezone : " + this.timezone
    + ", language : " + this.language
    + ", phone : " + this.phone
    + ", fareUrl : " + this.fareUrl
    + "]";
  }
}
