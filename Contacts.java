//import all necessary libraries
import java.util.*;
import java.io.*;
public class Contacts
{
    LinkedList<Person> personList;  //create LinkedList object
    ListIterator<Person> iterator;  //create a listiterator for personList
    public Contacts()
    {
        personList = new LinkedList<Person>();  //initialize personList
        iterator = personList.listIterator();
    }

    public Person findPersonEmail(String email2) //method to find person object based on email.
    {
        for (int i = 0; i < personList.size(); i++) //traverse the personList
        {
            if (iterator.next().email.equals(email2))  //if email property of the next object equals the email specified,
            {
                return personList.get(i);   //return the appropriate person object
            }
        }

        return null;    //if none found, return null
    }

    public Person findPersonLastName(String lastName1)   //method to find person based on lastname
    {
        for (int i = 0; i < personList.size(); i++) //traverse the personList
        {
            if (iterator.next().lastName.equals(lastName1)) //if lastName property of the next object equals the lastname specified
            {
                return personList.get(i);   //return the appropriate person object
            }
        }
        return null;    //if none found, return null
    }

    public Person findPersonFirstName(String firstName1) //method to find person based on firstname
    {
        for (int i = 0; i < personList.size(); i++) //traverse the personList
        {
            if (iterator.next().firstName.equals(firstName1))    //if firstName property of the next iterated object equals the firstname specified
            {
                return personList.get(i);   //return the appropriate person object
            }
        }
        return null;    //if none found, return null
    }

    public Person findPersonTel(String tel1) //method to find person based on telephone number
    {
        for (int i = 0; i < personList.size(); i++) //traverse the personList
        {
            if (iterator.next().telephone.equals(tel1))  //if the telephone property of the next iterated object equals the telephone specified
            {
                return personList.get(i);   //return the appropriate person object
            }
        }
        return null;    //return null
    }
    
    public int size()   //method to return the size of personList
    {
        return personList.size();
    }

    public LinkedList<Person> getList() //method to return the personList LinkedList
    {
        return personList;
    }

    public void readContactsFile() throws IOException
    {
        String firstName = "";
        String lastName = "";
        String email = "";
        String tel = "";
        String address = "";
        int numOfPersons, count, numOfLines;

        numOfLines = 0;
        Scanner scanner = new Scanner(new File("contacts.txt"));



        count = 0;

        while (scanner.hasNextLine())
        {
            numOfLines++;
            if (count == 0)
            {
                if (scanner.hasNextInt() == false)
                {
                    //System.out.println("DONE");
                    throw new IOException();
                }
                else
                {
                    numOfPersons = Integer.parseInt(scanner.nextLine());
                    count++;
                }
            }
            else if (count == 1)
            {
                String temp = scanner.nextLine();
                if (temp.equals(""))
                {
                    throw new IOException();
                }
                else
                {
                    lastName = temp;
                    count++;
                }
            }
            else if (count == 2)
            {
                String temp = scanner.nextLine();
                if (temp.equals(""))
                {
                    throw new IOException();
                }
                else{
                    firstName = temp;
                    count++;
                }
            }
            else if (count == 3)
            {
                String temp = scanner.nextLine();
                if (temp.equals(""))
                {
                    throw new IOException();
                }
                else
                {
                    address = temp;
                    count++;
                }
            }
            else if (count == 4)
            {
                String temp = scanner.nextLine();
                if (temp.equals(""))
                {
                    throw new IOException();
                }
                else
                {
                    tel = temp;
                    count++;
                }
            }
            else if (count == 5)
            {
                String temp = scanner.nextLine();
                if (temp.equals(""))
                {
                    throw new IOException();
                }
                else
                {
                    email = temp;

                    iterator.add(new Person(lastName, firstName, tel, address, email));
                    count = 1;
                }
                
            }

            for (int i = 0; i < personList.size(); i++)
            {
                System.out.println(personList.get(i));
            }
        }

        if ((numOfLines - 1)%5 != 0)
        {
            throw new IOException();
        }
    }
}