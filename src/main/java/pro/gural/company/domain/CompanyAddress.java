package pro.gural.company.domain;

import pro.gural.common.domain.AddressCategoryType;

import java.util.List;
import java.util.Objects;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
public class CompanyAddress {
    private String id;
    private String companyId;
    private String country;
    private String city;
    private String street;
    private String zip;
    private List<AddressCategoryType> addressCategory;

    public String getId() {
        return id;
    }

    public CompanyAddress setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public CompanyAddress setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public CompanyAddress setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CompanyAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public CompanyAddress setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public CompanyAddress setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public List<AddressCategoryType> getAddressCategory() {
        return addressCategory;
    }

    public CompanyAddress setAddressCategory(List<AddressCategoryType> addressCategory) {
        this.addressCategory = addressCategory;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyAddress{" +
                "id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", addressCategory=" + addressCategory +
                '}';
    }
}
