public class Person implements Comparable<Person>
{
    String lastName, firstName, telephone, address, email;  //declare all the instance variables

    public Person(String lastName1, String firstName1, String telephone1, String address1, String email1)
    {  
        //set all the variable values in the constructor
        lastName = lastName1;
        firstName = firstName1;
        telephone = telephone1;
        address = address1;
        email = email1;
    }

    public String getLastName() //method to return the lastName of specified object
    {
        return lastName;
    }

    public String getFirstName()    //method to return the firstName of specified object
    {
        return firstName;
    }

    public String getTelephone()    //method to return the telephone of specified object
    {
        return telephone;
    }

    public String getAddress()  //method to return the address of specified object
    {
        return address;
    }

    public String getEmail()    //method to return the email of the specified object
    {
        return email;
    }

    public void setLastName(String lName)   //method to set the lastname based on parameters passed
    {
        lastName = lName;
    }

    public void setFirstName(String fName)  //method to set the firstname based on parameter passed
    {
        firstName = fName;
    }
    
    public void setTelephone(String tPhone) //method to set the telephone number based on parameter passed
    {
        telephone = tPhone;
    }

    public void setAddress(String a)    //method to set the address based on parameter passed
    {
        address = a;
    }

    public void setEmail(String e)  //method to set the email based on parameter passed
    {
        email = e;
    }

    public int compareTo(Person person) //compareTo method to compare the first and last name of two objects
    {
        if (this.lastName.compareTo(person.lastName) == 0)  //if the last name of the two objects are the same
        {
            return (this.firstName.compareTo(person.firstName));    //return the result of comparison between first names of two objects
        }
        else
        {
            return (this.lastName.compareTo(person.lastName));  //return the result of the comparison between last names of two objects -- will never equal 0.
        }
    }

    public int compareEmail(Person person)  //compare method to compare the email of two person objects
    {
        return (this.email.compareTo(person.email));    //return the comparison between emails of two objects
    }

    public int compareTelephone(Person person)  //compare method to compare the telephone of two person objects
    {
        return (this.telephone.compareTo(person.telephone));    //return the comparison between telephone numbers of two objects
    }

    public String toString()
    {
        return this.firstName + " " + this.lastName + " " + this.telephone + " " + this.email;
    }
}