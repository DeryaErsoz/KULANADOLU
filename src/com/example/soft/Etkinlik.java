package com.example.soft;

public class Etkinlik {
	
	public String name, location, organizer, type, date_start, date_end;

	/**
	 * @param args
	 */
	public Etkinlik(String name,String location,String organizer,String date_start,String type,String date_end) 
	{
		this.name = name;
		this.location = location;
		this.organizer = organizer;
		this.type = type;
		this.date_end = date_end;
		this.date_start = date_start;
	}

}
