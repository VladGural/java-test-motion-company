package pro.gural.company.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.gural.company.domain.AddressServiceAware;
import pro.gural.company.domain.Company;
import pro.gural.company.domain.CompanyAddress;
import pro.gural.company.domain.exception.CompanyNotFoundRestException;

import java.util.List;

import static pro.gural.company.company.Converter.toCompany;
import static pro.gural.company.company.Converter.toCreateCompanyEntity;

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
        CompanyEntity companyEntity = repo.getById(companyId);
        checkCompanyExist(companyEntity, companyId);
        List<CompanyAddress> companyAddresses = addressService.getCompanyAddresses(companyId);
        return toCompany(companyEntity, companyAddresses);
    }

    private void checkCompanyExist(CompanyEntity entity, String companyId) {
        if (entity == null) {
            String msg = String.format("Company with id: %s does not exist", companyId);
            logger.error(msg);
            throw new CompanyNotFoundRestException(msg, companyId);
        }
    }
}

