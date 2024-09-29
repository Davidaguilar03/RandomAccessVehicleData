package co.edu.uptc.pojos;

import java.io.IOException;
import java.io.RandomAccessFile;
import lombok.Data;

@Data
public class VehicleMainData {
    private String vinFirstTenCharacters;
    private String registrationCounty;
    private String registrationCity;
    private String registrationState;
    private String registrationPostalCode;
    private String vehicleModelYear;
    private String vehicleManufacturer;
    private String vehicleModel;
    private String electricVehicleType;
    private String cleanAlternativeFuelEligibility;
    private Integer electricDrivingRange;
    private Integer baseManufacturerSuggestedRetailPrice;
    private String legislativeDistrictCode;
    private String departmentOfLicensingVehicleId;
    private String vehicleGeocodedLocation;
    private String electricUtilityProvider;
    private String censusTract2020;

    private static final int SIZE_VIN = 10;
    private static final int SIZE_COUNTY = 20;
    private static final int SIZE_CITY = 20;
    private static final int SIZE_STATE = 5;
    private static final int SIZE_POSTAL_CODE = 10;
    private static final int SIZE_MODEL_YEAR = 4;
    private static final int SIZE_MANUFACTURER = 20;
    private static final int SIZE_MODEL = 20;
    private static final int SIZE_ELECTRIC_VEHICLE_TYPE = 20;
    private static final int SIZE_FUEL_ELIGIBILITY = 10;
    private static final int SIZE_LEGISLATIVE_DISTRICT = 10;
    private static final int SIZE_DOL_VEHICLE_ID = 15;
    private static final int SIZE_GEOCODED_LOCATION = 30;
    private static final int SIZE_ELECTRIC_UTILITY = 30;
    private static final int SIZE_CENSUS_TRACT = 15;

    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.write(fixedLengthString(vinFirstTenCharacters, SIZE_VIN));
        raf.write(fixedLengthString(registrationCounty, SIZE_COUNTY));
        raf.write(fixedLengthString(registrationCity, SIZE_CITY));
        raf.write(fixedLengthString(registrationState, SIZE_STATE));
        raf.write(fixedLengthString(registrationPostalCode, SIZE_POSTAL_CODE));
        raf.write(fixedLengthString(vehicleModelYear, SIZE_MODEL_YEAR));
        raf.write(fixedLengthString(vehicleManufacturer, SIZE_MANUFACTURER));
        raf.write(fixedLengthString(vehicleModel, SIZE_MODEL));
        raf.write(fixedLengthString(electricVehicleType, SIZE_ELECTRIC_VEHICLE_TYPE));
        raf.write(fixedLengthString(cleanAlternativeFuelEligibility, SIZE_FUEL_ELIGIBILITY));
        raf.writeInt(electricDrivingRange != null ? electricDrivingRange : 0);
        raf.writeInt(baseManufacturerSuggestedRetailPrice != null ? baseManufacturerSuggestedRetailPrice : 0);
        raf.write(fixedLengthString(legislativeDistrictCode, SIZE_LEGISLATIVE_DISTRICT));
        raf.write(fixedLengthString(departmentOfLicensingVehicleId, SIZE_DOL_VEHICLE_ID));
        raf.write(fixedLengthString(vehicleGeocodedLocation, SIZE_GEOCODED_LOCATION));
        raf.write(fixedLengthString(electricUtilityProvider, SIZE_ELECTRIC_UTILITY));
        raf.write(fixedLengthString(censusTract2020, SIZE_CENSUS_TRACT));
    }

    public void readFromFile(RandomAccessFile raf) throws IOException {
        vinFirstTenCharacters = readFixedLengthString(raf, SIZE_VIN);
        registrationCounty = readFixedLengthString(raf, SIZE_COUNTY);
        registrationCity = readFixedLengthString(raf, SIZE_CITY);
        registrationState = readFixedLengthString(raf, SIZE_STATE);
        registrationPostalCode = readFixedLengthString(raf, SIZE_POSTAL_CODE);
        vehicleModelYear = readFixedLengthString(raf, SIZE_MODEL_YEAR);
        vehicleManufacturer = readFixedLengthString(raf, SIZE_MANUFACTURER);
        vehicleModel = readFixedLengthString(raf, SIZE_MODEL);
        electricVehicleType = readFixedLengthString(raf, SIZE_ELECTRIC_VEHICLE_TYPE);
        cleanAlternativeFuelEligibility = readFixedLengthString(raf, SIZE_FUEL_ELIGIBILITY);
        electricDrivingRange = raf.readInt();
        baseManufacturerSuggestedRetailPrice = raf.readInt();
        legislativeDistrictCode = readFixedLengthString(raf, SIZE_LEGISLATIVE_DISTRICT);
        departmentOfLicensingVehicleId = readFixedLengthString(raf, SIZE_DOL_VEHICLE_ID);
        vehicleGeocodedLocation = readFixedLengthString(raf, SIZE_GEOCODED_LOCATION);
        electricUtilityProvider = readFixedLengthString(raf, SIZE_ELECTRIC_UTILITY);
        censusTract2020 = readFixedLengthString(raf, SIZE_CENSUS_TRACT);
    }

    private byte[] fixedLengthString(String text, int length) {
        byte[] bytes = new byte[length];
        byte[] textBytes = text != null ? text.getBytes() : new byte[0];
        System.arraycopy(textBytes, 0, bytes, 0, Math.min(textBytes.length, length));
        return bytes;
    }

    private String readFixedLengthString(RandomAccessFile raf, int length) throws IOException {
        byte[] bytes = new byte[length];
        raf.read(bytes);
        return new String(bytes).trim();
    }
}


