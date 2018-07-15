package com.crossover.techtrial.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * Panel class hold information related to a Solar panel.
 * 
 * @author Crossover
 *
 */
@Entity
@Table(name = "panel")
public class Panel implements Serializable {

  private static final long serialVersionUID = -8527695980909864257L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "serial")
  private String serial;

  @Column(name = "longitude")
  private Double longitude;

  @Column(name = "latitude")
  private Double latitude;

  @Nullable
  @Column(name = "brand")
  private String brand;



  public Panel(Long id, @NotNull String serial, Double longitude, Double latitude, String brand) {
	this.id = id;
	this.serial = serial;
	this.longitude = longitude;
	this.latitude = latitude;
	this.brand = brand;
}

  public Panel() {

  }

public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSerial() {
    return serial;
  }

  public void setSerial(String serial) throws Exception{

    if (serial == null) throw new Exception();
    if (serial.length() < 16) throw new Exception("Serial number must be 16 characters long");
    this.serial = serial;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) throws Exception{
    if (getDecimalPlaces(longitude) != 6) throw new Exception("Longitude must be of 6 decimal places!");
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) throws Exception{
    if (getDecimalPlaces(latitude) != 6) throw new Exception("Latitude must be of 6 decimal places!");
    this.latitude = latitude;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  /*
   * Id, Serial and Brand are only fields required to uniquely identify a Panel
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((brand == null) ? 0 : brand.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((serial == null) ? 0 : serial.hashCode());
    return result;
  }

  /*
   * Id, Serial and Brand are only fields required to uniquely identify a Panel
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Panel other = (Panel) obj;
    if (brand == null) {
      if (other.brand != null) {
        return false;
      }
    } else if (!brand.equals(other.brand)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (serial == null) {
      if (other.serial != null) {
        return false;
      }
    } else if (!serial.equals(other.serial)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Panel [id=" + id + ", serial=" + serial + ", longitude=" + longitude + ", latitude="
        + latitude + ", brand=" + brand + "]";
  }

  private int getDecimalPlaces(Double number) {
    String str = String.valueOf(number);
    int decimalPlaces = str.length() - (str.indexOf(".") + 1);
    return decimalPlaces > 0 ? decimalPlaces : 0;
  }
}
