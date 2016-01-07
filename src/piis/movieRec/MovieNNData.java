package piis.movieRec;

public class MovieNNData {
	public int movieId;
	public int[] NNMovieIds;

	MovieNNData(int movieId, int[] NNMovieIds) {
		this.movieId = movieId;
		this.NNMovieIds = NNMovieIds;
	}
	
	public String toString() {
		String returnString = "movieId: " + movieId + ", NNMovieIds: {";
		for (int i: NNMovieIds) {
			returnString += i + ",";
		}
		returnString += "}\n";
		return returnString;
	}
}
