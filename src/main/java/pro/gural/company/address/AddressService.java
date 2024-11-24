package pro.gural.company.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.company.domain.AddressServiceAware;

import java.util.List;

import static pro.gural.company.address.Converter.toAddressEntityList;
import static pro.gural.company.address.Converter.toCompanyAddressList;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Service
class AddressService implements AddressServiceAware {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final AddressRepository repo;

    AddressService(AddressRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addCompanyAddresses(List<CompanyAddress> companyAddressList, String companyId) {
        List<AddressEntity> addressEntities = toAddressEntityList(companyAddressList, companyId);
        repo.saveAllAndFlush(addressEntities);
    }

    @Override
    public List<CompanyAddress> getCompanyAddresses(String companyId) {
        List<AddressEntity> addressEntities = repo.getByCompanyId(companyId);
        return toCompanyAddressList(addressEntities);
    }
}
