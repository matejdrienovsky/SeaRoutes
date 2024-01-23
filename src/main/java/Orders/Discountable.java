package Orders;

/**
 * Interface to calculate the discount based on the age of the customer
 * Default implementation is provided
 */
public interface Discountable {
    /**
     * Calculates the discount based on the age of the customer
     * Default implementation is provided
     * @param age
     * @return
     */
    default double calculateDiscount(int age) {
        if (age <=18) {
            // Apply 20% discount for customers under 18
            System.out.println("Student discount applied");
            return 0.8;
        } else if (age >= 60) {
            // Apply 15% discount for senior citizens
            System.out.println("Senior citizen discount applied");
            return 0.75;
        } else {
            // No discount applied
            System.out.println("No discount applied");
            return 0;
        }
    }
}

