import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Base class for vehicles
class Vehicle {
    private String licensePlate;
    private String model;
    private boolean isRented;

    public Vehicle(String licensePlate, String model) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.isRented = false;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rent() {
        if (!isRented) {
            isRented = true;
        } else {
            System.out.println("Vehicle is already rented.");
        }
    }

    public void returnVehicle() {
        if (isRented) {
            isRented = false;
        } else {
            System.out.println("Vehicle is not currently rented.");
        }
    }
}

// Derived class for cars
class Car extends Vehicle {
    public Car(String licensePlate, String model) {
        super(licensePlate, model);
    }
}

// Base class for people
class Person {
    private String id;
    private String name;

    public Person(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

// Derived class for customers
class Customer extends Person {
    public Customer(String id, String name) {
        super(id, name);
    }
}

// Rental agency class
class RentalAgency {
    private Map<String, Vehicle> vehicles = new HashMap<>();
    private Map<String, Customer> customers = new HashMap<>();
    private Map<String, String> rentals = new HashMap<>(); // customer ID -> vehicle license plate

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getLicensePlate(), vehicle);
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void rentVehicle(String customerId, String licensePlate) {
        Vehicle vehicle = vehicles.get(licensePlate);
        Customer customer = customers.get(customerId);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        if (vehicle.isRented()) {
            System.out.println("Vehicle is already rented.");
            return;
        }

        vehicle.rent();
        rentals.put(customerId, licensePlate);
        System.out.println("Vehicle rented successfully.");
    }

    public void returnVehicle(String customerId) {
        String licensePlate = rentals.get(customerId);

        if (licensePlate == null) {
            System.out.println("No vehicle rented by this customer.");
            return;
        }

        Vehicle vehicle = vehicles.get(licensePlate);
        vehicle.returnVehicle();
        rentals.remove(customerId);
        System.out.println("Vehicle returned successfully.");
    }

    public void listAvailableVehicles() {
        for (Vehicle vehicle : vehicles.values()) {
            if (!vehicle.isRented()) {
                System.out.println("License Plate: " + vehicle.getLicensePlate() + ", Model: " + vehicle.getModel());
            }
        }
    }

    public void listCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName());
        }
    }
}

// Main class to demonstrate the car rental system
public class Main {
    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        Vehicle car1 = new Car("ABC123", "Toyota Camry");
        Vehicle car2 = new Car("XYZ789", "Honda Civic");

        Customer customer1 = new Customer("C001", "John Doe");
        Customer customer2 = new Customer("C002", "Jane Smith");

        agency.addVehicle(car1);
        agency.addVehicle(car2);

        agency.addCustomer(customer1);
        agency.addCustomer(customer2);

        System.out.println("Available vehicles:");
        agency.listAvailableVehicles();
        System.out.println();

        System.out.println("Customers:");
        agency.listCustomers();
        System.out.println();

        System.out.println("Renting a vehicle:");
        agency.rentVehicle("C001", "ABC123");
        System.out.println();

        System.out.println("Available vehicles after renting:");
        agency.listAvailableVehicles();
        System.out.println();

        System.out.println("Returning a vehicle:");
        agency.returnVehicle("C001");
        System.out.println();

        System.out.println("Available vehicles after returning:");
        agency.listAvailableVehicles();
    }
}
