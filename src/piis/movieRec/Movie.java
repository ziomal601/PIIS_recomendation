package piis.movieRec;

import java.util.Map;

import org.json.simple.JSONArray;

import static java.lang.Math.toIntExact;

public class Movie {
	public static final String idKey = "id";
	public static final String budgetKey = "budget";
	public static final String genresKey = "genres";
	public static final String originalLanguageKey = "original_language";
	public static final String popularityKey = "popularity";
	public static final String productionCompaniesKey = "production_companies";
	public static final String productionCountriesKey = "production_countries";
	public static final String productionCountriesISOKey = "iso_3166_1";
	public static final String revenueKey = "revenue";
	public static final String runtimeKey = "runtime";
	public static final String spokenLanguagesKey = "spoken_languages";
	public static final String spokenLanguagesISOKey = "iso_639_1";
	public static final String voteAverageKey = "vote_average";
	
	int id;
	long budget;
	int[] genres;
	String originalLanguage;
	Double popularity;
	int[] productionCompanies;
	String[] productionCountries;
	long revenue;
	long runtime;
	String[] spokenLanguages;
	Double voteAverage;
	
	Movie(Map json) {
		id = toIntExact((long) json.get(idKey));
		budget = (long) json.get(budgetKey);
		
		JSONArray genresJSONArray = (JSONArray) json.get(genresKey);
		genres = new int[genresJSONArray.size()];
		for (int i = 0; i < genresJSONArray.size(); i++) {
			Map genresMap = (Map) genresJSONArray.get(i);
			genres[i] = toIntExact((long)genresMap.get(idKey));
		}
		
		originalLanguage = (String) json.get(originalLanguageKey);
		popularity = (Double) json.get(popularityKey);
		
		JSONArray productionCompaniesJSONArray = (JSONArray) json.get(productionCompaniesKey);
		productionCompanies = new int[productionCompaniesJSONArray.size()];
		for (int i = 0; i < productionCompaniesJSONArray.size(); i++) {
			Map productionCompaniesMap = (Map) productionCompaniesJSONArray.get(i);
			productionCompanies[i] = toIntExact((long)productionCompaniesMap.get(idKey));
		}
		
		JSONArray productionCountriesJSONArray = (JSONArray) json.get(productionCountriesKey);
		productionCountries = new String[productionCountriesJSONArray.size()];
		for (int i = 0; i < productionCountriesJSONArray.size(); i++) {
			Map productionCountriesMap = (Map) productionCountriesJSONArray.get(i);
			productionCountries[i] = (String) productionCountriesMap.get(productionCountriesISOKey);
		}
		
		revenue = (long) json.get(revenueKey);
		runtime = (long) json.get(runtimeKey);
		
		JSONArray spokenLanguagesJSONArray = (JSONArray) json.get(spokenLanguagesKey);
		 spokenLanguages = new String[spokenLanguagesJSONArray.size()];
		for (int i = 0; i < spokenLanguagesJSONArray.size(); i++) {
			Map  spokenLanguagesMap = (Map) spokenLanguagesJSONArray.get(i);
			 spokenLanguages[i] = (String)  spokenLanguagesMap.get(spokenLanguagesISOKey);
		}
		
		voteAverage = (Double) json.get(voteAverageKey);
	}
	
	public String toString() {
		String returnString = idKey + ": " + id + "\n";
		returnString += budgetKey + ": " + budget + "\n";
		returnString += genresKey + ": {";
		for (int i: genres) {
			returnString += i + ",";
		}
		returnString += "}\n";
		returnString += originalLanguageKey + ": " + originalLanguage + "\n";
		returnString += popularityKey + ": " + popularity + "\n";
		returnString += productionCompaniesKey + ": {";
		for (int i: productionCompanies) {
			returnString += i + ",";
		}
		returnString += "}\n";
		returnString += productionCountriesKey + ": {";
		for (String s: productionCountries) {
			returnString += s + ",";
		}
		returnString += "}\n";
		returnString += revenueKey + ": " + revenue + "\n";
		returnString += runtimeKey + ": " + runtime + "\n";
		returnString += spokenLanguagesKey + ": {";
		for (String s: spokenLanguages) {
			returnString += s + ",";
		}
		returnString += "}\n";
		returnString += voteAverageKey + ": " + voteAverage + "\n";
		return returnString;
	}
}
