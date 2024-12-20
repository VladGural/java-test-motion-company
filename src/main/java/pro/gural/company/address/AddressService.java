package pro.gural.company.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.company.domain.AddressServiceAware;

import java.util.List;

import static pro.gural.company.address.Converter.*;

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
        List<AddressEntity> addressEntities = toCreateAddressEntityList(companyAddressList, companyId);
        repo.saveAllAndFlush(addressEntities);
    }

    @Override
    public List<CompanyAddress> getCompanyAddresses(String companyId) {
        List<AddressEntity> addressEntities = repo.getByCompanyId(companyId);
        return toCompanyAddressList(addressEntities);
    }

    @Override
    public void updateCompanyAddresses(List<CompanyAddress> companyAddressListNew, String companyId) {
        List<AddressEntity> companyAddressListOld = repo.getByCompanyId(companyId);
        List<AddressEntity> oldAddress = getOldAddress(companyAddressListOld, companyAddressListNew);
        companyAddressListOld.removeAll(oldAddress);
        repo.deleteAll(oldAddress);
        List<AddressEntity> updatedAddressEntityList =
                toUpdateAddressEntityList(companyAddressListOld, companyAddressListNew, companyId);
        repo.saveAllAndFlush(updatedAddressEntityList);
    }

    @Override
    public void deleteAllCompanyAddresses(String companyId) {
        repo.deleteByCompanyId(companyId);
    }
}
