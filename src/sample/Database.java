package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String SELECT_COUNTRY = "SELECT country.Code, country.Name FROM country";
    private final String SELECT_CITYBYNAME = "SELECT city.Name, city.CountryCode, country.Code2, json_extract(Info, '$.Population') AS Info, country.Name FROM city JOIN country ON country.Code = city.CountryCode WHERE city.Name LIKE ?";
    //private final String SELECT_COUNTRYBYNAME = "SELECT city.Name, city.CountryCode, country.Code2, json_extract(Info, '$.Population') AS Info, country.Name FROM city JOIN country ON country.Code = city.CountryCode WHERE country.Name LIKE ?";
    private final String SELECT_CITY = "SELECT city.Name, city.CountryCode, country.Code2, json_extract(Info, '$.Population') AS Info, country.Name FROM city JOIN country ON country.Code = city.CountryCode WHERE country.Name LIKE ?";
    private final String SELECT_POPULATION = "SELECT json_extract(Info, '$.Population') AS Population, CountryCode " +
            "FROM city WHERE city.Name LIKE ?";
    //private final String  SELECT_CITY

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://itsovy.sk:3306/world_x?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "student", "kosice2019");
    }

    public List getCountries() {
        try {
            getConnection();
            PreparedStatement ps = getConnection().prepareStatement(SELECT_COUNTRY);
            ResultSet rs = ps.executeQuery();
            List<String> list = new ArrayList<>();
            while (rs.next()) {
                String countryName = rs.getString("Name");
                //System.out.println(countryName);
                list.add(rs.getString("Name"));
                //countryList.add(rs.getString("Name"));
            }
            ps.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
        public void setCityForWeather(){
            try {
                PreparedStatement ps = getConnection().prepareStatement("")
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    */
    public List getCities(String cityName) {
        try {
            System.out.println(cityName);
            PreparedStatement ps = getConnection().prepareStatement(SELECT_CITY);
            ps.setString(1, cityName);
            List<City> list = new ArrayList<>();
            //List<String> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String code2 = rs.getString("city.CountryCode");
                String code3 = rs.getString("country.Code2");
                String country = rs.getString("country.Name");
                int population = rs.getInt("Info");
                City newCity = new City(name, population, code3, code2, country);
                list.add(newCity);
                //list.add(rs.getString("Name"));
            }
            ps.close();
            rs.close();
            return list;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List getCityByName(String cityName) {
        try {
            System.out.println(cityName);
            PreparedStatement ps = getConnection().prepareStatement(SELECT_CITYBYNAME);
            ps.setString(1,  cityName);
            List<City> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String code2 = rs.getString("city.CountryCode");
                String code3 = rs.getString("country.Code2");
                String country = rs.getString("country.Name");
                int population = rs.getInt("Info");
                City newCity = new City(name, population, code3, code2, country);
                list.add(newCity);
                //list.add(rs.getString("Name"));
            }
            ps.close();
            rs.close();
            return list;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public List getCountryByName(String cityName) {
        try {
            System.out.println(cityName);
            PreparedStatement ps = getConnection().prepareStatement(SELECT_COUNTRYBYNAME);
            ps.setString(1,  cityName);
            List<City> list = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Name");
                String code2 = rs.getString("city.CountryCode");
                String code3 = rs.getString("country.Code2");
                String country = rs.getString("country.Name");
                int population = rs.getInt("Info");
                City newCity = new City(name, population, code3, code2, country);
                list.add(newCity);
                //list.add(rs.getString("Name"));
            }
            ps.close();
            rs.close();
            return list;
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    public String getPopulation(String cityName){
        try {
            PreparedStatement ps = getConnection().prepareStatement(SELECT_POPULATION);
            ps.setString(1, cityName);
            ps.executeQuery();
            String population = null;
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                population = rs.getString("Population");
                String countryCode = rs.getString("CountryCode");
                System.out.println(countryCode);
            }
            ps.close();
            return population;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

/*
    //@FXML
    private void getStation() {
        .setItems(countryList);
    }
*/

}