import java.util.*;
class Account {
    private String accountNumber;
    private String username;
    private String password;
    protected double balance;
    protected ArrayList<String> transactions;

    public Account(String accNum, String user, String pass, double balance) {
        this.accountNumber = accNum;
        this.username = user;
        this.password = pass;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String user, String pass) {
        return username.equals(user) && password.equals(pass);
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add("Deposited: " + amount);
        System.out.println("Deposited successfully!");
    }

    public void displayBalance() {
        System.out.println("Balance: " + balance +"Rupees");
    }

    public void showTransactions() {
        System.out.println("Transaction History:");
        for (String t : transactions) {
            System.out.println(t);
        }
    }
}

class SavingsAccount extends Account {
    public SavingsAccount(String accNum, String user, String pass, double balance) {
        super(accNum, user, pass, balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawn: " + amount);
            System.out.println("Withdrawn successfully!");
        } else {
            System.out.println("Insufficient funds!");
        }
    }
}

class CurrentAccount extends Account {
    public CurrentAccount(String accNum, String user, String pass, double balance) {
        super(accNum, user, pass, balance);
    }

    public void withdraw(double amount) {
        balance -= amount; 
        transactions.add("Withdrawn: " + amount);
        System.out.println("Withdrawn (Overdraft allowed)");
    }
}

public class BankingSystem {
    static ArrayList<Account> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int accCounter = 100;

    public static void main(String[] args) {

        System.out.println("=== Welcome to Bank System ===");

        int choice;

        do {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    Account user = login();
                    if (user != null) {
                        menu(user);
                    } else {
                        System.out.println("Invalid login!");
                    }
                    break;

                case 3:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 3);
    }

    public static void createAccount() {
        System.out.print("Enter Username: ");
        String user = sc.next();

        System.out.print("Enter Password: ");
        String pass = sc.next();

        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        System.out.println("Select Account Type:");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        int type = sc.nextInt();

        String accNum = "ACC" + (++accCounter);

        Account newAccount;

        if (type == 1) {
            newAccount = new SavingsAccount(accNum, user, pass, balance);
        } else {
            newAccount = new CurrentAccount(accNum, user, pass, balance);
        }

        accounts.add(newAccount);

        System.out.println("Account created successfully!");
        System.out.println("Your Account Number: " + accNum);
    }


    public static Account login() {
        System.out.print("Enter Username: ");
        String user = sc.next();

        System.out.print("Enter Password: ");
        String pass = sc.next();

        for (Account acc : accounts) {
            if (acc.authenticate(user, pass)) {
                System.out.println("Login successful!");
                return acc;
            }
        }
        return null;
    }


    public static void menu(Account acc) {
        int choice;

        do {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Transactions");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter amount: ");
                    acc.deposit(sc.nextDouble());
                    break;

                case 2:
                    System.out.print("Enter amount: ");
                    double amt = sc.nextDouble();

                    if (acc instanceof SavingsAccount) {
                        ((SavingsAccount) acc).withdraw(amt);
                    } else {
                        ((CurrentAccount) acc).withdraw(amt);
                    }
                    break;

                case 3:
                    acc.displayBalance();
                    break;

                case 4:
                    transfer(acc);
                    break;

                case 5:
                    acc.showTransactions();
                    break;

                case 6:
                    System.out.println("Logged out!");
                    break;
            }

        } while (choice != 6);
    }


    public static void transfer(Account sender) {
        System.out.print("Enter receiver account number: ");
        String accNum = sc.next();

        Account receiver = null;

        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNum)) {
                receiver = acc;
                break;
            }
        }

        if (receiver == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        if (sender.balance >= amount) {
            sender.balance -= amount;
            receiver.balance += amount;

            sender.transactions.add("Transferred: " + amount + " to " + accNum);
            receiver.transactions.add("Received: " + amount);

            System.out.println("Transfer successful!");
        } else {
            System.out.println("Insufficient balance!");
        }
    }
}