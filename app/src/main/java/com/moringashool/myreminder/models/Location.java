
package com.moringashool.myreminder.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


@Parcel
public class Location {

    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("address2")
    @Expose
    public String address2;
    @SerializedName("address3")
    @Expose
    public String address3;
    @SerializedName("address1")
    @Expose
    public String address1;
    @SerializedName("zip_code")
    @Expose
    public String zipCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Location() {
    }

    /**
     * 
     * @param country
     * @param zipCode
     * @param address3
     * @param city
     * @param address2
     * @param address1
     */
    public Location(String city, String country, String address2, String address3, String address1, String zipCode) {
        super();
        this.city = city;
        this.country = country;
        this.address2 = address2;
        this.address3 = address3;
        this.address1 = address1;
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s", this.address1, this.city, this.zipCode);
    }

}
