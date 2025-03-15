import java.util.*;
import java.text.*;

class StockTradingPlatform {
    static Scanner scanner = new Scanner(System.in);
    static User currentUser = null;
    static Map<String, Stock> stockMarket = new HashMap<>();
    
    public static void main(String[] args) {
       
        stockMarket.put("AAPL", new Stock("AAPL", "Apple", 150.00));
        stockMarket.put("GOOGL", new Stock("GOOGL", "Google", 2750.00));
        stockMarket.put("AMZN", new Stock("AMZN", "Amazon", 3400.00));

        
        while (true) {
            if (currentUser == null) {
                System.out.println("Welcome to the Stock Trading Platform");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (choice == 1) {
                    registerUser();
                } else if (choice == 2) {
                    loginUser();
                }
            } else {
                System.out.println("\nWelcome, " + currentUser.getName());
                showPortfolio();

                System.out.println("\n1. View Market Data");
                System.out.println("2. Buy Stock");
                System.out.println("3. Sell Stock");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline

                if (choice == 1) {
                    viewMarketData();
                } else if (choice == 2) {
                    buyStock();
                } else if (choice == 3) {
                    sellStock();
                } else if (choice == 4) {
                    currentUser = null; // Log out
                }
            }
        }
    }

    // Register new user
    static void registerUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your initial deposit amount: ");
        double balance = scanner.nextDouble();
        currentUser = new User(name, balance);
        System.out.println("Registration successful!");
    }

    // Log in an existing user
    static void loginUser() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        // In a real application, you would verify login credentials from a database
        currentUser = new User(name, 10000);  // Setting initial balance
        System.out.println("Login successful!");
    }

    // Display the current portfolio
    static void showPortfolio() {
        System.out.println("\nYour Portfolio:");
        currentUser.showPortfolio();
    }

    
    static void viewMarketData() {
        System.out.println("\nMarket Data:");
        for (Stock stock : stockMarket.values()) {
            stock.displayStockInfo();
        }
    }

    static void buyStock() {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.nextLine().toUpperCase();
        Stock stock = stockMarket.get(symbol);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter number of shares to buy: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        double cost = stock.getPrice() * quantity;

        if (currentUser.getBalance() < cost) {
            System.out.println("Insufficient balance!");
        } else {
            currentUser.buyStock(stock, quantity, cost);
            System.out.println("Purchase successful!");
        }
    }

   
    static void sellStock() {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.nextLine().toUpperCase();
        Stock stock = stockMarket.get(symbol);
        if (stock == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter number of shares to sell: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (currentUser.hasStock(stock, quantity)) {
            double proceeds = stock.getPrice() * quantity;
            currentUser.sellStock(stock, quantity, proceeds);
            System.out.println("Sale successful!");
        } else {
            System.out.println("You do not have enough shares to sell!");
        }
    }
}

class User {
    private String name;
    private double balance;
    private Map<String, StockHolding> portfolio;

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void buyStock(Stock stock, int quantity, double cost) {
        balance -= cost;
        portfolio.putIfAbsent(stock.getSymbol(), new StockHolding(stock, 0));
        StockHolding holding = portfolio.get(stock.getSymbol());
        holding.addShares(quantity);
    }

    public boolean hasStock(Stock stock, int quantity) {
        if (!portfolio.containsKey(stock.getSymbol())) return false;
        StockHolding holding = portfolio.get(stock.getSymbol());
        return holding.getShares() >= quantity;
    }

    public void sellStock(Stock stock, int quantity, double proceeds) {
        balance += proceeds;
        StockHolding holding = portfolio.get(stock.getSymbol());
        holding.removeShares(quantity);
        if (holding.getShares() == 0) {
            portfolio.remove(stock.getSymbol());
        }
    }

    public void showPortfolio() {
        System.out.println("Balance: $" + balance);
        if (portfolio.isEmpty()) {
            System.out.println("No stocks in portfolio.");
        } else {
            for (StockHolding holding : portfolio.values()) {
                holding.displayHoldingInfo();
            }
        }
    }
}

class Stock {
    private String symbol;
    private String name;
    private double price;

    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void displayStockInfo() {
        System.out.println(symbol + " (" + name + ") - $" + price);
    }
}

class StockHolding {
    private Stock stock;
    private int shares;

    public StockHolding(Stock stock, int shares) {
        this.stock = stock;
        this.shares = shares;
    }

    public int getShares() {
        return shares;
    }

    public void addShares(int quantity) {
        shares += quantity;
    }

    public void removeShares(int quantity) {
        shares -= quantity;
    }

    public void displayHoldingInfo() {
        System.out.println(stock.getSymbol() + " (" + stock.getName() + ") - " + shares + " shares at $" + stock.getPrice() + " each");
    }
}
