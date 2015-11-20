package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Optional;

import models.Movie;
import models.User;
import utils.Serializer;

public class MovieRecommenderAPI 
{
	private Serializer serializer;

	private Map<Long,User>   userIndex = new HashMap<>();
	public Map<Long, Movie> movieIndex = new HashMap<>();

	public MovieRecommenderAPI()
	{}

	public MovieRecommenderAPI(Serializer serializer)
	{
		this.serializer = serializer;
	}
	@SuppressWarnings("unchecked")
	public void load() throws Exception
	{
		serializer.read();
		userIndex = (Map<Long, User>)     serializer.pop();
	}

	public void store() throws Exception
	{
		serializer.push(userIndex);
		serializer.write(); 
	}

	public Collection<User> getUsers ()
	{
		return userIndex.values();
	}

	public  void deleteUsers() 
	{
		userIndex.clear();
	}

	public User createFileUser(Long id,String firstName, String lastName, int age, String gender, String occupation)
	{
		User user = new User(id,firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		return user;
	}
	

	public User createUser(String firstName, String lastName, int age, String gender, String occupation)
	{
		User user = new User (firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		return user;
	}


	public User getUser(Long id) 
	{
		return userIndex.get(id);
	}

	public void deleteUser(Long id) 
	{
		User user = userIndex.remove(id);
	}

	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		return movie;
	}
}

