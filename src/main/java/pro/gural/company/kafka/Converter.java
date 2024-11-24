package pro.gural.company.kafka;

import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;

import java.time.Instant;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class Converter {
    public static CompanyKafkaMessage toCompanyKafkaMessage(Company company, KafkaActionType action) {
        return new CompanyKafkaMessage()
                .setAction(action)
                .setCompany(company)
                .setEventTime(Instant.now());
    }
}
