package pro.gural.company.address;

import org.apache.kafka.common.protocol.types.Field;
import pro.gural.common.domain.AddressCategoryType;
import pro.gural.common.domain.CompanyAddress;
import pro.gural.company.domain.exception.CompanyNotFoundRestException;
import pro.gural.company.util.Util;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Vladyslav Gural
 * @version 2024-11-23
 */
class Converter {
    public static List<AddressEntity> toCreateAddressEntityList(List<CompanyAddress> companyAddressList, String companyId) {
        if (companyAddressList == null) {
            return new ArrayList<>();
        }
        return companyAddressList.stream()
                .map(a -> toCreateAddressEntity(a, companyId))
                .collect(Collectors.toList());
    }

    public static List<AddressEntity> toUpdateAddressEntityList(List<AddressEntity> addressEntityListOld,
                                                                List<CompanyAddress> companyAddressList,
                                                                String companyId) {
        if (companyAddressList == null) {
            return new ArrayList<>();
        }
        Map<String, AddressEntity> addressEntityListOldMap = addressEntityListOld.stream()
                .collect(Collectors.toMap(AddressEntity::getId, Function.identity()));
        return companyAddressList.stream()
                .map(ca -> {
                    if (ca.getId() == null) {
                        return toCreateAddressEntity(ca, companyId);
                    } else {
                        return toUpdateAddressEntity(addressEntityListOldMap.get(ca.getId()), ca);
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static AddressEntity toCreateAddressEntity(CompanyAddress companyAddress, String companyId) {
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

    public static AddressEntity toUpdateAddressEntity(AddressEntity addressEntity, CompanyAddress companyAddress) {
        if (companyAddress == null) {
            return null;
        }
        return addressEntity
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

    public static List<AddressEntity> getOldAddress(List<AddressEntity> addressEntityListOld, List<CompanyAddress> companyAddressList) {
        List<String> companyAddressIds = getCompanyAddressIds(companyAddressList);
        return addressEntityListOld.stream()
                .filter(ae -> !companyAddressIds.contains(ae.getId()))
                .toList();
    }

    public static List<String> getCompanyAddressIds(List<CompanyAddress> companyAddressList) {
        if (companyAddressList == null) {
            return new ArrayList<>();
        }
        return companyAddressList.stream()
                .map(CompanyAddress::getId)
                .collect(Collectors.toList());
    }

    public static List<String> getAddressEntityIds(List<AddressEntity> addressEntityList) {
        if (addressEntityList == null) {
            return new ArrayList<>();
        }
        return addressEntityList.stream()
                .map(AddressEntity::getId)
                .collect(Collectors.toList());
    }
}
