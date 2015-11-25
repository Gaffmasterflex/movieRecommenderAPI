package models;
import static org.junit.Assert.*;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;
import static models.Fixtures.users;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import controllers.Data;
import controllers.MovieRecommenderAPI;
public class MovieTest 
{

	Movie movie = new Movie ("deansMovie", "1996","www.dean.com");
	MovieRecommenderAPI movieRecommender;
	@Test
	public void testCreate()
	{
		assertEquals ("deansMovie",                movie.title);
		assertEquals ("1996",             		   movie.year);
		assertEquals ("www.dean.com",              movie.url);   
	}

	@Test
	public void testIds()
	{
		//test id size
		Set<Long> ids = new HashSet<>();
		for (Movie movie:movies)
		{
			ids.add(movie.id);
		}
		assertEquals (movies.length, ids.size());
		//test that each objects id witch each other to ensure they are the same
		for(int i = 0; i<movies.length;i++)
		{
			assertEquals(movies[i].id,movies[i].id);
		}
	}

	@Test
	public void testToString()
	{
		assertEquals(movie.toString(),movie.toString());
	}


	@Test
	public void testEquals()
	{
		Movie movie2 = new Movie ("deansMovie", "1996","www.dean.com"); 
		Movie movie3  = new Movie ("algorithmsMovie", "2015","www.algorithms.com"); 

		assertEquals(movie, movie);
		assertEquals(movie, movie2);
		assertNotEquals(movie, movie3);
	} 

	@Test
	public void getMovieById() throws Exception
	{
		File usersFile = new File("testdatastore.xml");
		Serializer serializer = new XMLSerializer(usersFile);
		MovieRecommenderAPI movieRecommender = new MovieRecommenderAPI(serializer);
		Data data = new Data();

		List<Movie> movies = data.importMovies("data/items5.dat");
		for(Movie movie : movies)
		{
			movieRecommender.addMovie(movie);
		}
		movieRecommender.store();

		//loads movieRecommender2 with the new data and tests their equality
		MovieRecommenderAPI movieRecommender2 =  new MovieRecommenderAPI(serializer);
		movieRecommender2.load();

		/*the for loop will go through each user in movieRecommender
		 * and will get the id's of each user. It will then test the movieRecommender
		 * id's against the id's of movieRecommender2 assuring that the getUser(id) function
		 * is working correctly.
		 */
		for (Movie movie : movieRecommender.getMovies())
		{
			assertEquals(movie.id,movieRecommender2.getMovie(movie.id).id);
		}
	}
}



