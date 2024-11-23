package pro.gural.company.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pro.gural.common.domain.AddressCategoryType;

import java.util.List;
import java.util.Objects;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Entity
@Table(name = "company_address")
class AddressEntity {
    @Id
    private String id;

    @Column(name = "company_id")
    private String companyId;

    private String country;

    private String city;

    private String street;

    private String zip;

    @Column(name = "category")
    private String addressCategory;

    public String getId() {
        return id;
    }

    public AddressEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public AddressEntity setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public AddressEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public AddressEntity setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getAddressCategory() {
        return addressCategory;
    }

    public AddressEntity setAddressCategory(String addressCategory) {
        this.addressCategory = addressCategory;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "id='" + id + '\'' +
                ", companyId='" + companyId + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", zip='" + zip + '\'' +
                ", addressCategory='" + addressCategory + '\'' +
                '}';
    }
}
