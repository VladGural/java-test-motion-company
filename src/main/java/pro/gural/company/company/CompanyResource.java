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
import pro.gural.company.domain.Company;

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

    CompanyResource(CompanyService service) {
        this.service = service;
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
}
