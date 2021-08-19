package com.hyunryungkim.whatgoeswhere.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

/**
 * A class representing an item
 * Stores item id, name, condition, BestOption (enum), special instruction, notes, and added date.
 * 
 * @author Hyunryung Kim
 * @see com.hyunryungkim.whatgoeswhere.models.BestOption
 *
 */
@Entity
@Table(name="Items", uniqueConstraints = { @UniqueConstraint(name = "UniqueNameAndState", columnNames = { "name", "state" }) })
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
@NamedQuery(name="Item.findByName", query="SELECT i FROM Item i WHERE i.name = ?1")
@NamedQuery(name="Item.findByNameAndState", query="SELECT i FROM Item i WHERE i.name = ?1 AND i.condition = ?2")
public class Item {
	/**
	 * An integer representing the item id, auto-generated
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id; // Unique Item identifier
	/**
	 * A string containing this item's name
	 */
	@Column(name="name", length=50, nullable=false)
	@NotEmpty(message="Item name is required.")
	private String name; // Item name
	/**
	 * A string containing this item's condition
	 */
	@Column(name="state", length=50) // "condition" is a reserved keyword in MariaDB
	private String condition; // Item's condition
	/**
	 * An enum containing this item's best disposing option
	 */
	@Column(length=50, nullable=false)
	@NotEmpty(message="Best Option is required.")
	private BestOption bestOption;
	/**
	 * A string containing special instructions for disposing the item
	 */
	@Column(name="special_instruction", length=100)
	private String specialInstruction; // Any special instruction to use the best option
	/**
	 * A string containing additional notes
	 */
	@Column(name="notes", length=200)
	private String notes; // Additional comments or notes
	/**
	 * A date and time on which this item is added 
	 */
	@Column(name="added_date", nullable=false)
	private LocalDateTime addedDate; // The date the entry is added	
	
	/**
	 * Class constructor initializing all class fields
	 */
	public Item() {
		super();
		this.name = "";
		this.condition = "";
		this.bestOption = null;
		this.specialInstruction = "";
		this.notes = "";
		this.addedDate = null;
	}
	/**
	 * Class constructor accepting fields
	 * 
	 * @param name a string of this item's name
	 * @param condition a string of this item's condition
	 * @param bestOption an enum whose value contains this item's best disposing option 
	 * @param specialInstruction  a string of special instruction for this item 
	 * @param notes  a string of notes for this item
	 * @param addedDate a LocalDateTime object containing this item's added date and time
	 */
	public Item(String name, String condition, BestOption bestOption,
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

	public BestOption getBestOption() {
		return bestOption;
	}

	public void setBestOption(BestOption bestOption) {
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
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", condition=" + condition + ", bestOption=" + bestOption
				+ ", specialInstruction=" + specialInstruction + ", notes=" + notes + ", addedDate=" + addedDate + "]";
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
		} else if (!condition.equalsIgnoreCase(other.condition))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}
	
}
