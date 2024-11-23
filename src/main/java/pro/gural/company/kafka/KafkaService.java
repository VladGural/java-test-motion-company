package pro.gural.company.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.company.domain.Company;
import pro.gural.company.domain.KafkaServiceAware;
import pro.gural.company.util.Util;

import java.lang.reflect.UndeclaredThrowableException;

import static pro.gural.company.kafka.Converter.toCompanyKafkaMessage;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Service
class KafkaService implements KafkaServiceAware {
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    private static final String COMPANY_TOPIC = "company.event.v1";

    private final KafkaTemplate<String, String> kafkaTemplate;

    KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendCompanyEvent(Company company, KafkaActionType action) {
        CompanyKafkaMessage companyKafkaMessage = toCompanyKafkaMessage(company, action);
        kafkaTemplate.send(COMPANY_TOPIC, Util.toJson(companyKafkaMessage));
    }
}
