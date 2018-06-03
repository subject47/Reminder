package com.example.reminder.forms;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class DateForm {

	private List<String> years;
	private Map<Integer, String> months;
	private int year;
	private int month;
	
	public DateForm() {
		years = Lists.newArrayList("2018", "2019", "2020", "2021", "2022");
//		months = Lists.newArrayList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
//				"Nov", "Dec");
		months = new HashMap<>();
		months.put(Month.JANUARY.getValue(), "January");
		months.put(Month.FEBRUARY.getValue(), "February");
		months.put(Month.MARCH.getValue(), "March");
		months.put(Month.APRIL.getValue(), "April");
		months.put(Month.MAY.getValue(), "May");
		months.put(Month.JUNE.getValue(), "June");
		months.put(Month.JULY.getValue(), "July");
		months.put(Month.AUGUST.getValue(), "August");
		months.put(Month.SEPTEMBER.getValue(), "September");
		months.put(Month.OCTOBER.getValue(), "October");
		months.put(Month.NOVEMBER.getValue(), "November");
		months.put(Month.DECEMBER.getValue(), "December");
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public Map<Integer, String> getMonths() {
		return months;
	}

	public void setMonths(Map<Integer, String> months) {
		this.months = months;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

}
