package pro.gural.company.address;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
@Transactional
interface AddressRepository extends JpaRepository<AddressEntity, String> {
    AddressEntity getById(String companyId);

    List<AddressEntity> getByCompanyId(String companyId);

    int deleteByCompanyId(String companyId);
}
