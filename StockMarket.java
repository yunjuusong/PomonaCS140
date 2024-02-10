import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StockMarket {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java StockMarket <inputfile> <outputfile>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];
        int days = 0;
        int[] value;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            days = Integer.parseInt(reader.readLine());
            value = new int[days];
            for(int i = 0; i < days; i++){
                value[i] = Integer.parseInt(reader.readLine());
            }


            // PROBLEM START
            int startDay = 0;
            int maxLength = 0;
            for (int i = 1; i < days; i++) {
                int length = findLongestRun(value, i, days - 1);
                if (length > maxLength) {
                    maxLength = length;
                    startDay = i;
                }
            }

            // Writing results to output file
            writer.write(maxLength + "\n");
            writer.write((startDay + 1) + "\n"); // 1-based indexing
            for (int i = startDay; i < startDay + maxLength; i++) {
                writer.write(value[i] + "\n");
            }

            // PROBLEM END

            reader.close();
            writer.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static int findLongestRun(int[] arr, int start, int end) {
        if (start == end) {
            return 1;
        }

        int mid = (start + end) / 2;
        int leftRun = findLongestRun(arr, start, mid);
        int rightRun = findLongestRun(arr, mid + 1, end);
        int crossMidRun = findCrossingRunLength(arr, start, mid, end);

        return Math.max(Math.max(leftRun, rightRun), crossMidRun);
    }

    public static int findCrossingRunLength(int[] arr, int start, int mid, int end) {
        if (arr[mid] > arr[mid + 1]) {
            return 0; // Crossing run doesn't exist
        }

        int leftMax = 0;
        int leftCount = 0;
        for (int i = mid; i >= start; i--) {
            if (arr[i] <= arr[i + 1]) {
                leftCount++;
            } else {
                break;
            }
            leftMax = leftCount;
        }

        int rightMax = 0;
        int rightCount = 0;
        for (int i = mid + 1; i <= end; i++) {
            if (arr[i] >= arr[i - 1]) {
                rightCount++;
            } else {
                break;
            }
            rightMax = rightCount;
        }

        return leftMax + rightMax;
    }
}
