import java.util.ArrayList;

public class MovieCollectionRunner
{
    public static void main(String arg[])
    {
        MovieCollection myCollection = new MovieCollection("MovieData/movies_data.csv");
        myCollection.menu();
    }
}