package co.edu.uptc.models;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import co.edu.uptc.pojos.VehicleMainData;
import co.edu.uptc.pojos.VehicleAllData;
import co.edu.uptc.utilities.ApiConsumer;
import co.edu.uptc.utilities.DoubleLinkedList;
import co.edu.uptc.utilities.PropertiesService;
import java.io.RandomAccessFile;
import lombok.Data;

@Data
public class VehicleManagerModel {
    private DoubleLinkedList<VehicleMainData> vehiclesData;
    private ApiConsumer apiConsumer;
    private PropertiesService propertiesService;
    private String filePath;

    public VehicleManagerModel() {
        vehiclesData = new DoubleLinkedList<VehicleMainData>();
        apiConsumer = new ApiConsumer();
        propertiesService = new PropertiesService();
        filePath = propertiesService.getKeyValue("RandomAccessData");   
    }

    public void loadData() {
        try {
            String jsonData = consumeApiToString();
            List<VehicleAllData> vehiclesAllData = parseJsonDataToVehicleRegistration(jsonData);
            List<VehicleMainData> vehiclesMainData = vehicleRegistrationToVehicleInfo(vehiclesAllData);
            for (VehicleMainData vehicleInfo : vehiclesMainData) {
                this.vehiclesData.add(vehicleInfo);
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String consumeApiToString() throws IOException, URISyntaxException {
        apiConsumer.consumeApi();
        Path dataPath = Paths.get(propertiesService.getKeyValue("DataPath"));
        return new String(Files.readAllBytes(dataPath));
    }

    private List<VehicleAllData> parseJsonDataToVehicleRegistration(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
        });
        @SuppressWarnings("unchecked")
        List<List<Object>> dataList = (List<List<Object>>) map.get("data");

        List<VehicleAllData> vehiclesAllData = new ArrayList<>();
        for (List<Object> dataObject : dataList) {
            VehicleAllData vehicle = mapRowToVehicleRegistration(dataObject);
            vehiclesAllData.add(vehicle);
        }
        return vehiclesAllData;
    }

    private List<VehicleMainData> vehicleRegistrationToVehicleInfo(List<VehicleAllData> vehiclesAllData) {
        List<VehicleMainData> vehiclesMainData = new ArrayList<>();
        for (VehicleAllData vehicle : vehiclesAllData) {
            VehicleMainData vehicleInfo = new VehicleMainData();
            vehicleInfo.setVinFirstTenCharacters(vehicle.getVinNumber());
            vehicleInfo.setRegistrationCounty(vehicle.getCounty());
            vehicleInfo.setRegistrationCity(vehicle.getCity());
            vehicleInfo.setRegistrationState(vehicle.getState());
            vehicleInfo.setRegistrationPostalCode(vehicle.getPostalCode());
            vehicleInfo.setVehicleModelYear(vehicle.getModelYear());
            vehicleInfo.setVehicleManufacturer(vehicle.getManufacturer());
            vehicleInfo.setVehicleModel(vehicle.getModel());
            vehicleInfo.setElectricVehicleType(vehicle.getElectricVehicleType());
            vehicleInfo.setCleanAlternativeFuelEligibility(vehicle.getCleanFuelVehicleEligibility());
            vehicleInfo.setElectricDrivingRange(vehicle.getElectricRange());
            vehicleInfo.setBaseManufacturerSuggestedRetailPrice(vehicle.getBaseMsrp());
            vehicleInfo.setLegislativeDistrictCode(vehicle.getLegislativeDistrict());
            vehicleInfo.setDepartmentOfLicensingVehicleId(vehicle.getDolVehicleId());
            vehicleInfo.setVehicleGeocodedLocation(vehicle.getGeocodedLocation());
            vehicleInfo.setElectricUtilityProvider(vehicle.getElectricUtility());
            vehicleInfo.setCensusTract2020(vehicle.getCensus2020Tract());
            vehiclesMainData.add(vehicleInfo);
        }
        return vehiclesMainData;
    }

    private VehicleAllData mapRowToVehicleRegistration(List<Object> row) {
        VehicleAllData auxVehicleAllData = new VehicleAllData();
        auxVehicleAllData.setIdentifierSID((String) row.get(0));
        auxVehicleAllData.setIdentifierID((String) row.get(1));
        auxVehicleAllData.setPosition((Integer) row.get(2));
        auxVehicleAllData.setCreatedAtEpoch(convertEpochToLong(row.get(3)));
        auxVehicleAllData.setCreatedMeta((String) row.get(4));
        auxVehicleAllData.setUpdatedAtEpoch(convertEpochToLong(row.get(5)));
        auxVehicleAllData.setUpdatedMeta((String) row.get(6));
        auxVehicleAllData.setMeta((String) row.get(7));
        auxVehicleAllData.setVinNumber((String) row.get(8));
        auxVehicleAllData.setCounty((String) row.get(9));
        auxVehicleAllData.setCity((String) row.get(10));
        auxVehicleAllData.setState((String) row.get(11));
        auxVehicleAllData.setPostalCode((String) row.get(12));
        auxVehicleAllData.setModelYear((String) row.get(13));
        auxVehicleAllData.setManufacturer((String) row.get(14));
        auxVehicleAllData.setModel((String) row.get(15));
        auxVehicleAllData.setElectricVehicleType((String) row.get(16));
        auxVehicleAllData.setCleanFuelVehicleEligibility((String) row.get(17));
        auxVehicleAllData.setElectricRange(parseInteger(row.get(18)));
        auxVehicleAllData.setBaseMsrp(parseInteger(row.get(19)));
        auxVehicleAllData.setLegislativeDistrict((String) row.get(20));
        auxVehicleAllData.setDolVehicleId((String) row.get(21));
        auxVehicleAllData.setGeocodedLocation((String) row.get(22));
        auxVehicleAllData.setElectricUtility((String) row.get(23));
        auxVehicleAllData.setCensus2020Tract((String) row.get(24));
        auxVehicleAllData.setRegionsCounted(parseInteger(row.get(25)));
        auxVehicleAllData.setCongressionalDistrict(parseInteger(row.get(26)));
        auxVehicleAllData.setLegislativeDistrictBoundaryGIS(parseInteger(row.get(27)));
        return auxVehicleAllData;
    }

    private Long convertEpochToLong(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).longValue();
        } else if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }

    private Integer parseInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.valueOf((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public void writeDataToRandomAccessFile(){
       try (RandomAccessFile raf = new RandomAccessFile(filePath, "rw")) {
            for (VehicleMainData vehicleMainData : vehiclesData) {
                vehicleMainData.writeToFile(raf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readRandomAccess(){
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                VehicleMainData vehicleMainData = new VehicleMainData();
                vehicleMainData.readFromFile(raf);
                System.out.println(vehicleMainData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
