package DesignPattern;

import Boats.*;

/**
 * Factory class for creating boats.
 * Builder pattern is used to create boats
 */
public class Factory {

    /**
     * Creates a boat based on the type of boat
     * Factory method pattern is used to create boats
     * @param type
     * @param name
     * @param capacity
     * @param sailArea
     * @param maxSpeed
     * @return Boat
     */
    public Boat createBoat(String type, String name, int capacity, int sailArea, int maxSpeed) {
        if (type.equalsIgnoreCase("SailBoat")) {
            return new SailBoatBuilder(name, capacity).setSailArea(sailArea).setMaxSpeed(maxSpeed).build();
        } else if (type.equalsIgnoreCase("SpeedBoat")) {
            return new SpeedBoatBuilder(name, capacity).setMaxSpeed(maxSpeed).build();
        } else {
            return new Boat(name, capacity);
        }
    }

    /**
     * Builder class for creating boats
     * Builder pattern is used to create boats
     */
    class BoatBuilder {
        protected String name;
        protected int capacity;

        public BoatBuilder(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }
    }

    /**
     * Builder class for creating sail boats
     * Builder pattern is used to create boats
     */
    class SailBoatBuilder extends BoatBuilder {
        private int sailArea;
        private int maxSpeed;

        public SailBoatBuilder(String name, int capacity) {
            super(name, capacity);
        }

        public SailBoatBuilder setSailArea(int sailArea) {
            this.sailArea = sailArea;
            return this;
        }

        public SailBoatBuilder setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
            return this;
        }

        public SailBoat build() {
            return new SailBoat(name, capacity, sailArea, maxSpeed);
        }
    }

    /**
     * Builder class for creating speed boats
     * Builder pattern is used to create boats
     */
    class SpeedBoatBuilder extends BoatBuilder {
        private int maxSpeed;

        public SpeedBoatBuilder(String name, int capacity) {
            super(name, capacity);
        }

        public SpeedBoatBuilder setMaxSpeed(int maxSpeed) {
            this.maxSpeed = maxSpeed;
            return this;
        }

        public SpeedBoat build() {
            return new SpeedBoat(name, capacity, maxSpeed);
        }
    }
}
