package pro.gural.company.company;

import org.springframework.http.HttpStatus;
import pro.gural.common.domain.AddressCategoryType;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.common.domain.CompanyStatusType;
import pro.gural.company.component_test.BaseComponentTest;
import pro.gural.company.util.Util;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

    public static Company updateCompany(BaseComponentTest restCall, String companyId, String companyName, CompanyStatusType companyStatus,
                                        String contactInformation, String industry, List<CompanyAddress> companyAddresses) throws Exception {
        String url = "/v1/companies/" + companyId;
        CompanyRequest companyRequest = new CompanyRequest()
                .setName(companyName)
                .setStatus(companyStatus)
                .setContactInformation(contactInformation)
                .setIndustry(industry)
                .setCompanyAddress(companyAddresses);
        String put = restCall.put(url, companyRequest);
        return Util.fromJson(put, Company.class);
    }

    public static CompanyAddress getAddressByCity(List<CompanyAddress> addresses, String city) {
        return addresses.stream()
                .filter(a -> city.equals(a.getCity()))
                .findFirst().orElse(null);
    }

    public static void checkCompanyData(Company company, String name, CompanyStatusType status, String contactInformation,
                                        String industry, Map<String, List<AddressCategoryType>> addressCategoryMap) {
        assertThat(company.getId() != null, is(true));
        assertThat(company.getName().equals(name), is(true));
        assertThat(company.getStatus().equals(status), is(true));
        assertThat(company.getContactInformation().equals(contactInformation), is(true));
        assertThat(company.getIndustry().equals(industry), is(true));
        assertThat(company.getCompanyAddress().size(), is(addressCategoryMap.size()));
        for (CompanyAddress address : company.getCompanyAddress()) {
            List<AddressCategoryType> checkAddressCategoryTypes = addressCategoryMap.get(address.getCity());
            assertThat(checkAddressCategoryTypes != null, is(true));
            assertThat(address.getAddressCategory().size(), is(checkAddressCategoryTypes.size()));
            assertThat(address.getAddressCategory().containsAll(checkAddressCategoryTypes), is(true));
        }
    }
}
