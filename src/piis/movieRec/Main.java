package piis.movieRec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
	
	public static final String movieParametersFilename = "movies_data.json";
	public static final String trainFilename = "train.csv";
	public static final String taskFilename = "task.csv";
	public static final String outputFilename = "output.csv";
	public static final int kNN = 5;
	
	public static void main(String[] args) {
		LinkedHashMap<Integer,Movie> movieParameters = MovieAttributesReader.readMovieParameters(movieParametersFilename);
		LinkedHashMap<Integer,ArrayList<EvaluationData>> usersEvaluations = EvaluationsReader.getEvaluations(trainFilename);
		LinkedHashMap<Integer,ArrayList<EvaluationData>> unknownUsersEvaluations = EvaluationsReader.getEvaluations(taskFilename);
		ArrayList<EvaluationData> predictedUsersEvaluations = UnknownUserEvaluationHandler.evaluateUnknownUsers(movieParameters, unknownUsersEvaluations, usersEvaluations, kNN);
		
		/*
		for (EvaluationData evals: predictedUsersEvaluations) {
			System.out.println(evals);
		}
		*/
		MovieEvaluationsWriter.writeEvaluationsCsvFile(outputFilename, predictedUsersEvaluations);
		
		/*
		for (Map.Entry<Integer, Movie> movie: movieParameters.entrySet()) {
			System.out.println(movie.toString());
		}
		
		for (Map.Entry<Integer, ArrayList<EvaluationData>> unknownUsersEvaluation: unknownUsersEvaluations.entrySet()) {
			System.out.println(unknownUsersEvaluation);
		}
		*/
	}
}
