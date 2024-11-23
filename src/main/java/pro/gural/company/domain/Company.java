package pro.gural.company.domain;

import pro.gural.common.domain.CompanyStatusType;

import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
public class Company {
    private String id;
    private String name;
    private CompanyStatusType status;
    private String contactInformation;
    private String industry;
    private List<CompanyAddress> companyAddress;

    public String getId() {
        return id;
    }

    public Company setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Company setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyStatusType getStatus() {
        return status;
    }

    public Company setStatus(CompanyStatusType status) {
        this.status = status;
        return this;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public Company setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
        return this;
    }

    public String getIndustry() {
        return industry;
    }

    public Company setIndustry(String industry) {
        this.industry = industry;
        return this;
    }

    public List<CompanyAddress> getCompanyAddress() {
        return companyAddress;
    }

    public Company setCompanyAddress(List<CompanyAddress> companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", contactInformation='" + contactInformation + '\'' +
                ", industry='" + industry + '\'' +
                ", companyAddress=" + companyAddress +
                '}';
    }
}

