/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
package pro.gural.company.company;

import jakarta.persistence.*;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.common.domain.CompanyStatusType;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
class CompanyEntity {
    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyStatusType status;

    @Column(name = "contact_information")
    private String contactInformation;

    private String industry;

    public String getId() {
        return id;
    }

    public CompanyEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyStatusType getStatus() {
        return status;
    }

    public CompanyEntity setStatus(CompanyStatusType status) {
        this.status = status;
        return this;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public CompanyEntity setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public String getIndustry() {
        return industry;
    }

    public CompanyEntity setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyEntity that = (CompanyEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", contactInformation='" + contactInformation + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}

class CompanyRequest {
    private String name;
    private CompanyStatusType status;
    private String contactInformation;
    private String industry;
    private List<CompanyAddress> companyAddress;

    public String getName() {
        return name;
    }

    public CompanyRequest setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyStatusType getStatus() {
        return status;
    }

    public CompanyRequest setStatus(CompanyStatusType status) {
        this.status = status;
        return this;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public CompanyRequest setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public String getIndustry() {
        return industry;
    }

    public CompanyRequest setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public List<CompanyAddress> getCompanyAddress() {
        return companyAddress;
    }

    public CompanyRequest setCompanyAddress(List<CompanyAddress> companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    @Override
    public String toString() {
        return "CompanyRequest{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", contactInformation='" + contactInformation + '\'' +
                ", industry='" + industry + '\'' +
                ", companyAddress=" + companyAddress +
                '}';
    }
}