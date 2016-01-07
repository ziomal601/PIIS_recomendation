package piis.movieRec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class EvaluationsReader {
	//Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ";";

    //Submission attributes index
    private static final int SUBMISSION_ID_IDX = 0;
    private static final int SUBMISSION_PERSON = 1;
    private static final int SUBMISSION_MOVIE = 2;
    private static final int SUBMISSION_RATE = 3;

    public static LinkedHashMap<Integer,ArrayList<EvaluationData>> getEvaluations(String fileName) {
    	LinkedHashMap<Integer,ArrayList<EvaluationData>> evaluationsMap = new LinkedHashMap<Integer,ArrayList<EvaluationData>>();
    	
    	ArrayList<EvaluationData> dataArrayList = readEvaluationsCsvFile(fileName);
    	
    	while (!dataArrayList.isEmpty()) {
    		int userId = dataArrayList.get(0).userId;
    		ArrayList<EvaluationData> userEvaluation = new ArrayList<EvaluationData>();
    		Iterator it = dataArrayList.iterator();
    		while (it.hasNext()) {
    			EvaluationData evaluation = (EvaluationData) it.next();
    			if (evaluation.userId == userId) {
    				userEvaluation.add(evaluation);
    				it.remove();
    			}
    		}
    		evaluationsMap.put(userId, userEvaluation);
    	}
    	
    	return evaluationsMap;
    }
    
    public static ArrayList<EvaluationData> readEvaluationsCsvFile(String fileName) {

        BufferedReader fileReader = null;
        //Create a new list of submission to be filled by CSV file data
        ArrayList<EvaluationData> evaluations = new ArrayList<EvaluationData>();
        try {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    //Create a new submission object and fill his  data
                    if(tokens[SUBMISSION_RATE].equals("NULL")) {
                        tokens[SUBMISSION_RATE] = "-1";
                    }
                    EvaluationData evaluation = new EvaluationData(Integer.parseInt(tokens[SUBMISSION_ID_IDX]), Integer.parseInt(tokens[SUBMISSION_PERSON]), Integer.parseInt(tokens[SUBMISSION_MOVIE]), Integer.parseInt(tokens[SUBMISSION_RATE]));
                    evaluations.add(evaluation);
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
        return evaluations;
    }
}
