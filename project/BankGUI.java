import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/* ==========================================================
   BankAccount Class
   ========================================================== */
class BankAccount {

    private int accountNumber;
    private String accountHolderName;
    private double balance;

    public BankAccount(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

/* ==========================================================
   Bank Class
   ========================================================== */
class Bank {

    private ArrayList<BankAccount> accounts = new ArrayList<>();

    // Create Account
    public String createAccount(int accNo, String name, double balance) {

        if (findAccount(accNo) != null) {
            return "Account Number Already Exists!";
        }

        accounts.add(new BankAccount(accNo, name, balance));
        return "Account Created Successfully!";
    }

    // Find Account
    public BankAccount findAccount(int accNo) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountNumber() == accNo) {
                return acc;
            }
        }
        return null;
    }

    // Deposit
    public String deposit(int accNo, double amount) {

        BankAccount acc = findAccount(accNo);

        if (acc == null)
            return "Account Not Found!";

        if (amount <= 0)
            return "Invalid Deposit Amount!";

        acc.setBalance(acc.getBalance() + amount);
        return "Amount Deposited Successfully!";
    }

    // Withdraw
    public String withdraw(int accNo, double amount) {

        BankAccount acc = findAccount(accNo);

        if (acc == null)
            return "Account Not Found!";

        if (amount <= 0)
            return "Invalid Withdrawal Amount!";

        if (amount > acc.getBalance())
            return "Insufficient Balance!";

        acc.setBalance(acc.getBalance() - amount);
        return "Amount Withdrawn Successfully!";
    }

    // Check Balance
    public String getBalance(int accNo) {

        BankAccount acc = findAccount(accNo);

        if (acc == null)
            return "Account Not Found!";

        return "Account Holder: " + acc.getAccountHolderName()
                + "\nCurrent Balance: " + acc.getBalance();
    }
}

/* ==========================================================
   BankGUI Class (Main Class)
   ========================================================== */
public class BankGUI extends JFrame implements ActionListener {

    private JTextField accNumberField, nameField, amountField;
    private JTextArea outputArea;
    private JButton createBtn, depositBtn, withdrawBtn, balanceBtn, clearBtn;

    private Bank bank;

    public BankGUI() {

        bank = new Bank();

        setTitle("Bank Account Simulator");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        inputPanel.add(new JLabel("Account Number:"));
        accNumberField = new JTextField();
        inputPanel.add(accNumberField);

        inputPanel.add(new JLabel("Account Holder Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        add(inputPanel, BorderLayout.NORTH);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));

        createBtn = new JButton("Create");
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        balanceBtn = new JButton("Balance");
        clearBtn = new JButton("Clear");

        buttonPanel.add(createBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(clearBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // ===== Output Area =====
        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        // Add Listeners
        createBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        try {

            int accNo = Integer.parseInt(accNumberField.getText());
            String name = nameField.getText();
            double amount = 0;

            if (!amountField.getText().isEmpty()) {
                amount = Double.parseDouble(amountField.getText());
            }

            if (e.getSource() == createBtn) {

                String message = bank.createAccount(accNo, name, amount);
                outputArea.setText(message);

            } else if (e.getSource() == depositBtn) {

                String message = bank.deposit(accNo, amount);
                outputArea.setText(message);

            } else if (e.getSource() == withdrawBtn) {

                String message = bank.withdraw(accNo, amount);
                outputArea.setText(message);

            } else if (e.getSource() == balanceBtn) {

                String message = bank.getBalance(accNo);
                outputArea.setText(message);

            } else if (e.getSource() == clearBtn) {

                accNumberField.setText("");
                nameField.setText("");
                amountField.setText("");
                outputArea.setText("");
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric values!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new BankGUI();
    }
}