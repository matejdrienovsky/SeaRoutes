package com.example.searoutes;

import Boats.Boat;
import Captains.Captain;
import Database.DatabaseFacade;
import DesignPattern.Factory;
import GenericFilter.GenericFilter;
import Orders.*;
import OwnException.OwnException;
import User.User;
import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Class for the travel page where the user can choose the departure and arrival harbour, the boat and the captain and other options
 */
public class Travel {
    private User user;
    @FXML javafx.scene.control.ChoiceBox departure_choice;
    @FXML javafx.scene.control.ChoiceBox arrival_choice;
    @FXML javafx.scene.control.ChoiceBox boats_choicebox;
    @FXML javafx.scene.control.ChoiceBox captain_choicebox;
    @FXML javafx.scene.control.DatePicker dateChoice;
    @FXML javafx.scene.control.CheckBox studentCheck;
    @FXML javafx.scene.control.CheckBox seniorCheck;
    @FXML javafx.scene.control.CheckBox weather;
    @FXML javafx.scene.control.Label labelWarning;
    @FXML javafx.scene.image.ImageView warningGif;
    @FXML javafx.scene.control.TextField ageText;
    static boolean check = false;
    private Factory factory;
    private static int count = 0;

    /**
     * Method that set the user from the previous page
     * @param user
     */
    public void userset(User user){
        this.user = user;
    }


    /**
     * Method that give the departure harbours to the choiceboxes from the database
     */
    @FXML
    protected void add_departure(){
        departure_choice.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Harbours", "harbour");
        FindIterable<Document> documents = database8.find();
        // Convert iterable to list using Java Streams
        List<Document> docsList = StreamSupport.stream(documents.spliterator(), false)
                .collect(Collectors.toList());
        // Sort using Java Streams
        List<Document> sortedList = docsList.stream()
                .sorted(Comparator.comparing(d -> d.get("_id").toString()))
                .collect(Collectors.toList());
        // Add sorted id to users choicebox
        for (Document doc : sortedList) {
            departure_choice.getItems().add(doc.get("_id").toString());
            arrival_choice.getItems().add(doc.get("_id").toString());
        }
   }

    /**
     * Method that add arrival harbours to the choicebox from the database
     */
    @FXML
    protected void add_arrival(){
        arrival_choice.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Harbours", "harbour");
        FindIterable<Document> documents = database8.find();
        // Convert iterable to list using Java Streams
        List<Document> docsList = StreamSupport.stream(documents.spliterator(), false)
                .collect(Collectors.toList());
        // Sort using Java Streams
        List<Document> sortedList = docsList.stream()
                .sorted(Comparator.comparing(d -> d.get("_id").toString()))
                .collect(Collectors.toList());
        // Add sorted id to users choicebox
        for (Document doc : sortedList) {
            arrival_choice.getItems().add(doc.get("_id").toString());
        }
    }


