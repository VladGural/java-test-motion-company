package pro.gural.company.consumer;

import org.awaitility.Awaitility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.common.domain.KafkaTopics;
import pro.gural.company.util.Util;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Service
public class KafkaTestConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaTestConsumer.class);

    public static Map<MapKey, CompanyKafkaMessage> companyEventMessageMap = new HashMap<>();

    @KafkaListener(topics = KafkaTopics.COMPANY_TOPIC)
    public static void receiveEmail(@Payload String message) {
        CompanyKafkaMessage companyKafkaMessage = Util.fromJson(message, CompanyKafkaMessage.class);
        logger.info("Receive kafka message: {}", companyKafkaMessage);
        companyEventMessageMap.put(new MapKey(companyKafkaMessage.getCompany().getId(), companyKafkaMessage.getAction()),
                companyKafkaMessage);
    }

    public static CompanyKafkaMessage waitForSendCompanyEvent(String companyId, KafkaActionType action) {
        Awaitility.await().atMost(10, SECONDS).pollInterval(500, MILLISECONDS)
                .until(() -> companyEventMessageMap.get(new MapKey(companyId, action)) != null);
        return companyEventMessageMap.get(new MapKey(companyId, action));
    }

    public static void cleanMap() {
        companyEventMessageMap.clear();
    }


    record MapKey(String companyId, KafkaActionType action){}
}
