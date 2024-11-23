package pro.gural.company;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pro.gural.common.domain.AddressCategoryType;
import pro.gural.common.domain.CompanyKafkaMessage;
import pro.gural.common.domain.CompanyStatusType;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.company.company.CompanyClient;
import pro.gural.company.component_test.BaseComponentTestWebWithPostgres;
import pro.gural.company.consumer.KafkaTestConsumer;
import pro.gural.company.domain.Company;
import pro.gural.company.domain.CompanyAddress;

import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-07-29
 */
@DirtiesContext
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = {"component-tapplication-component-test.ymlest"})
@SpringBootTest(
    classes = {
            CompanyApplication.class
    },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(
    initializers = {
            BaseComponentTestWebWithPostgres.PostgresInitializer.class
    }
)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:3333", "port=3333"})
public class CompanyComponentIT extends BaseComponentTestWebWithPostgres {
    private static final Logger logger = LoggerFactory.getLogger(CompanyComponentIT.class);

    @BeforeAll
    void init() throws Exception {
    }

    @Test
    void test() throws Exception {

        // create Alis company
        CompanyAddress companyAddress01 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Kyiv")
                .setStreet("Vasilya Tutunnika")
                .setZip("03150")
                .setAddressCategory(List.of(AddressCategoryType.HEADQUARTER, AddressCategoryType.DISTRIBUTION_CENTER));

        CompanyAddress companyAddress02 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Velika Gora")
                .setZip("02011")
                .setAddressCategory(List.of(AddressCategoryType.HEADQUARTER));

        Company company = CompanyClient.createCompany(this, "Alis", CompanyStatusType.ACTIVE,
                "phone: +380503332211, mail: info@alis.pro", "food",
                List.of(companyAddress01, companyAddress02));

        // check sending Company event
        CompanyKafkaMessage companyKafkaMessage =
                KafkaTestConsumer.waitForSendCompanyEvent(company.getId(), KafkaActionType.CREATE);


        logger.info("Component test finished");
    }
}
