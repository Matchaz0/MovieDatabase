import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class MovieCollection
{
    // instance variables
    private ArrayList<Movie> movies;
    private ArrayList<String> uniqueCast;
    private ArrayList<String> uniqueGenres;
    private ArrayList<Movie> top50Rated;
    private ArrayList<Movie> top50Grossing;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        // init required lists
        setUniqueCastMembers();
        setUniqueGenres();
        top50Rated = setHighestCategory(top50Rated, "rating", 50);
        top50Grossing = setHighestCategory(top50Grossing, "revenue", 50);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t")) {
            searchTitles();
        }
        else if (option.equals("c")) {
            searchCast();
        }
        else if (option.equals("k")) {
            searchKeywords();
        }
        else if (option.equals("g")) {
            listGenres();
        }
        else if (option.equals("r")) {
            listTop50(top50Rated);
        }
        else if (option.equals("h")) {
            listTop50(top50Grossing);
        }
        else {
            System.out.println("Invalid choice!");
        }
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }



    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine().toLowerCase(); // prevent case sensitivity


        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++) {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        askForMovieChoice(results, true);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

        private void searchCast()
    {
        System.out.print("Enter a cast member search term: ");
        String searchTerm = scanner.nextLine().toLowerCase(); // prevent case sensitivity

        // arraylist to hold search results
        ArrayList<String> results = new ArrayList<String>();

        // search through ALL CAST in collection
        for (int i = 0; i < uniqueCast.size(); i++)
        {
            String castMember = uniqueCast.get(i);
            if (castMember.toLowerCase().contains(searchTerm)) {
                results.add(castMember);
            }
        }
        // sort the results by alphabet
        Collections.sort(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String cast = results.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + cast);
        }

        System.out.println("Which cast would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedCast = results.get(choice - 1);
        ArrayList<Movie> resultMovies = getMoviesOfACategory("cast", selectedCast);

        // This method called alot
        askForMovieChoice(resultMovies, true);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private ArrayList<Movie> getMoviesOfACategory(String category, String value) {
        ArrayList<Movie> resultMovies = new ArrayList<Movie>();
        for (Movie movie : movies) {
            if (category.equals("title") && movie.getTitle().contains(value)) {
                resultMovies.add(movie);
            }
            if (category.equals("keyword") && movie.getKeywords().contains(value)) {
                resultMovies.add(movie);
            }
            if (category.equals("cast") && movie.getCast().contains(value)) {
                resultMovies.add(movie);
            }
            if (category.equals("genre") && movie.getGenres().contains(value)) {
                resultMovies.add(movie);
            }


        }
        return resultMovies;
    }

    private void askForMovieChoice(ArrayList<Movie> movies, boolean sort) {
        if (sort) {
            sortResults(movies);
        }
        for (int i = 0; i < movies.size(); i++) {
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movies.get(i).getTitle());
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        displayMovieInfo(movies.get(choice - 1));
    }


    // Sets the array list of uniqueCast
    private void setUniqueCastMembers() {
        ArrayList<String> allCast = new ArrayList<String>();
        ArrayList<String> checkedCast = new ArrayList<String>();
        ArrayList<String> uniqueCast = new ArrayList<String>();

        // Get all actors
        for (int i = 0; i < movies.size(); i++) {
            String[] tempArray = movies.get(i).getCast().split("\\|");
            ArrayList<String> tempArray2 = new ArrayList<String>(Arrays.asList(tempArray));
            allCast.addAll(tempArray2);
        }
        // For every actor, if found ignore, if not add to checked cast and unique cast
        for (int i = 0; i < allCast.size(); i++) {
            String tempActor = allCast.get(i);
            if (!checkedCast.contains(allCast.get(i))) {
                uniqueCast.add(tempActor);
                checkedCast.add(tempActor);
            }
        }
        // do the setting
        Collections.sort(uniqueCast);
        this.uniqueCast = uniqueCast;
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine().toLowerCase(); // prevent case sensitivity
        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String keywords = movies.get(i).getKeywords();
            if (keywords.contains(searchTerm))
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }



    private void listGenres()
    {
        // Show all genres
        for (int i = 0; i < uniqueGenres.size(); i++)
        {
            String genre = uniqueGenres.get(i);
            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.println("Which Genre would you like to learn more about?");
        System.out.print("Enter number: ");
        searchGenre();

    }

    private void searchGenre() {
        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = uniqueGenres.get(choice - 1);
        ArrayList<Movie> resultMovies = getMoviesOfACategory("genre", selectedGenre);

        // This method called alot
        askForMovieChoice(resultMovies, true);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void setUniqueGenres() {
        ArrayList<String> allGenre = new ArrayList<String>();
        ArrayList<String> checkedGenre = new ArrayList<String>();
        ArrayList<String> uniqueGenre = new ArrayList<String>();

        // Get all genre
        for (int i = 0; i < movies.size(); i++) {
            String[] tempArray = movies.get(i).getGenres().split("\\|");
            ArrayList<String> tempArray2 = new ArrayList<String>(Arrays.asList(tempArray));
            allGenre.addAll(tempArray2);
        }
        // For every actor, if found ignore, if not add to checked cast and unique cast
        for (int i = 0; i < allGenre.size(); i++) {
            String tempActor = allGenre.get(i);
            if (!checkedGenre.contains(allGenre.get(i))) {
                uniqueGenre.add(tempActor);
                checkedGenre.add(tempActor);
            }
        }
        // do the setting
        Collections.sort(uniqueGenre);
        this.uniqueGenres = uniqueGenre;
    }

    private void listTop50(ArrayList<Movie> top50List) {
        // now, display them all to the user

        askForMovieChoice(top50List, false);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private ArrayList<Movie> setHighestCategory(ArrayList<Movie> bestCategoryList, String category, int howMany) {
        ArrayList<Movie> tempMovies = new ArrayList<Movie>(movies);
        ArrayList<Movie> highestRatedMovies = new ArrayList<Movie>();
        for (int i = 0; i < howMany; i++) {
            Movie bestMovie = findGreatest(tempMovies, category);
            highestRatedMovies.add(bestMovie);
            tempMovies.remove(bestMovie);
        }
        return highestRatedMovies;

    }

    private Movie findGreatest(ArrayList<Movie> movies, String category) {
        // find the highest score
        Movie highestRated = null;
        double highestScore = 0;
        for (Movie movie : movies) {
            double valueToCheck = 0;
            if (category.equals("rating")) { // ratings
                valueToCheck = movie.getUserRating();
            }
            else if (category.equals("revenue")) { // revenue
                valueToCheck = movie.getRevenue();
            }
            if (valueToCheck > highestScore) {
                highestScore = valueToCheck;
            }
        }

        // depending on which, find the greasest
        if (category.equals("rating")) { // ratings
            ArrayList<Double> tempScore = new ArrayList<Double>();
            for (Movie movie : movies) {
                tempScore.add(movie.getUserRating());
            }
            int indexOfHighestScore = tempScore.indexOf(highestScore);
            highestRated = movies.get(indexOfHighestScore);
        }
        else if (category.equals("revenue")) { // revenue
            ArrayList<Integer> tempScore = new ArrayList<Integer>();
            for (Movie movie : movies) {
                tempScore.add(movie.getRevenue());
            }
            int indexOfHighestScore = tempScore.indexOf((int) highestScore);
            highestRated = movies.get(indexOfHighestScore);
        }
        return highestRated;
    }


    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}