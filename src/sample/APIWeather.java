package sample;

import com.mysql.cj.xdevapi.JsonString;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class APIWeather {

    public Weather getWeather(String city, String code2) {
        Weather weather = null;
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + code2 + "&units=metric&appid=26f312e1a14ba8902ba63873b2ff0cb2");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == 200) { // 200 - means successfully return right info
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();

                System.out.println(response.toString());

                JSONObject json = new JSONObject(response.toString());
                double temp = json.getJSONObject("main").getDouble("temp");
                int humidity = json.getJSONObject("main").getInt("humidity");
                String cityName = json.getString("name");
                String countryCode = json.getJSONObject("sys").getString("country");
                try {
                    double visibility = json.getDouble("visibility") / 1000;

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    long unix = json.getJSONObject("sys").getLong("sunset");
                    long unix_1 = json.getJSONObject("sys").getLong("sunrise");

                    // Calculate the date from the json request. Warning! The time returns in microseconds!
                    Date now = new Date(unix * 1000);
                    Date now_x = new Date(unix_1 * 1000);
                    String riseTime = sdf.format(now_x);
                    String setTime = sdf.format(now);
                    System.out.println(riseTime);
                    System.out.println(setTime);

                    System.out.println("The main temp is: " + temp);
                    System.out.println("The main humidity is: " + humidity);
                    System.out.println("The visibility is: " + visibility);

                    double lon = json.getJSONObject("coord").getDouble("lon");
                    double lat = json.getJSONObject("coord").getDouble("lat");

                    //return new Weather(cityName, countryCode, temp, humidity, visibility, 0, riseTime, setTime);
                    return new Weather(cityName, countryCode, temp, humidity, visibility, lon, lat, riseTime, setTime);
                } catch (Exception e) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    long unix = json.getJSONObject("sys").getLong("sunset");
                    long unix_1 = json.getJSONObject("sys").getLong("sunrise");

                    // Calculate the date from the json request. Warning! The time returns in microseconds!
                    Date now = new Date(unix * 1000);
                    Date now_x = new Date(unix_1 * 1000);
                    String riseTime = sdf.format(now_x);
                    String setTime = sdf.format(now);
                    System.out.println(riseTime);
                    System.out.println(setTime);

                    System.out.println("The main temp is: " + temp);
                    System.out.println("The main humidity is: " + humidity);

                    double lon = json.getJSONObject("coord").getDouble("lon");
                    double lat = json.getJSONObject("coord").getDouble("lat");

                    return new Weather(cityName, countryCode, temp, humidity, 0, lon, lat, riseTime, setTime);
                }
            } else throw new NoSuchException("City " + city + " was not found in the database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
