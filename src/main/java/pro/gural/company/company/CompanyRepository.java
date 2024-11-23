package pro.gural.company.company;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    CompanyEntity getById(String companyId);
}
