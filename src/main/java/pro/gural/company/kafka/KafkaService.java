package pro.gural.company.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.common.domain.KafkaTopics;
import pro.gural.company.domain.KafkaServiceAware;
import pro.gural.company.util.Util;

import java.util.concurrent.CompletableFuture;

import static pro.gural.company.kafka.Converter.toCompanyKafkaMessage;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Service
class KafkaService implements KafkaServiceAware {
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    KafkaService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendCompanyEvent(Company company, KafkaActionType action) {
        CompanyKafkaMessage companyKafkaMessage = toCompanyKafkaMessage(company, action);
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(KafkaTopics.COMPANY_TOPIC, company.getId(), Util.toJson(companyKafkaMessage));
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Kafka company message: {} was sent",companyKafkaMessage);
            } else {
                logger.info("Unable to send kafka company message {}", companyKafkaMessage);
            }
        });
    }
}
