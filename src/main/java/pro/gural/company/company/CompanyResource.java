package pro.gural.company.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pro.gural.common.domain.Company;
import pro.gural.common.domain.KafkaActionType;
import pro.gural.company.domain.KafkaServiceAware;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Tag(name = "company")
@RestController
@RequestMapping("/v1/companies")
class CompanyResource {
    private static final Logger logger = LoggerFactory.getLogger(CompanyResource.class);

    private final CompanyService service;
    private final KafkaServiceAware kafkaService;

    CompanyResource(CompanyService service,
                    KafkaServiceAware kafkaService) {
        this.service = service;
        this.kafkaService = kafkaService;
    }

    @Operation(summary = "Create new company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company successfully created"),
            @ApiResponse(responseCode = "400", description = "Company create request validation failed")
    })
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    Company createCompany(@RequestBody @Valid CompanyRequest request) {
        logger.info("User try to create company by request: {}", request);
        String companyId = service.createCompany(request);
        Company company = service.getById(companyId);
        kafkaService.sendCompanyEvent(company, KafkaActionType.CREATE);
        logger.info("Company was successfully created: {}", company);
        return company;
    }

    @Operation(summary = "Get company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @GetMapping("/{companyId}")
    Company getCompanyById(@PathVariable String companyId) {
        logger.info("User try to get company by id: {}", companyId);
        Company company = service.getById(companyId);
        logger.info("Company was successfully received: {}", company);
        return company;
    }

    @Operation(summary = "Update company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated company"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PutMapping("/{companyId}")
    Company updateCustomer(@PathVariable String companyId, @RequestBody @Valid CompanyRequest request) {
        logger.info("User try to update company by request: {}", request);
        String customerId = service.updateCompany(companyId, request);
        Company company = service.getById(customerId);
        kafkaService.sendCompanyEvent(company, KafkaActionType.UPDATE);
        logger.info("Company was successfully updated: {}", company);
        return company;
    }

    @Operation(summary = "Delete company by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Request to remove company was successful"),
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{companyId}")
    void deleteCompanyById(@PathVariable String companyId) {
        logger.info("User try to delete company by id: {}", companyId);
        Company deletedCompany = service.getById(companyId);
        service.deleteCompany(companyId);
        kafkaService.sendCompanyEvent(deletedCompany, KafkaActionType.DELETE);
        logger.info("Company was successfully deleted");
    }
}
