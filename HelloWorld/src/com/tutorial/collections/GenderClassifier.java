package com.tutorial.collections;

import java.util.HashMap;
import java.util.Map;


import com.tutorial.collections.PersonExample.Person;

public class GenderClassifier
{
	private Map<Person.Sex,String> map = new HashMap<PersonExample.Person.Sex, String>();
	
	public Map<Person.Sex,String> getMap()
	{
		return map;
	}
	
	
	
	/*
	 * Accept person object and set map according to it.
	 */
	public  void accept(Person p)
	{
		if(map.get(p.getGender())!=null)
		{
			map.put(p.getGender(),map.get(p.getGender())+","+p.getName());
		}
		else
			map.put(p.getGender(), p.getName());
		
	}
	
	/*
	 * Combine existing values of map with the map in the new genderclassifier obect
	 */
	public  void combine(GenderClassifier element)
	{

	}
}