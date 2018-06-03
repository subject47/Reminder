package com.example.reminder.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Plan extends AbstractDomainClass {

	@OneToOne
	private User user;
	private Double amountPlanned;
	private Double amountSpent;
	private Date startDate;
	private Date endDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getAmountPlanned() {
		return amountPlanned;
	}

	public void setAmountPlanned(Double amountPlanned) {
		this.amountPlanned = amountPlanned;
	}

	public Double getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(Double amountSpent) {
		this.amountSpent = amountSpent;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
