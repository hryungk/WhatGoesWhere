package org.perscholas.whatgoeswhere.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Items")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
@NamedQuery(name="Item.findByName", query="SELECT i FROM Item i WHERE i.name = :givenName")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id; // Unique Item identifier
	@Column(name="name", length=50, nullable=false)
	private String name; // Item name
	@Column(name="state", length=50)
	private String condition; // Item's condition
	@Column(name="best_option", length=50, nullable=false)
	private String bestOption;
	@Column(name="special_instruction", length=100)
	private String specialInstruction; // Any special instruction to use the best option
	@Column(name="notes", length=200)
	private String notes; // Additional comments or notes
	@Column(name="added_date")
	private LocalDateTime addedDate; // The date the entry is added	
	
	public Item() {
		super();
	}
	public Item(String name, String condition, String bestOption,
			String specialInstruction, String notes, LocalDateTime addedDate) {
		super();
		this.name = name;
		this.condition = condition;
		this.bestOption = bestOption;
		this.specialInstruction = specialInstruction;
		this.notes = notes;
		this.addedDate = addedDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int itemId) {
		this.id = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getBestOption() {
		return bestOption;
	}

	public void setBestOption(String bestOption) {
		this.bestOption = bestOption;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDateTime getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDateTime addedDate) {
		this.addedDate = addedDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condition == null) ? 0 : condition.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (condition == null) {
			if (other.condition != null)
				return false;
		} else if (!condition.equals(other.condition))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
