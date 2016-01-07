package piis.movieRec;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MovieEvaluationsWriter {
	//Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ";";

    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeEvaluationsCsvFile(String fileName, ArrayList<EvaluationData> evaluations) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write a new submission object list to the CSV file
            for (EvaluationData evaluation : evaluations) {
                fileWriter.append(String.valueOf(evaluation.id));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(evaluation.userId));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(evaluation.movieId));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(evaluation.evaluation));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (Exception e) {
            System.out.println("Error in MovieEvaluationsWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }
}
