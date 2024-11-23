package pro.gural.company.company;

import org.springframework.http.HttpStatus;
import pro.gural.common.domain.CompanyStatusType;
import pro.gural.company.component_test.BaseComponentTest;
import pro.gural.company.domain.Company;
import pro.gural.company.domain.CompanyAddress;
import pro.gural.company.util.Util;

import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
public class CompanyClient {

    public static Company createCompany(BaseComponentTest restCall, String companyName, CompanyStatusType companyStatus,
                          String contactInformation, String industry, List<CompanyAddress> companyAddresses) throws Exception {
        String url = "/v1/companies";
        CompanyRequest companyRequest = new CompanyRequest()
                .setName(companyName)
                .setStatus(companyStatus)
                .setContactInformation(contactInformation)
                .setIndustry(industry)
                .setCompanyAddress(companyAddresses);
        String post = restCall.post(url, companyRequest, HttpStatus.CREATED);
        return Util.fromJson(post, Company.class);
    }

    public static Company getById(BaseComponentTest restCall, String companyId) throws Exception {
        String url = "/v1/companies/" + companyId;
        String get = restCall.get(url);
        return Util.fromJson(get, Company.class);
    }

}
