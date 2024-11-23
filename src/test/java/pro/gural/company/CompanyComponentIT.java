package pro.gural.company;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pro.gural.company.component_test.BaseComponentTestWebWithPostgres;

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
public class CompanyComponentIT extends BaseComponentTestWebWithPostgres {
    private static final Logger logger = LoggerFactory.getLogger(CompanyComponentIT.class);

    @BeforeAll
    void init() throws Exception {
    }

    @Test
    void test() throws Exception {
        logger.info("Component test");
    }
}
