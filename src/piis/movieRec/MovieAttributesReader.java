package piis.movieRec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.parser.JSONParser;

public class MovieAttributesReader {
    public static LinkedHashMap<Integer,Movie> readMovieParameters(String fileName) {

        BufferedReader fileReader = null;
        LinkedHashMap<Integer,Movie> movieParameters = new LinkedHashMap<Integer,Movie>();
        JSONParser jsonParser = new JSONParser();
        try {
        	int index = 1;
        	String line = "";
            fileReader = new BufferedReader(new FileReader(fileName));
            while ((line = fileReader.readLine()) != null) {
                Map movieMap = (Map) jsonParser.parse(line);
                Movie movie = new Movie(movieMap);
                movieParameters.put(index, movie);
                index++;
            }
        } catch (Exception e) {
            System.out.println("Error in MovieAttributesReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {

                System.out.println("Error while closing fileReader !!!");

                e.printStackTrace();
            }
        }
        return movieParameters;
    }
}
