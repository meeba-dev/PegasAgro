package ru.pegasagro;

import java.io.*;
import java.util.ArrayList;


public class Main {


    public static void main(String[] args) {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        ArrayList buffer = new ArrayList();
        try {
            FileInputStream fistream = new FileInputStream("./src/main/resources/nmea.log");
            FileOutputStream fostream = new FileOutputStream("./src/main/resources/results.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fistream));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fostream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                System.out.println(strLine);
                String[] arr = strLine.split(",");
                if (arr[0].equals("$GPGGA")) {
                    if (arr[2].equals("") || arr[4].equals("")) {
                        continue;
                    }
                    buffer.add(convertTime(arr[1]));
                    buffer.add(convertDMStoDDlat(arr[2]));
                    buffer.add(convertDMStoDDlng(arr[4]));
                }
                else if (arr[0].equals("$GNVTG") || arr[0].equals("$BDVTG")) {
                    if (buffer.isEmpty()) {
                        coordinates.add(coordinates.get(coordinates.size() - 1));
                        coordinates.get(coordinates.size() - 1).setSpeed(Double.parseDouble(arr[7].toString()));
                    } else {
                        buffer.add(arr[7]);
                        coordinates.add(new Coordinates(buffer.get(0).toString(), Double.parseDouble(buffer.get(1).toString()), Double.parseDouble(buffer.get(2).toString()), Double.parseDouble(buffer.get(3).toString())));
                    }
                    System.out.println(buffer);
                    buffer.clear();
                }
            }
            br.close();
            fistream.close();
            for (int i = 0; i < coordinates.size() - 1; i++) {
                String time = coordinates.get(i).time;
                double speed = coordinates.get(i).speed;
                double lat1 = coordinates.get(i).latitude;
                double lng1 = coordinates.get(i).longitude;
                double lat2 = coordinates.get(i+1).latitude;
                double lng2 = coordinates.get(i+1).longitude;
                double destination = 0;
                if (speed != 0) {
                    destination = calculateDistance(lat1, lat2, lng1, lng2);
                }
                bw.write(time + " (" + lat1 + ", " + lng1 + ") & (" + lat2 + ", " + lng2 + "), V: " + speed + " km/h, S: " + destination + " m");
                bw.newLine();
            }
            bw.close();
            fostream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String convertTime(String time) {
        return time.substring(0, 2) + ":" + time.substring(2,4) + ":" + time.substring(4,8);
    }
    private static double convertDMStoDDlat(String dms) {
        int d = Integer.parseInt(dms.substring(0, 2));
        double m = Double.parseDouble(dms.substring(2, 4)) / 60;
        double s = Double.parseDouble(dms.substring(5, 7) + '.' + dms.substring(7)) / 3600;
        return d + m + s;
    }
    private static double convertDMStoDDlng(String dms) {
        int d = Integer.parseInt(dms.substring(0, 3));
        double m = Double.parseDouble(dms.substring(3, 5)) / 60;
        double s = Double.parseDouble(dms.substring(6, 8) + '.' + dms.substring(8)) / 3600;
        return d + m + s;
    }
    private static double calculateDistance(double lat1, double lat2, double lng1, double lng2) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lng1 = Math.toRadians(lng1);
        lng2 = Math.toRadians(lng2);
        double dlat = lat2 - lat1;
        double dlng = lng2 - lng1;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlng / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371000;
        return c * r;
    }
}