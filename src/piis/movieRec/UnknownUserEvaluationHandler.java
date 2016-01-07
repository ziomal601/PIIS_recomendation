package piis.movieRec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class UnknownUserEvaluationHandler {
	public static ArrayList<EvaluationData> evaluateUnknownUsers(LinkedHashMap<Integer,Movie> movieParameters,
																	LinkedHashMap<Integer,ArrayList<EvaluationData>> unknownUsersEvaluations,
																	LinkedHashMap<Integer,ArrayList<EvaluationData>> usersEvaluations,
																	int kNN) {
		ArrayList<EvaluationData> predictedUsersEvaluations = new ArrayList<EvaluationData>();
		
		MovieNNHandler movieNNHandler = new MovieNNHandler(movieParameters);
		
		for (Map.Entry<Integer, ArrayList<EvaluationData>> evals : unknownUsersEvaluations.entrySet()) {
			Integer userId = evals.getKey();
			ArrayList<EvaluationData> knownEvaluations = usersEvaluations.get(userId);
			ArrayList<Integer> knownMoviesIds = new ArrayList<Integer>();
			for (EvaluationData eval : knownEvaluations) {
				knownMoviesIds.add(eval.movieId);
			}
			LinkedHashMap<Integer,ArrayList<Integer>> movieNN = movieNNHandler.assignNNToMovies(kNN, knownMoviesIds);
			
			for (Map.Entry<Integer, ArrayList<Integer>> movie : movieNN.entrySet()) {
				
				int evaluation = evaluateMovie(usersEvaluations.get(userId), movie.getValue(), kNN);
				int id = -1;
				for (EvaluationData evalData : evals.getValue()) {
					if (evalData.movieId == movie.getKey()) {
						id = evalData.id;
						System.out.println(id);
						break;
					}
				}
				if (id != -1) {
					predictedUsersEvaluations.add(new EvaluationData(id, userId, movie.getKey(), evaluation));
				}
			}
		}
		
		return predictedUsersEvaluations;
	}
	
	private static int evaluateMovie(ArrayList<EvaluationData> movieEvaluations, ArrayList<Integer> movieNNIds, int kNN) {
		int evaluation = 0;
		for (Integer movie : movieNNIds) {
			for (EvaluationData movieNNEval : movieEvaluations) {
				if (movie == movieNNEval.movieId) {
					evaluation += movieNNEval.evaluation;
					break;
				}
			}
		}
		evaluation /= kNN;
		return evaluation;
	}
}
