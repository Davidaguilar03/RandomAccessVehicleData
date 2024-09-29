package co.edu.uptc;

import co.edu.uptc.models.VehicleManagerModel;

public class Main {

    public static void main(String[] args) {
        VehicleManagerModel vehicleManagerModel = new VehicleManagerModel();
        vehicleManagerModel.loadData();
        vehicleManagerModel.writeDataToRandomAccessFile();
        vehicleManagerModel.readRandomAccess();
    }
}
