import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first three digits of your bank account: ");
        String accountPrefix = scanner.nextLine().trim();
        String url = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";
        StringBuilder data = new StringBuilder();
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine())!=null) {
                data.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Failed to retrieve the bank data. Please try again later.");
            e.printStackTrace();
            return;
        }
        String[] lines = data.toString().split("\n");
        String bankName =null;
        String abbreviatedBankNumber = null;
        for (String line :lines) {
            String[] parts =line.split("\t");
            if (parts.length >=2 && parts[0].startsWith(accountPrefix)) {
                abbreviatedBankNumber =parts[0];
                bankName = parts[1];
                break;
            }
        }
        if (bankName != null) {
            System.out.println("The bank with account prefix "+accountPrefix+" is: "+bankName);
        } else {
            System.out.println("No bank found with the specified account prefix.");
        }
    }
}