    /**
     * Method that create an order object and sends it to the next page
     * @param event
     * @return
     */
   @FXML
   protected void onOrder(javafx.event.ActionEvent event) throws IOException, ClassNotFoundException {
       if (departure_choice.getValue() == null || arrival_choice.getValue() == null || boats_choicebox.getValue() == null || captain_choicebox.getValue() == null || dateChoice.getValue() == null || ageText.getText().isEmpty()) {
           labelWarning.setText("Please fill all the fields");
           warningGif.setVisible(true);
       }
       else {
           if (check==false){
               labelWarning.setText("Check the age with button");
               warningGif.setVisible(true);
               return;
           }
           warningGif.setVisible(false);
           FXMLLoader loader = new FXMLLoader(getClass().getResource("order/order.fxml"));
           Parent root = loader.load();
           OrderController orderController = loader.getController();
           Boat boat = createBoat();
           String tempCaptain = captain_choicebox.getValue().toString();
           Order order = add_to_order(boat);
           if (order == null){
               return;
           }

           else {
               order.getBoat().getInformation();
               try {
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               orderController.setOrder(order);
               Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
               stage.setScene(new Scene(root));
               stage.show();
           }
       }
   }

    /**
     * Method that gives the boats to the choicebox from the database
     * Sorted by name
     */
   @FXML
   protected void add_boats(){
        boats_choicebox.getItems().clear();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        FindIterable<Document> documents = database8.find();

       // Convert iterable to list
       List<Document> docsList = new ArrayList<>();
       for (Document doc : documents) {
           docsList.add(doc);
       }

       Comparator<Document> idComparator = (d1, d2) -> {
           String id1 = d1.get("_id").toString();
           String id2 = d2.get("_id").toString();
           String[] words1 = id1.split("\\s+");
           String[] words2 = id2.split("\\s+");
           int cmp = words1[0].compareToIgnoreCase(words2[0]);
           if (cmp == 0 && words1.length > 1 && words2.length > 1) {
               cmp = words1[1].compareToIgnoreCase(words2[1]);
           }
           return cmp;
       };

       // Filter using predicate
       Predicate<Document> alphabeticalIdPredicate = (doc) -> {
           String[] words = doc.get("_id").toString().split("\\s+");
           if (words.length > 0) {
               String firstWord = words[0];
               return firstWord.matches("\\p{L}+");
           }
           return false;
       };
       GenericFilter<Document> alphabeticalIdFilter = new GenericFilter<>(alphabeticalIdPredicate);
       List<Document> alphabeticalDocsList = alphabeticalIdFilter.filter(docsList);

       // Sort using comparator
       Collections.sort(alphabeticalDocsList, idComparator);

       boats_choicebox.getItems().clear();
       for (Document doc : alphabeticalDocsList) {
           boats_choicebox.getItems().add(doc.get("_id").toString());
       }
   }

    /**
     * Method that gives the captains to the choicebox from the database
     */
   @FXML
   protected void add_captain() {
         captain_choicebox.getItems().clear();
         List<Captain> captains = new ArrayList<>();

            // Try to read the list from the file
            try {
                FileInputStream fileIn = new FileInputStream("captains.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                captains = (List<Captain>) in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            DatabaseFacade database8 = new DatabaseFacade();
            database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Captains", "captain");
            FindIterable<Document> documents = database8.find();
            // Convert iterable to list using Java Streams
            List<Document> docsList = StreamSupport.stream(documents.spliterator(), false)
                    .collect(Collectors.toList());

            // Sort using Java Streams
            List<Document> sortedList = docsList.stream()
                    .sorted(Comparator.comparing(d -> d.get("_id").toString()))
                    .collect(Collectors.toList());

            for (Document doc : sortedList) {
                boolean captainFound = false;
                for (Captain captain : captains) {
                    if (captain.getName().equals(doc.get("_id"))) {
                        // Example: add captain name to choicebox
                        captain_choicebox.getItems().add(captain.getName()+" "+ captain.getRating()+"⭐");
                        captainFound = true;
                        break;
                    }
                }
                if (!captainFound) {
                    captain_choicebox.getItems().add(doc.get("_id").toString()+" ⭐");
                }
            }
        }

    /**
     * Method that creates an order object
     * @param boat1
     * @return order
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Order add_to_order(Boat boat1) throws IOException, ClassNotFoundException {
        if (count ==0) {
            actualUser(user);
            count++;
        }
        User user8 = deserialize();

        Double normalPrice = 10.0;
        Rout rout = createRout();
        String captainWithRating = (String) captain_choicebox.getValue();
        int lastSpaceIndex = captainWithRating.lastIndexOf(' ');
        String captainName = captainWithRating.substring(0, lastSpaceIndex);
        String date = dateChoice.getValue().toString();
        String username = user8.getusername();
        List<Integer> list = new ArrayList<>();
        Captain captain = new Captain(captainName, list);

        createCaptain(captainName);
        int age = Integer.parseInt(ageText.getText());
        try {
            if (rout.getArrival().getName().equals(rout.getDeparture().getName())) {
                throw new OwnException("Same departure and arrival");
            }
        }catch (OwnException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            alert.close();
            return null;
        }
        if (weather.isSelected()) {
            if (studentCheck.isSelected()) {
                if (age <= 18) {
                    Order order = new OrderDiscountStudent(user8,rout , captain, date, boat1, normalPrice, true, age);
                    return order;
                } else {
                    System.out.println("You are not a student");
                    return null;
                }
            } else if (seniorCheck.isSelected()) {
                if (age >= 60) {
                    Order order = new OrderDiscountSenior(user8, rout, captain, date, boat1, normalPrice, true, age);
                    return order;
                } else {
                    System.out.println("You are not a senior");
                    return null;
                }
            } else {
                Order order = new OrderExtend(user8, rout, captain, date, boat1, normalPrice, true, age);
                return order;
            }
        } else {
            if (studentCheck.isSelected()) {
                if (age <= 18) {
                    Order order = new OrderDiscountStudent(user8, rout, captain, date, boat1, normalPrice, false, age);
                    return order;
                } else {
                    System.out.println("You are not a student");
                    return null;
                }
            } else if (seniorCheck.isSelected()) {
                if (age >= 60) {
                    Order order = new OrderDiscountSenior(user8, rout, captain, date, boat1, normalPrice, false, age);
                    return order;
                } else {
                    System.out.println("You are not a senior");
                    return null;
                }
            } else {
                System.out.println("No discount");
                Order order = new OrderExtend(user8, rout, captain, date, boat1, normalPrice, false, age);
                return order;
            }
        }
    }

    /**
     * Method that creates a harbour object for the departure
     * @return
     */
    protected Harbour departure_Choice(){
        String departure = (String) departure_choice.getValue();
        Harbour harbour = new Harbour(departure);
        return harbour;
    }


    /**
     * Method that creates a harbour object for the arrival
     * @return
     */
    protected Harbour arrival_Choice(){
        String arrival = (String) arrival_choice.getValue();
        Harbour harbour = new Harbour(arrival);
        return harbour;
    }

    protected Rout createRout() {
        Harbour departure = departure_Choice();
        Harbour arrival = arrival_Choice();
          Rout rout = new Rout(departure, arrival);
          return rout;
    }

    /**
     * Method that creates a boat object
     * @return boat
     */
    protected Boat createBoat() {
        String boat = (String) boats_choicebox.getValue();
        DatabaseFacade database8 = new DatabaseFacade();
        database8.connect("mongodb+srv://mati:dtPRaA7r2wsDOzcD@cluster.jpmq3gk.mongodb.net/?retryWrites=true&w=majority", "Boats", "boat");
        FindIterable<Document> documents = database8.find();
        factory = new Factory();

        for (Document doc : documents) {
            if(doc.get("_id").equals(boat)){
                String name = boats_choicebox.getValue().toString();
                String type = "";
                int capacity = (int) doc.get("capacity");
                int sailArea = 0;
                int maxSpeed = 0;
                if (doc.containsKey("sailArea")) {
                    sailArea = (int) doc.get("sailArea");
                }
                if (doc.containsKey("maxSpeed")) {
                    maxSpeed = (int) doc.get("maxSpeed");
                }
                if (doc.containsKey("type")) {
                    type = (String) doc.get("type");
                }
                return factory.createBoat(type,name, capacity, sailArea, maxSpeed);
            }
        }
        return null;
    }


    /**
     * Method for pop up window when order is applied
     * @throws IOException
     */
    public void popUp() throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu_user/popUpOrderDiscountApply.fxml")));
       Scene scene = new Scene(root);
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
   }


    /**
     * Method for pop up window when order is not applied
     * @throws IOException
     */
    public void popUpNotApplied() throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu_user/popUpNotApplied.fxml")));
       Scene scene = new Scene(root);
       Stage stage = new Stage();
       stage.setScene(scene);
       stage.show();
   }

    /**
     * Method for pop up window when order has no discount
     * @throws IOException
     */
    public void noDiscount() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu_user/anyDiscount.fxml")));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that checks if the order is applied correctly
     * @throws IOException
     */
   public void check() throws IOException {
       if (ageText.getText().isEmpty()){
           popUpNotApplied();
           check = false;
           return;
       }
       else {
           int age = Integer.parseInt(ageText.getText());
           if (studentCheck.isSelected()) {
               if (age <= 18) {
                   popUp();
                   check = true;
               } else {
                   popUpNotApplied();
                   check = false;
               }
           }
           else if (seniorCheck.isSelected()) {
               if (age >=60) {
                   popUp();
                   check = true;
               } else {
                   popUpNotApplied();
                   check = false;
               }
           }
           else {
               check = true;
               noDiscount();
           }
       }
   }

    /**
     * Method that creates a captain object
     * @param captain
     */
    public void createCaptain(String captain) {
        List<Integer> list = new ArrayList<>();
        Captain captain1 = new Captain(captain, list);
        List<Captain> captains = new ArrayList<>();

        // Try to read the list from the file
        try {
            FileInputStream fileIn = new FileInputStream("captains.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            captains = (List<Captain>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {

        }

        // Check if the captain already exists
        boolean captainExists = false;
        for (Captain a : captains) {
            if (captain1.getName().equals(a.getName())) {
                captainExists = true;
                break;
            }
        }

        // Add the new captain to the list if it doesn't already exist
        if (!captainExists) {
            captains.add(captain1);

            // Serialize the list back to the file
            try {
                FileOutputStream fileOut = new FileOutputStream("captains.ser"); // overwrite the existing file
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(captains);
                out.close();
                fileOut.close(); // close the FileOutputStream object
            } catch (IOException i) {
                i.printStackTrace();
            }

        }
    }

    /**
     * Method for log out menu item
     * @param event
     * @throws IOException
     */
    @FXML
    protected void onLogOut(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login/login.fxml")));
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    /**
     * Method for setting the current user in a serialized file
     * @param user
     * @throws IOException
     */
    protected void actualUser(User user) throws IOException {
        User actualUser = user;
        File file = new File("currentUser.ser");
        // delete the contents of the file
        file.delete();
        file.createNewFile();

        // serialize the order and write it to the file
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(actualUser);
        out.close();
        fileOut.close();

    }

    /**
     * Method for deserializing the current user
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected User deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("currentUser.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        User user = (User) in.readObject();
        in.close();
        fileIn.close();
        return user;
    }
}
