package com.tutorial.collections;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonExample {
	
	public static class Person {

	    public enum Sex {
	        MALE, FEMALE
	    }

	    String name;
	    LocalDate birthday;
	    Sex gender;
	    String emailAddress;
	    
	    // ...
	    
	    

	    public int getAge() {
	    	LocalDate today = LocalDate.now();
			int age = Period.between(this.birthday, today).getYears();
			return age;
	    }

	    public Person(String name, LocalDate birthday, Sex gender,
				String emailAddress) {
			super();
			this.name = name;
			this.birthday = birthday;
			this.gender = gender;
			this.emailAddress = emailAddress;
		}

		public String getName() {
	        return this.name;
	    }

		public Sex getGender() {
			return gender;
		}

		public void setGender(Sex gender) {
			this.gender = gender;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public void setName(String name) {
			this.name = name;
		}
	    
	    
	}
	
	
	
	public static void main(String... arg)
	{
		PersonExample example = new PersonExample();
		
		Person p = new Person("Dhanil",LocalDate.of(1981, 12, 13),Person.Sex.MALE,"");
		Person p2 = new Person("Dona",LocalDate.of(1983, 12, 13),Person.Sex.FEMALE,"");
		Person p3 = new Person("Rihaan",LocalDate.of(2010, 4, 6),Person.Sex.MALE,"");
		Person p4 = new Person("Joe",LocalDate.of(2010, 1,22),Person.Sex.MALE,"");
		Person p5 = new Person("Malu",LocalDate.of(2011, 12, 20),Person.Sex.FEMALE,"");
		Person p6 = new Person("Minnah",LocalDate.of(2010, 9, 27),Person.Sex.FEMALE,"");
		
		List<Person> roster = new ArrayList<>();
		roster.add(p);roster.add(p2);roster.add(p3);roster.add(p4);roster.add(p5);roster.add(p6);
		
		roster.stream()
		.filter(person -> person.getGender()==Person.Sex.MALE)
		.forEach(filteredPerson -> System.out.println(filteredPerson.getName()));
		
		roster.stream()
		.filter(person->person.getAge()>10)
		.forEach(person -> 
		{
			int i=person.getAge();
			System.out.println("Age selected: "+i);
		});
		
		System.out.println("No name is null: "+roster.stream()
		.allMatch(person -> person.name!=null));
		
		/*
		 * The following will classify Person objects by city: 

     Map<String, List<Person>> peopleByCity
         = personStream.collect(Collectors.groupingBy(Person::getCity));
 

The following will classify Person objects by state and city, cascading two Collectors together: 

     Map<String, Map<String, List<Person>>> peopleByStateAndCity
         = personStream.collect(Collectors.groupingBy(Person::getState,
                                                      Collectors.groupingBy(Person::getCity)));


		 */
//		Map<Person.Sex, List<Person>> mapByGender = roster.stream()
//											.collect(Collectors.groupingBy(Person::getGender));
		
//		1. Return a map with key = gender,  value: comma separated list of names
		System.out.println("Sorting people by gender using collect");
		Map<Person.Sex,String> mapByGender = 
				roster
				.stream()
				.collect(GenderClassifier::new,GenderClassifier::accept,GenderClassifier::combine)
				.getMap();
		mapByGender.entrySet().forEach(entry -> System.out.println(entry.getKey()+":"+entry.getValue()));
		
		
		
		
		Map<Integer,List<Person>> mapByAge = roster.stream()
				.collect(Collectors.groupingBy(Person::getAge));
		System.out.println(mapByAge.toString());
		
//		mapByAge.forEach(System.out.println("Printing mapByAge:"));
		System.out.println("Printing map By Age");
		mapByAge.entrySet().forEach(entry -> System.out.println(entry.getKey()+","+
		((List<Person>)entry.getValue())
		.stream()
		.map(Person :: getName)
		.reduce("",(name1,name2)->
		{
			if(name1=="")
			{
				return (name2);
			}
			else
				return name1+","+name2;	
		}
	)));
		
		
				
		
		List<Person> eldersList = roster.stream()
				.filter(person->person.getAge()>10)
				.collect(Collectors.toList());
		
		eldersList.forEach(person->System.out.println("Printing elders List: "+person.getName()+","+person.getAge()));
		int total = eldersList.stream().map(Person::getAge).reduce(0,(a,b)->a+b);
		System.out.println("Totalled using map.reduce: "+total);
		
		Integer totalAge = roster.stream()
				.filter(person -> person.getGender()==Person.Sex.MALE)
				.mapToInt(Person::getAge)
				.sum();
		
		String concatNames = roster.stream()
				.map(Person::getName)
				.reduce("",(a,b)->a+b);
		System.out.println("concatNames= "+(concatNames));
		
		System.out.println("totalAge="+totalAge);
	}

}
