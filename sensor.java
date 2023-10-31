
// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta

package com.example.shaan.hazardapp;

import android.widget.TextView;

public class sensor {

    private String sensorData;
    private String SensorName;

    public sensor(){

    }
    public sensor(String sensorData, String sensorName) {
        this.sensorData = sensorData;
        SensorName = sensorName;
    }

    public String getSensorData() {
        return sensorData;
    }

    public String getSensorName() {
        return SensorName;
    }
}
