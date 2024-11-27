package pro.gural.company.domain;

import pro.gural.common.domain.CompanyAddress;

import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
public interface AddressServiceAware {
    void addCompanyAddresses(List<CompanyAddress> companyAddressList, String companyId);

    List<CompanyAddress> getCompanyAddresses(String companyId);

    void updateCompanyAddresses(List<CompanyAddress> companyAddressListNew, String companyId);
}
