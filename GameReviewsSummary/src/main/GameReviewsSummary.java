import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameReviewsSummary {

  private static class GenreSummary {
    private int reviews;
    private Map<String, Integer> scorePhrases = new HashMap<>();
    private List<Double> scores = new ArrayList<>();
    private Double highestScore = null;
    private Double lowestScore = null;
    private String bestGame = null;
    private String worstGame = null;
  }

  public static void main(String[] args) throws IOException {
    Map<String, GenreSummary> summaries = new HashMap<>();
    Map<String, Integer> nintendoGenres = new HashMap<>();

    List<String> headers;

    String filePath = "game-reviews.csv";

    if (args != null && args.length > 0) {
      filePath = args[0];
    }

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line = br.readLine();
      String[] str = line.split(";");

      headers = Arrays.asList(str);

      while ((line = br.readLine()) != null) {
        try {
          str = line.split(";");
          String game = str[headers.indexOf("title")];
          String genre = str[headers.indexOf("genre")];
          String scorePhrase = str[headers.indexOf("score_phrase")];
          String platform = str[headers.indexOf("platform")];
          Double score = Double.parseDouble(str[headers.indexOf("score")]);

          // genre
          GenreSummary summary = summaries.get(genre);

          if (summary == null) {
            summaries.put(genre, summary = new GenreSummary());
          }

          summary.reviews++;

          // score_phrase
          Integer phraseCount = summary.scorePhrases.get(scorePhrase);

          if (phraseCount == null) {
            phraseCount = 0;
          }

          summary.scorePhrases.put(scorePhrase, ++phraseCount);

          // score
          summary.scores.add(score);

          // best game
          if (summary.highestScore == null || summary.highestScore < score) {
            summary.highestScore = score;
            summary.bestGame = game;
          }

          // worst game
          if (summary.lowestScore == null || summary.lowestScore > score) {
            summary.lowestScore = score;
            summary.worstGame = game;
          }

          // nintendo genres
          if (platform.contains("Nintendo")) {
            Integer genreCount = nintendoGenres.get(genre);

            if (genreCount == null) {
              genreCount = 0;
            }

            nintendoGenres.put(genre, ++genreCount);
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }

    for (Map.Entry<String, GenreSummary> entry: summaries.entrySet()) {
      try {
        GenreSummary summary = entry.getValue();

        double amazingPercent = 0.0;
        double average = 0.0;
        double standardDeviation = 0.0;

        if (summary.scorePhrases.containsKey("Amazing")) {
          amazingPercent = (summary.scorePhrases.get("Amazing") * 100.0) / summary.reviews;
        }

        for (Double score: summary.scores) {
          average += score;
        }

        average /= summary.reviews;

        for (Double score: summary.scores) {
          standardDeviation += Math.pow(score - average, 2);
        }

        standardDeviation = Math.sqrt(standardDeviation / summary.reviews);

        System.out.println("Gênero: " + entry.getKey());
        System.out.println("Número de reviews deste gênero: " + summary.reviews);
        System.out.printf("Percentual de 'amazing' reviews: %.2f%%\n", amazingPercent);
        System.out.printf("Média aritmética dos scores: %.5f\n", average);
        System.out.printf("Desvio padrão populacional dos scores: %.5f\n", standardDeviation);
        System.out.println("Melhor jogo: " + summary.bestGame);
        System.out.println("Pior jogo: " + summary.worstGame);
        System.out.println("\n--------------------------------------------------\n");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    Integer maxGenre = null;
    String nintendoGenre = "";

    for (Map.Entry<String, Integer> genreEntry : nintendoGenres.entrySet()) {
      if (maxGenre == null || maxGenre < genreEntry.getValue()) {
        maxGenre = genreEntry.getValue();
        nintendoGenre = genreEntry.getKey();
      }
    }

    System.out.println("O gênero de jogo mais comum na família de consoles 'Nintendo' é: " + nintendoGenre + "(" + maxGenre + " jogos)");
  }
}
