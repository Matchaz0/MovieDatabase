import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<SimpleMovie> movies = MovieDatabaseBuilder.getMovieDB("MovieData/movie_data");
        for (SimpleMovie movie : movies) {
            System.out.println(movie);
        }



        SimpleMovie movie1 = movies.get(0);
        System.out.println();
        System.out.println(movie1.getTitle());
        System.out.println(movie1.getActors());
        System.out.println();
        System.out.println("Number of movies: " + movies.size());

        ArrayList<SimpleMovie> bacon = new ArrayList<SimpleMovie>();
        ArrayList<String> actorsBacon1 = new ArrayList<String>();
        for (SimpleMovie movie : movies) {
            if (movie.getActors().contains("Kevin Bacon")) {
                bacon.add(movie);
            }
        }
        System.out.println(bacon.size());
    }
}