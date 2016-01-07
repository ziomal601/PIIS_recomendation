package piis.movieRec;

public class EvaluationData {
	public int id;
	public int userId;
	public int movieId;
	public int evaluation;

	EvaluationData(int id, int userId, int movieId, int evaluation) {
		this.id = id;
		this.userId = userId;
		this.movieId = movieId;
		this.evaluation = evaluation;
	}
	
	EvaluationData(String id, String userId, String movieId, String evaluation) {
		this.id = Integer.parseInt(id);
		this.userId = Integer.parseInt(userId);
		this.movieId = Integer.parseInt(movieId);
		this.evaluation = Integer.parseInt(evaluation);
	}
	
	public String toString() {
		String returnString = "id: " + id + ", userId: " + userId + ", movieId: " + movieId + ", evaluation: " + evaluation;
		return returnString;
	}
}
