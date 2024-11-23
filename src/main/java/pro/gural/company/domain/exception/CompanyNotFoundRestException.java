package pro.gural.company.domain.exception;

import pro.gural.exception.template.NotFoundRestException;

/**
 * @author Vladyslav Gural
 * @version 2024-08-01
 */
public class CompanyNotFoundRestException extends NotFoundRestException {

    public CompanyNotFoundRestException(String message) {
        super(message);
    }

    public CompanyNotFoundRestException(String message, Object... parameters) {
        super(message, parameters);
    }

    @Override
    public String getCode() {
        return "company.not.found.error";
    }
}
