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
    private final KafkaServiceAware kafkaService;

    CompanyService(CompanyRepository repo,
                   AddressServiceAware addressService,
                   KafkaServiceAware kafkaService) {
        this.repo = repo;
        this.addressService = addressService;
        this.kafkaService = kafkaService;
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

    private CompanyEntity getCompanyEntity(String companyId) {
        CompanyEntity companyEntity = repo.getById(companyId);
        checkCompanyExist(companyEntity, companyId);
        return companyEntity;
    }

    private void checkCompanyExist(CompanyEntity entity, String companyId) {
        if (entity == null) {
            String msg = String.format("Company with id: %s does not exist", companyId);
            logger.error(msg);
            throw new CompanyNotFoundRestException(msg, companyId);
        }
    }
}

