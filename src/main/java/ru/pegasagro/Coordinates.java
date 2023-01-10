package ru.pegasagro;

public class Coordinates {
    public final String time;
    public final double latitude;
    public final double longitude;

    public double speed;

    public Coordinates(String time, double latitude, double longitude, double speed) {
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public String getCoordPair() {
        return this.time + ", " + this.latitude + ", " + this.longitude;
    }
}
