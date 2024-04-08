
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TillManagement {

    public static void main(String[] args) {
        // Define the initial float of the till
        Map<String, Integer> till = new HashMap<>();
        till.put("R50", 5);
        till.put("R20", 5);
        till.put("R10", 6);
        till.put("R5", 12);
        till.put("R2", 10);
        till.put("R1", 10);

        // Read in the transactions from a file
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = br.readLine())!= null) {
                processTransaction(line, till);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Output the final amount left in the till
        System.out.println("Till Start: " + getTotalAmount(till));
    }

    private static void processTransaction(String transaction, Map<String, Integer> till) {
        // Parse the transaction string into its components
        String[] parts = transaction.split(",");
        String[] items = parts[0].split(";");
        String[] payment = parts[1].split("-");
        double totalCost = 0;

        // Calculate the total cost of the items
        for (String item : items) {
            String[] itemParts = item.split(" ");
            double price = Double.parseDouble(itemParts[itemParts.length - 1].replace("R", ""));
            totalCost += price;
        }

        // Calculate the change to be given
        double paid = Double.parseDouble(payment[0].replace("R", ""));
        double change = paid - totalCost;
        Map<String, Integer> changeBreakdown = calculateChange(change, till);

        // Output the results
        System.out.println("Till Start: " + getTotalAmount(till) + ", Transaction Total: R" + totalCost + ", Paid: R" + paid + ", Change Total: R" + change + ", Change Breakdown: " + getChangeBreakdownString(changeBreakdown));
    }

    private static Map<String, Integer> calculateChange(double change, Map<String, Integer> till) {
        // Calculate the change to be given in each denomination
        Map<String, Integer> changeBreakdown = new HashMap<>();
        for (Map.Entry<String, Integer> entry : till.entrySet()) {
            String denomination = entry.getKey();
            int count = entry.getValue();
            int numDenominations = (int) (change / Double.parseDouble(denomination));
            if (numDenominations > count) {
                numDenominations = count;
            }
            changeBreakdown.put(denomination, numDenominations);
            change -= numDenominations * Double.parseDouble(denomination);
            till.put(denomination, count - numDenominations);
        }
        return changeBreakdown;
    }

    private static String getChangeBreakdownString(Map<String, Integer> changeBreakdown) {
        // Convert the change breakdown map to a string
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : changeBreakdown.entrySet()) {
            if (sb.length() > 0) {
                sb.append("-");
            }
            sb.append(entry.getKey() + "x" + entry.getValue());
        }
        return sb.toString();
    }

    private static double getTotalAmount(Map<String, Integer> till) {
        // Calculate the total amount of money in the till
        double total = 0;
        for (Map.Entry<String, Integer> entry : till.entrySet()) {
            total += entry.getValue() * Double.parseDouble(entry.getKey());
        }
        return total;
    }
}