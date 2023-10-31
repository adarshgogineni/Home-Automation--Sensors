// written by: Shaan Parikh, Shivum Mehta
// tested by: Shaan Parikh, Shivum Mehta
// debugged by: Shaan Parikh, Shivum Mehta

package com.example.shaan.hazardapp;

public class User {
    private String frontDoor;
    private String cmonoxide;
    private String airQ;
    private String flame;
    private String window;





public User(){

}

    public User(String frontDoor, String cmonoxide, String airQ, String flame, String window) {
        this.frontDoor = frontDoor;
        this.cmonoxide = cmonoxide;
        this.airQ = airQ;
        this.flame = flame;
        this.window = window;
    }

    public String getFrontDoor() {
        return frontDoor;
    }

    public void setFrontDoor(String frontDoor) {
        this.frontDoor = frontDoor;
    }

    public String getCmonoxide() {
        return cmonoxide;
    }

    public void setCmonoxide(String cmonoxide) {
        this.cmonoxide = cmonoxide;
    }

    public String getAirQ() {
        return airQ;
    }

    public void setAirQ(String airQ) {
        this.airQ = airQ;
    }

    public String getFlame() {
        return flame;
    }

    public void setFlame(String flame) {
        this.flame = flame;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }
}
