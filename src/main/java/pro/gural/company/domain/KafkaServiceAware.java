package pro.gural.company.domain;

import pro.gural.common.domain.KafkaActionType;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
public interface KafkaServiceAware {

    void sendCompanyEvent(Company company, KafkaActionType action);
}
