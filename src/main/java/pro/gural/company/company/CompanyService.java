package pro.gural.company.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.gural.company.domain.exception.CompanyNotFoundRestException;

import static pro.gural.company.company.Converter.toCompany;
import static pro.gural.company.company.Converter.toCreateCompanyEntity;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class CompanyService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    private final CompanyRepository repo;

    CompanyService(CompanyRepository repo) {
        this.repo = repo;
    }

    public String createCompany(CompanyRequest req) {
        CompanyEntity companyEntity = toCreateCompanyEntity(req);
        repo.saveAndFlush(companyEntity);
        return companyEntity.getId();
    }

    public Company getById(String companyId) {
        CompanyEntity companyEntity = repo.getById(companyId);
        checkCompanyExist(companyEntity, companyId);
        // TODO add companyAddress
        return toCompany(companyEntity, null);
    }

    private void checkCompanyExist(CompanyEntity entity, String companyId) {
        if (entity == null) {
            String msg = String.format("Company with id: %s does not exist", companyId);
            logger.error(msg);
            throw new CompanyNotFoundRestException(msg, companyId);
        }
    }
}

