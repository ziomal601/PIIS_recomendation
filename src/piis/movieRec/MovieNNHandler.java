package piis.movieRec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MovieNNHandler {
	
	private Double budgetMax;
	private Double popularityMax;
	private Double revenueMax;
	private Double runtimeMax;
	private Double voteAverageMax;
	private LinkedHashMap<Integer,Movie> movieParameters;
	
	MovieNNHandler(LinkedHashMap<Integer,Movie> movieParameters) {
		this.movieParameters = movieParameters;
		this.budgetMax = (double)(Collections.max(movieParameters.values(), new Comparator<Movie>() {
			@Override
			public int compare(Movie first, Movie second) {
				if (first.budget > second.budget) return 1;
				else if (first.budget < second.budget) return -1;
				return 0;
			}
		})).budget;
		this.popularityMax = (Collections.max(movieParameters.values(), new Comparator<Movie>() {
			@Override
			public int compare(Movie first, Movie second) {
				if (first.popularity > second.popularity) return 1;
				else if (first.popularity < second.popularity) return -1;
				return 0;
			}
		})).popularity;
		this.revenueMax = (double)(Collections.max(movieParameters.values(), new Comparator<Movie>() {
			@Override
			public int compare(Movie first, Movie second) {
				if (first.revenue > second.revenue) return 1;
				else if (first.revenue < second.revenue) return -1;
				return 0;
			}
		})).revenue;
		this.runtimeMax = (double)(Collections.max(movieParameters.values(), new Comparator<Movie>() {
			@Override
			public int compare(Movie first, Movie second) {
				if (first.runtime > second.runtime) return 1;
				else if (first.runtime < second.runtime) return -1;
				return 0;
			}
		})).runtime;
		this.voteAverageMax = (Collections.max(movieParameters.values(), new Comparator<Movie>() {
			@Override
			public int compare(Movie first, Movie second) {
				if (first.voteAverage > second.voteAverage) return 1;
				else if (first.voteAverage < second.voteAverage) return -1;
				return 0;
			}
		})).voteAverage;
	}
	
	public LinkedHashMap<Integer,ArrayList<Integer>> assignNNToMovies(int kNN, ArrayList<Integer> knownMoviesIds) {
		LinkedHashMap<Integer,ArrayList<Integer>> movieNN = new LinkedHashMap<Integer,ArrayList<Integer>>();
		LinkedHashMap<Integer,Movie> knownMovieParameters = new LinkedHashMap<Integer,Movie>();
		LinkedHashMap<Integer,Movie> unknownMovieParameters = new LinkedHashMap<Integer,Movie>();
		
		for (Map.Entry<Integer, Movie> movie: movieParameters.entrySet()) {
			if (knownMoviesIds.contains(movie.getValue().id)) knownMovieParameters.put(movie.getKey(), movie.getValue());
			else unknownMovieParameters.put(movie.getKey(), movie.getValue());
		}
		
		for (Map.Entry<Integer, Movie> movie: unknownMovieParameters.entrySet()) {
			Integer movieId = movie.getKey();
			ArrayList<Integer> neirestNeighbours = getNNForMovie(movie.getValue(), kNN, knownMovieParameters);
			movieNN.put(movieId, neirestNeighbours);
		}
		
		return movieNN;
	}
	
	private ArrayList<Integer> getNNForMovie(Movie targetMovie, int kNN, LinkedHashMap<Integer,Movie> knownMovieParameters) {
		ArrayList<Integer> neirestNeighbours = new ArrayList<Integer>();
		LinkedHashMap<Integer, Double> SimilarityValue = new LinkedHashMap<Integer, Double>();
		
		for (Movie movie: knownMovieParameters.values()) {
			if (movie.id == targetMovie.id) {
				continue;
			}
			
			Double similarity = getSimilarity(targetMovie, movie);
			SimilarityValue.put(movie.id, similarity);
		}
		
		while ((neirestNeighbours.size() < kNN) && (SimilarityValue.size() > 0)) {
			Double maxValueInMap=(Collections.max(SimilarityValue.values()));
			
			Iterator<Entry<Integer, Double>> it = SimilarityValue.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Integer, Double> pair = (Map.Entry<Integer, Double>)it.next();
		        if (pair.getValue() == maxValueInMap) {
		        	neirestNeighbours.add(pair.getKey());
		        	it.remove();
		        }
		    }
		}
		
		return neirestNeighbours;
	}
	
	private Double getSimilarity(Movie targetMovie, Movie movie) {
		Double similarity = 0.0;
		
		similarity += getSimilarityMinMax((double)targetMovie.budget, (double)movie.budget, budgetMax); // budget
		similarity += getSimilarityIntCollection(targetMovie.genres, movie.genres); // genres
		similarity += getSimilarityString(targetMovie.originalLanguage, movie.originalLanguage); // originalLanguage
		similarity += getSimilarityMinMax(targetMovie.popularity, movie.popularity, popularityMax); // popularity
		similarity += getSimilarityIntCollection(targetMovie.productionCompanies, movie.productionCompanies); // productionCompanies
		similarity += getSimilarityStringCollection(targetMovie.productionCountries, movie.productionCountries); // productionCountries
		similarity += getSimilarityMinMax((double)targetMovie.revenue, (double)movie.revenue, revenueMax); // revenue
		similarity += getSimilarityMinMax((double)targetMovie.runtime, (double)movie.runtime, runtimeMax); // runtime
		similarity += getSimilarityStringCollection(targetMovie.spokenLanguages, movie.spokenLanguages); // spokenLanguages
		similarity += getSimilarityMinMax(targetMovie.voteAverage, movie.voteAverage, voteAverageMax); // voteAverage
		
		return similarity;
	}
	
	private Double getSimilarityMinMax(Double targetValue, Double value, Double maxValue) {		
		return maxValue != 0? 1 - Math.abs(targetValue - value)/maxValue : 1;
	}
	
	private Double getSimilarityIntCollection(int[] targetCol, int[] col) {
		int size = targetCol.length;
		int correct = 0;
		for (int targetValue : targetCol) {
			for (int value : col) {
				if (targetValue == value) {
					correct++;
					break;
				}
			}
		}
		Double similarity = (double)correct/(double)size;
		return similarity;
	}
	
	private Double getSimilarityStringCollection(String[] targetCol, String[]col) {
		int size = targetCol.length;
		int correct = 0;
		for (String targetValue : targetCol) {
			for (String value : col) {
				if (targetValue.equals(value)) {
					correct++;
					break;
				}
			}
		}
		Double similarity = (double)correct/(double)size;
		return similarity;
	}
	
	private Double getSimilarityString(String targetValue, String value) {
		Double similarity = 0.0;
		if (targetValue.equals(value)) similarity = 1.0;
		return similarity;
	}
}
