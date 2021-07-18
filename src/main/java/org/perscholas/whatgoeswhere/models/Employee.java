package org.perscholas.whatgoeswhere.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="employees")
@NamedQuery(name = "findAll", query = "SELECT e from Employee e")
@NamedQuery(name = "find_Employee_By_ID", query = "SELECT e FROM Employee e WHERE e.employeeNumber = :givenID")
public class Employee {

	@Id
	int employeeNumber;
	String firstName;
	String lastName;
	String email;
	int officeCode;
	
	public Employee() {
		super();
	}
	
	public Employee(int employeeNumber, String firstName, String lastName, String email, int officeCode) {
		super();
		this.employeeNumber = employeeNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.officeCode = officeCode;
	}
	
	public int getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(int officeCode) {
		this.officeCode = officeCode;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [employeeNumber=" + employeeNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", officeCode=" + officeCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + employeeNumber;
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
		Employee other = (Employee) obj;
		if (employeeNumber != other.employeeNumber)
			return false;
		return true;
	}
	
	
	
}
