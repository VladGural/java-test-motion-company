package pro.gural.company.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.company.domain.AddressServiceAware;
import pro.gural.company.domain.KafkaServiceAware;
import pro.gural.company.domain.exception.CompanyNotFoundRestException;

import java.util.List;
import java.util.Optional;

import static pro.gural.company.company.Converter.*;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Service
class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository repo;
    private final AddressServiceAware addressService;

    CompanyService(CompanyRepository repo,
                   AddressServiceAware addressService) {
        this.repo = repo;
        this.addressService = addressService;
    }

    public String createCompany(CompanyRequest req) {
        CompanyEntity companyEntity = toCreateCompanyEntity(req);
        repo.saveAndFlush(companyEntity);
        addressService.addCompanyAddresses(req.getCompanyAddress(), companyEntity.getId());
        return companyEntity.getId();
    }

    public Company getById(String companyId) {
        CompanyEntity companyEntity = getCompanyEntity(companyId);
        List<CompanyAddress> companyAddresses = addressService.getCompanyAddresses(companyId);
        return toCompany(companyEntity, companyAddresses);
    }

    public String updateCompany(String companyId, CompanyRequest req) {
        CompanyEntity companyEntity = getCompanyEntity(companyId);
        companyEntity = toUpdateCompanyEntity(req, companyEntity);
        repo.saveAndFlush(companyEntity);
        addressService.updateCompanyAddresses(req.getCompanyAddress(), companyId);
        return companyId;
    }

    public void deleteCompany(String companyId) {
        addressService.deleteAllCompanyAddresses(companyId);
        repo.deleteById(companyId);
    }

    private CompanyEntity getCompanyEntity(String companyId) {
        Optional<CompanyEntity> companyEntityOptional = repo.findById(companyId);
        return checkCompanyExist(companyEntityOptional, companyId);
    }

    private CompanyEntity checkCompanyExist(Optional<CompanyEntity> companyEntityOptional, String companyId) {
        if (companyEntityOptional.isEmpty()) {
            String msg = String.format("Company with id: %s does not exist", companyId);
            logger.error(msg);
            throw new CompanyNotFoundRestException(msg, companyId);
        }
        return companyEntityOptional.get();
    }
}

