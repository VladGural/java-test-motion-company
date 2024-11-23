package pro.gural.company.kafka;

import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.company.domain.Company;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class Converter {
    public static CompanyKafkaMessage toCompanyKafkaMessage(Company company, KafkaActionType action) {
        return new CompanyKafkaMessage()
                .setAction(action)
                .setId(company.getId())
                .setName(company.getName());
    }
}
