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
import pro.gural.common.domain.*;
import pro.gural.company.company.CompanyClient;
import pro.gural.company.component_test.BaseComponentTestWebWithPostgres;
import pro.gural.company.consumer.KafkaTestConsumer;

import java.util.List;
import java.util.Map;

import static pro.gural.common.domain.AddressCategoryType.*;
import static pro.gural.common.domain.CompanyStatusType.ACTIVE;
import static pro.gural.company.company.CompanyClient.checkCompanyData;
import static pro.gural.company.company.CompanyClient.getAddressByCity;
import static pro.gural.company.consumer.KafkaTestConsumer.cleanMap;

/**
 * @author Vladyslav Gural
 * @version 2024-07-29
 */
@DirtiesContext
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = {"component-test"})
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
        logger.info("Company component test is started");

        //
        // check not exist status
        CompanyClient.companyNotExist(this, "BAD_ID");


        //
        // create Alis company1

        // create 2 company1 addresses
        CompanyAddress companyAddress01 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Kyiv")
                .setStreet("Vasilya Tutunnika")
                .setZip("03150")
                .setAddressCategory(List.of(HEADQUARTER, DISTRIBUTION_CENTER));

        CompanyAddress companyAddress02 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Lviv")
                .setStreet("Velika Gora")
                .setZip("02011")
                .setAddressCategory(List.of(HEADQUARTER));

        Company company1 = CompanyClient.createCompany(this, "Alis", ACTIVE,
                "phone: +380503332211, mail: info@alis.pro", "food",
                List.of(companyAddress01, companyAddress02));

        // check company1 data
        checkCompanyData(company1, "Alis", ACTIVE, "phone: +380503332211, mail: info@alis.pro",
                "food", Map.of("Kyiv", List.of(HEADQUARTER, DISTRIBUTION_CENTER), "Lviv", List.of(HEADQUARTER)));

        // check sending Company event
        CompanyKafkaMessage companyKafkaMessage =
                KafkaTestConsumer.waitForSendCompanyEvent(company1.getId(), KafkaActionType.CREATE);

        // get company1 by id
        company1 = CompanyClient.getById(this, company1.getId());

        // check company1 data
        checkCompanyData(company1, "Alis", ACTIVE, "phone: +380503332211, mail: info@alis.pro",
                "food", Map.of("Kyiv", List.of(HEADQUARTER, DISTRIBUTION_CENTER), "Lviv", List.of(HEADQUARTER)));

        //
        // update Alis company1

        // create 2 new company1 address update company1 address in Kyiv and delete company1 Address in Lviv
        companyAddress01 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Odesa")
                .setStreet("Chernomorska")
                .setZip("04223")
                .setAddressCategory(List.of(WAREHOUSE));

        companyAddress02 = new CompanyAddress()
                .setCountry("Ukraine")
                .setCity("Dnipro")
                .setStreet("Dniprovska")
                .setZip("05332")
                .setAddressCategory(List.of(BRANCH_OFFICE, WAREHOUSE));

        CompanyAddress companyAddress03 = getAddressByCity(company1.getCompanyAddress(), "Kyiv");
        companyAddress03
                .setStreet("Petra Sagaydachnogo")
                .setZip("03133")
                .setAddressCategory(List.of(HEADQUARTER, DISTRIBUTION_CENTER, WAREHOUSE));

        company1 = CompanyClient.updateCompany(this, company1.getId(), "Alis-1", ACTIVE,
                "phone: +380503332211, +380508885533, mail: info@alis.pro", "food",
                List.of(companyAddress01, companyAddress02, companyAddress03));

        // check sending Company event
        cleanMap();
        companyKafkaMessage = KafkaTestConsumer.waitForSendCompanyEvent(company1.getId(), KafkaActionType.UPDATE);

        // check company1 data
        checkCompanyData(company1, "Alis-1", ACTIVE, "phone: +380503332211, +380508885533, mail: info@alis.pro",
                "food", Map.of("Odesa", List.of(WAREHOUSE), "Dnipro", List.of(BRANCH_OFFICE, WAREHOUSE),
                        "Kyiv", List.of(HEADQUARTER, DISTRIBUTION_CENTER, WAREHOUSE)));

        //
        // delete Alis company1

        // delete company
        CompanyClient.deleteCompany(this, company1.getId());

        // check sending Company event
        cleanMap();
        companyKafkaMessage = KafkaTestConsumer.waitForSendCompanyEvent(company1.getId(), KafkaActionType.DELETE);

        // check company not exist
        CompanyClient.companyNotExist(this, company1.getId());


        logger.info("Company component test is finished");
    }
}
