package sample;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import java.text.DecimalFormatSymbols;

public class Controller extends Application {
    public ComboBox comboBox1;
    public Label cityLabel;
    public ComboBox comboBox2;
    public Button okBtn;
    public Label weatherLabel;
    public Label populationLabel;
    public Label popValueLbl;
    public Label cityLbl;
    public Label countryLbl;
    public Label tempLbl;
    public Label humidityLbl;
    public Label visiblLbl;
    public Label riseLbl;
    public Label downLbl;
    public CheckBox chkBox1;
    public CheckBox chkBox2;
    public Label coordLbl;
    public Button coordBtn;
    public Button infoBtn;
    public Pane mainPane;
    public GridPane mainGridPane;
    public Button findBtn;
    public TextField srchTxt;
    //public Hyperlink link;
    private List<City> cities; // create a list Cities

    List<String> countries;  // create a list of Countries


    public Controller() throws SQLException, ClassNotFoundException {
        Database database = new Database();
        countries = database.getCountries();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public String getComboBox1Value() {
        return (String) comboBox1.getValue();
    }

    public String getComboBox2Value() {
        return (String) comboBox2.getValue();
    }

    public void selectCountries(Event event) throws SQLException, ClassNotFoundException {
        //Database data = new Database();
        comboBox1.getItems().setAll(countries);
    }

    public void selectCities(Event event) throws SQLException, ClassNotFoundException {
        String country = null;
        country = getComboBox1Value();  // get the value from the first comboBox
        if (country != null) {  //if the value from the first comboBox not null
            Database db = new Database(); // create a new object db with Database type
            cities = db.getCities(country); // write all info about the selected country into the list of Cities
            comboBox2.getItems().clear(); // clear the previous items from second comboBox
            for (City s : cities) { // go though all cities
                //System.out.println(s.getName());
                comboBox2.getItems().add(s.getName()); // fill the second comboBox with the names of the cities in the selected country
            }
            comboBox2.setDisable(false); // enable the second comboBox
            chkBox1.setDisable(false);
            chkBox2.setDisable(false);
        }
        /*
        Database database = new Database();
        comboBox2.getItems().setAll(database.getCities(getComboBox1Value()));
         */
    }

    public void showNextSelect(ActionEvent actionEvent) {
        if (getComboBox1Value() != null) {
            comboBox2.setDisable(false);
        }
    }

    /* Not in use for a while
    public double getSceneWeight(){
       return mainPane.getWidth();
    }

    public double getSceneHeight(){
       return mainPane.getHeight();
    }
     */

    public void showAllLabels(ActionEvent actionEvent) {
        okBtn.setDisable(false);
        //mainPane.setPrefWidth(600);
        //mainPane.setPrefHeight(460);
    }

    public void getAllInfo(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String cityName = getComboBox2Value();
        City city = null;  // create an object with type City
        for (City c : cities) { //run through cities list
            if (c.getName().equals(cityName)) { //if found city.Name in list cities which is equal to the cityName that we selected in first comboBox
                city = c; //set to null object city all the data (city.Name, country.Name, population and etc.) from the found city in the list. Now we're able to work with it.
                break; // if OK - stop further iterations
            }
        }

        if (city == null)  // condition to avoid a NullPointerException
            return;

        setLbl();
        cityLbl.setText("City:         " + city.getName());
        countryLbl.setText("Country:      " + city.getCountry() + " (" + city.getCode2() + ")");
        populationLabel.setText("Population:    " + formatPopulation(city.getPopulation()));
        Weather weather = new APIWeather().getWeather(city.getName(), city.getCode3());
        if (weather != null) {
            coordBtn.setDisable(false);
            String temp = String.valueOf(weather.getTemp());
            //String temp = Double.toString(weather.getTemp());
            String hum = String.valueOf(weather.getHumidity());
            String vis = String.valueOf(weather.getVisibility());
            tempLbl.setText("The temperature: " + temp + "°C");
            humidityLbl.setText("Humidity: " + hum + "%");
            visiblLbl.setText("Visibility: " + vis + "km");
            riseLbl.setText("Sunrise: " + weather.getSunRise());
            downLbl.setText("Sunset: " + weather.getSunSet());
            coordLbl.setText("http://www.google.com/maps/place/" + weather.getLat() + "," + weather.getLon());
        } else {
            coordBtn.setDisable(true);
            tempLbl.setText("---");
            humidityLbl.setText("---");
            visiblLbl.setText("---");
            riseLbl.setText("---");
            downLbl.setText("---");
        }

    }

    private String formatPopulation(int population) { // format the population result
        DecimalFormat df = new DecimalFormat("#,###,###");
        DecimalFormatSymbols symbols = df.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);
        return df.format(population);
    }

