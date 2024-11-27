package pro.gural.company.company;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Transactional
interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findById(String companyId);

    void deleteById(String companyId);
}
