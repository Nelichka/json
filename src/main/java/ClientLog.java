import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientLog {

    protected List<String[]> operations = new ArrayList<>();


    public void log(int productNum, int amount) {
        String numberToString = Integer.toString(productNum);
        String amountToString = Integer.toString(amount);
        operations.add(new String[]{numberToString, amountToString});


    }

    public void exportAsCSV(File txtFile) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeAll(operations);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
