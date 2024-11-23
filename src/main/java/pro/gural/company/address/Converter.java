package pro.gural.company.address;

import pro.gural.common.domain.AddressCategoryType;
import pro.gural.company.domain.CompanyAddress;
import pro.gural.company.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class Converter {
    public static List<AddressEntity> toAddressEntityList(List<CompanyAddress> companyAddressList, String companyId) {
        if (companyAddressList == null) {
            return new ArrayList<>();
        }
        return companyAddressList.stream()
                .map(a -> toAddressEntity(a, companyId))
                .collect(Collectors.toList());
    }

    public static AddressEntity toAddressEntity(CompanyAddress companyAddress, String companyId) {
        if (companyAddress == null) {
            return null;
        }
        return new AddressEntity()
                .setId(UUID.randomUUID().toString())
                .setCompanyId(companyId)
                .setCountry(companyAddress.getCountry())
                .setCity(companyAddress.getCity())
                .setStreet(companyAddress.getStreet())
                .setZip(companyAddress.getZip())
                .setAddressCategory(Util.toJson(companyAddress.getAddressCategory()));
    }

    public static List<CompanyAddress> toCompanyAddressList(List<AddressEntity> entityList) {
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream()
                .map(Converter::toCompanyAddress)
                .collect(Collectors.toList());
    }

    public static CompanyAddress toCompanyAddress(AddressEntity entity) {
        if (entity == null) {
            return null;
        }
        List<String> categoryStrings = Util.fromStringArrayJson(entity.getAddressCategory());
        List<AddressCategoryType> categories = categoryStrings.stream()
                .map(s -> Util.stringToEnum(AddressCategoryType.class, s))
                .collect(Collectors.toList());
        return new CompanyAddress()
                .setId(entity.getId())
                .setCompanyId(entity.getCompanyId())
                .setCountry(entity.getCountry())
                .setCity(entity.getCity())
                .setStreet(entity.getStreet())
                .setZip(entity.getZip())
                .setAddressCategory(categories);
    }
}
