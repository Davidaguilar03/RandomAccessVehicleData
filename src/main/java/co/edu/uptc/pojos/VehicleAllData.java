package co.edu.uptc.pojos;
import lombok.Data;

@Data
public class VehicleAllData {
    private String identifierSID;
    private String identifierID;
    private Integer position;
    private Long createdAtEpoch;
    private String createdMeta;
    private Long updatedAtEpoch;
    private String updatedMeta;
    private String meta;
    private String vinNumber;
    private String county;
    private String city;
    private String state;
    private String postalCode;
    private String modelYear;
    private String manufacturer;
    private String model;
    private String electricVehicleType;
    private String cleanFuelVehicleEligibility;
    private Integer electricRange;
    private Integer baseMsrp;
    private String legislativeDistrict;
    private String dolVehicleId;
    private String geocodedLocation;
    private String electricUtility;
    private String census2020Tract;
    private Integer regionsCounted;
    private Integer congressionalDistrict;
    private Integer legislativeDistrictBoundaryGIS;
}

