package com.tutorial.collections;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public class DateExample {
	
	public static void main(String... arg)
	{
		LocalDate bday = LocalDate.of(1983, 11, 2);
		LocalDate today = LocalDate.now();
		int age = Period.between(bday, today).getYears();
		System.out.println(age);
		
		
	}

}
