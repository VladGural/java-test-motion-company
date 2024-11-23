package pro.gural.company.company;

import pro.gural.company.domain.Company;
import pro.gural.company.domain.CompanyAddress;

import java.util.List;
import java.util.UUID;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class Converter {
    public static CompanyEntity toCreateCompanyEntity(CompanyRequest req) {
        return new CompanyEntity()
                .setId(UUID.randomUUID().toString())
                .setName(req.getName())
                .setStatus(req.getStatus())
                .setContactInformation(req.getContactInformation())
                .setIndustry(req.getIndustry());
    }

    public static CompanyEntity toUpdateCompanyEntity(CompanyRequest req) {
        return new CompanyEntity()
                .setName(req.getName())
                .setStatus(req.getStatus())
                .setContactInformation(req.getContactInformation())
                .setIndustry(req.getIndustry());
    }

    public static Company toCompany(CompanyEntity entity, List<CompanyAddress> companyAddressList) {
        return new Company()
                .setId(entity.getId())
                .setName(entity.getName())
                .setStatus(entity.getStatus())
                .setContactInformation(entity.getContactInformation())
                .setIndustry(entity.getIndustry())
                .setCompanyAddress(companyAddressList);
    }
}
