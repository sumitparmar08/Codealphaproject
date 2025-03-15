import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Room {
    private int roomId;
    private String type;
    private double price;
    private boolean available;

    public Room(int roomId, String type, double price) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.available = true; // initially all rooms are available
    }

    public int getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Reservation {
    private int reservationId;
    private String userName;
    private Room room;
    private String checkInDate;
    private String checkOutDate;
    private double amount;

    public Reservation(int reservationId, String userName, Room room, String checkInDate, String checkOutDate) {
        this.reservationId = reservationId;
        this.userName = userName;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.amount = room.getPrice();
    }

    public int getReservationId() {
        return reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public double getAmount() {
        return amount;
    }

    public String getUserName() {
        return userName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }
}

class HotelReservationSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int reservationCounter;

    public HotelReservationSystem() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationCounter = 1;
        initializeRooms();
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 100));
        rooms.add(new Room(102, "Double", 150));
        rooms.add(new Room(103, "Suite", 250));
    }

    public void searchAndBookRoom(String type, String checkInDate, String checkOutDate, String userName) {
        Room availableRoom = null;
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type)) {
                availableRoom = room;
                break;
            }
        }

        if (availableRoom != null) {
            Reservation reservation = new Reservation(reservationCounter++, userName, availableRoom, checkInDate, checkOutDate);
            reservations.add(reservation);
            availableRoom.setAvailable(false); // mark room as booked
            System.out.println("Reservation Confirmed!");
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Room: " + availableRoom.getType());
            System.out.println("Total Amount: $" + availableRoom.getPrice());
            System.out.println("Check-in Date: " + checkInDate);
            System.out.println("Check-out Date: " + checkOutDate);

            processPayment(reservation);
        } else {
            System.out.println("No rooms available for the selected type.");
        }
    }

    private void processPayment(Reservation reservation) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter payment method (Credit Card/Debit Card): ");
        String paymentMethod = scanner.nextLine();
        System.out.println("Processing payment...");
        // Simulate successful payment
        System.out.println("Payment successful via " + paymentMethod);
    }

    public void viewBookingDetails(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                System.out.println("Booking Details:");
                System.out.println("Reservation ID: " + reservation.getReservationId());
                System.out.println("Guest Name: " + reservation.getUserName());
                System.out.println("Room: " + reservation.getRoom().getType());
                System.out.println("Amount Paid: $" + reservation.getAmount());
                System.out.println("Check-in Date: " + reservation.getCheckInDate());
                System.out.println("Check-out Date: " + reservation.getCheckOutDate());
                return;
            }
        }
        System.out.println("Reservation not found.");
    }
}

public class HotelReservationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelReservationSystem system = new HotelReservationSystem();

        System.out.println("Welcome to the Hotel Reservation System!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Search and Book Room");
            System.out.println("2. View Booking Details");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Room Type (Single/Double/Suite): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter Check-in Date (YYYY-MM-DD): ");
                    String checkInDate = scanner.nextLine();
                    System.out.print("Enter Check-out Date (YYYY-MM-DD): ");
                    String checkOutDate = scanner.nextLine();
                    System.out.print("Enter Your Name: ");
                    String userName = scanner.nextLine();

                    system.searchAndBookRoom(type, checkInDate, checkOutDate, userName);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID to view details: ");
                    int reservationId = scanner.nextInt();
                    system.viewBookingDetails(reservationId);
                    break;

                case 3:
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