    public void switchToFirstCity(KeyEvent keyEvent) { // the method to switch old cities into the new one if the user will choose another country
        /*
        Database data = new Database();
        //comboBox2.set
        comboBox2.getSelectionModel().selectFirst(data.getCities(getComboBox1Value()).);
        //comboBox2.getItems().setAll(data.getCities(getComboBox1Value()));
         */
    }

    public void setLbl() {
        if (chkBox1.isSelected()) {
            weatherLabel.setVisible(true);
            tempLbl.setVisible(true);
            humidityLbl.setVisible(true);
            visiblLbl.setVisible(true);
            riseLbl.setVisible(true);
            downLbl.setVisible(true);
        } else {
            weatherLabel.setVisible(false);
            tempLbl.setVisible(false);
            humidityLbl.setVisible(false);
            visiblLbl.setVisible(false);
            riseLbl.setVisible(false);
            downLbl.setVisible(false);
        }
        if (chkBox2.isSelected()) {
            populationLabel.setVisible(true);
            popValueLbl.setVisible(true);
            cityLbl.setVisible(true);
            countryLbl.setVisible(true);
        } else {
            populationLabel.setVisible(false);
            popValueLbl.setVisible(false);
            cityLbl.setVisible(false);
            countryLbl.setVisible(false);
        }
    }

    public void goToCoord() {  // open the web page with the coordinates
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(this);
        getHostServices().showDocument(coordLbl.getText());  // the coordinates stores in coordLbl value. Can be changed by String.
    }

    public void getInfo(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("City Information");
        alert.setHeaderText("                              Version: 0.8p2");
        alert.setContentText("Choose the country and city and find out all info about it! Use button 'WEB' to open selected city on google maps.");
        //alert.setContentText("Version: 0.7e3");

        alert.showAndWait();
    }

    public void getInfoFromDB(ActionEvent actionEvent) {
        String cityName = srchTxt.getText();
        Database db = new Database(); // create a new object db with Database type
        cities = db.getCityByName(cityName);
        City city = null;
        for (City s : cities) {
            if (s.getName().equals(cityName)) {
                city = s;
                break;
            }
        }

        if (city != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result of the search");
            alert.setHeaderText(null);
            alert.setContentText("| "+city.getName() + " | " + city.getCountry() + " | Population:  " + city.getPopulation() + " | " + city.getCode2()+ " |");

            alert.showAndWait();
        } else {
            cities = db.getCountryByName(cityName);
            for (City s : cities){
                if(s.getCountry().equals(cityName)){
                    city = s;
                    break;
                }
            }

            if (city != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Result of the search");
                alert.setHeaderText(null);
                alert.setContentText("| "+city.getName() + " | " + city.getCountry() + " | Population:  " + city.getPopulation() + " | " + city.getCode2()+ " |");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Result of the search");
                alert.setHeaderText(null);
                alert.setContentText("The search couldn't find anything!");

                alert.showAndWait();
            }
        }
    }

    public void isTxtEmp(ActionEvent actionEvent) {
        if (srchTxt.getText() != null) {
            findBtn.setDisable(false);
        }
    }
}
