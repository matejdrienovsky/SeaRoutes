package Captains;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to create a captain object.
 * It contains the captain's name, age, rating and a list of ratings.
 */
public class Captain implements Serializable {
    private String name;
    private int age;
    private double rating;
    private List<Integer> ratings;

    public Captain(){}
    public Captain(String name, List<Integer> ratings) {
        this.name = name;
        this.ratings = ratings;

    }

    public String getName() {
        return name;
    }


    public int getAge() {
        return age;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setAge(int age) {
        this.age = age;
    }


    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public List<Integer> getRatings(){
        return ratings;
    }

    public void setRatings(List<Integer> ratings){
        this.ratings = ratings;
    }


    /**
     * This method is used to calculate the rating of a captain.
     * @param ratingnew
     * @return rating of the captain after adding a new rating and calculating the average
     */
    public double calculateRating(int ratingnew){
        ratings.add(ratingnew);
        double sum = 0;
        for (int i = 0; i < ratings.size(); i++) {
            sum += ratings.get(i);
        }
        rating = sum / ratings.size();
        rating = Math.round(rating * 100.0) / 100.0;

        return rating;
    }

}
