// Import required packages
import java.util.ArrayList;   // Used to store multiple accounts
import java.util.Scanner;     // Used to take user input

/* ============================================================
   BankAccount Class
   ------------------------------------------------------------
   This class represents a single bank account.
   It stores account details and basic display functionality.
   ============================================================ */
class BankAccount {

    // Private variables (Encapsulation)
    private int accountNumber;
    private String accountHolderName;
    private double balance;

    // Constructor
    // This constructor is called when a new account is created
    public BankAccount(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Getter method to get account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // Getter method to get account holder name
    public String getAccountHolderName() {
        return accountHolderName;
    }

    // Getter method to get balance
    public double getBalance() {
        return balance;
    }

    // Setter method to update balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Method to display account details
    public void displayAccountDetails() {
        System.out.println("------------------------------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolderName);
        System.out.println("Current Balance: " + balance);
        System.out.println("------------------------------------------------");
    }
}


/* ============================================================
   Transaction Class
   ------------------------------------------------------------
   This class handles deposit and withdrawal operations.
   Business logic is separated from account storage.
   ============================================================ */
class Transaction {

    // Method to deposit money into an account
    public void deposit(BankAccount account, double amount) {

        // Check if deposit amount is valid
        if (amount > 0) {

            // Increase account balance
            account.setBalance(account.getBalance() + amount);

            System.out.println("Amount Deposited Successfully!");

        } else {
            System.out.println("Invalid Deposit Amount!");
        }
    }

    // Method to withdraw money from an account
    public void withdraw(BankAccount account, double amount) {

        // Check if amount is valid
        if (amount <= 0) {

            System.out.println("Invalid Withdrawal Amount!");

        }
        // Check if sufficient balance is available
        else if (amount > account.getBalance()) {

            System.out.println("Insufficient Balance!");

        }
        // If valid, deduct amount
        else {

            account.setBalance(account.getBalance() - amount);

            System.out.println("Amount Withdrawn Successfully!");
        }
    }
}


/* ============================================================
    Bank Class
   ------------------------------------------------------------
   This class manages multiple bank accounts.
   It stores accounts in an ArrayList.
   ============================================================ */
class Bank {

    // ArrayList to store multiple accounts
    private ArrayList<BankAccount> accounts = new ArrayList<>();

     // Method to create a new account (with duplicate check)
public void createAccount(int accNo, String name, double balance) {

    // Check if account already exists
    if (findAccount(accNo) != null) {

        System.out.println("Account Number Already Exists!");
        return;
    }

    // If not exists, create new account
    BankAccount account = new BankAccount(accNo, name, balance);
    accounts.add(account);

    System.out.println("Account Created Successfully!");
}

    // Method to search account using account number
    public BankAccount findAccount(int accNo) {

        // Loop through accounts list
        for (BankAccount account : accounts) {

            // Compare account number
            if (account.getAccountNumber() == accNo) {

                return account;   // Return matching account
            }
        }

        return null;  // If not found
    }

    // Method to display all accounts
    public void displayAllAccounts() {

        if (accounts.isEmpty()) {

            System.out.println("No Accounts Available!");

        } else {

            for (BankAccount account : accounts) {
                account.displayAccountDetails();
            }
        }
    }
}


/* ============================================================
    BankAccountSimulator Class (Main Class)
   ------------------------------------------------------------
   This class contains the main method.
   It provides a menu-driven interface to the user.
   ============================================================ */
public class BankAccountSimulator {

    public static void main(String[] args) {

        // Scanner object for user input
        Scanner sc = new Scanner(System.in);

        // Create Bank object
        Bank bank = new Bank();

        // Create Transaction object
        Transaction transaction = new Transaction();

        int choice;  // Variable to store menu choice

        // Do-while loop for repeated menu display
        do {

            System.out.println("\n===== BANK ACCOUNT SIMULATOR =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter Your Choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    // Creating new account
                    System.out.print("Enter Account Number: ");
                    int accNo = sc.nextInt();
                    sc.nextLine();  // Consume leftover newline

                    System.out.print("Enter Account Holder Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();

                    bank.createAccount(accNo, name, balance);
                    break;

                case 2:
                    // Deposit operation
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    BankAccount account = bank.findAccount(accNo);

                    if (account != null) {

                        System.out.print("Enter Amount to Deposit: ");
                        double amount = sc.nextDouble();

                        transaction.deposit(account, amount);

                    } else {
                        System.out.println("Account Not Found!");
                    }
                    break;

                case 3:
                    // Withdraw operation
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    account = bank.findAccount(accNo);

                    if (account != null) {

                        System.out.print("Enter Amount to Withdraw: ");
                        double amount = sc.nextDouble();

                        transaction.withdraw(account, amount);

                    } else {
                        System.out.println("Account Not Found!");
                    }
                    break;

                case 4:
                    // Check balance
                    System.out.print("Enter Account Number: ");
                    accNo = sc.nextInt();

                    account = bank.findAccount(accNo);

                    if (account != null) {
                        account.displayAccountDetails();
                    } else {
                        System.out.println("Account Not Found!");
                    }
                    break;

                case 5:
                    // Display all accounts
                    bank.displayAllAccounts();
                    break;

                case 6:
                    System.out.println("Thank You for Using Bank Account Simulator!");
                    break;

                default:
                    System.out.println("Invalid Choice! Please Try Again.");
            }

        } while (choice != 6);

        sc.close(); // Close scanner
    }
}