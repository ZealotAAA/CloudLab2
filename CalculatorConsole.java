import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CalculatorConsole {
    private String operation;
    private String operand;
    private int n1;
    private int n2;

    private static Scanner in = new Scanner(System.in);

    public CalculatorConsole(String operation, int n1, int n2) {
        this.operation = operation;
        this.n1 = n1;
        this.n2 = n2;
    }

    public static void run() throws IOException {
        CalculatorConsole app = new CalculatorConsole("reset", 0, 0);
        boolean exit;

        do {
            System.out.println("******** Calculator ********");

            System.out.println("N1: ");
            app.setN1(getOption());

            System.out.println("N2:");
            app.setN2(getOption());

            int option = getOperationOption();

            app.setOperation(option == 1 ? "+" : option == 2 ? "-" : option == 3 ? "/" : "*");
            app.setOperand(option == 1 ? " + " : option == 2 ? " - " : option == 3 ? " / " : " * ");

            double result = sentCalcReq(app.getOperation(), app.getN1(), app.getN2());

            System.out.println("RESULT-> " + app.getN1() + app.getOperand() + app.getN2() + " = " + result);

            System.out.println("Enter '1' to continue, or another character to Exit");
            exit = in.nextLine().equals("1");
        } while (exit);

        System.exit(0);
    }

    private static int getOperationOption() {
        int choice = 0;
        boolean i;
        System.out.println("Please select the operation:\n1]  +\n2]  -\n3]  /\n4]  *\n");
        do {
            try {
                choice = Integer.parseInt(in.nextLine());
                i = choice > 4 || choice < 1;
            } catch (NumberFormatException e) {
                i = true;
            }
        } while (i);
        return choice;
    }

    private static int getOption() {
        int choice = 0;
        boolean i;
        do {
            try {
                choice = Integer.parseInt(in.nextLine());
                i = false;
            } catch (NumberFormatException e) {
                i = true;
            }
        } while (i);
        return choice;
    }

    private static double sentCalcReq(String operand, int n1, int n2) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("https://salty-temple-47968.herokuapp.com/calc?operand=" + operand + "&numberone=" + n1 + "&numbertwo=" + n2);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (var reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        )) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return Double.parseDouble(result.toString());
    }
    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }
}
