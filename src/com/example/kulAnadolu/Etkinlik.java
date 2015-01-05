package com.example.kulAnadolu;

public class Etkinlik {
	
	public String name, location, organizer, type, date_start, date_end;

	public Etkinlik(String name,String location,String organizer,String date_start,String type,String date_end) 
	{
		this.name = name;
		this.location = location;
		this.organizer = organizer;
		this.type = type;
		this.date_end = date_end;
		this.date_start = date_start;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOrganizer() {
		return organizer;
	}

	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate_start() {
		return date_start;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

}
