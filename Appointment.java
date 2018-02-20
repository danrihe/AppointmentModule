//Danri He
//Student Number 500765982

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Appointment implements Comparable<Appointment>
{
    //create instance variables
    Calendar date; 
    String description;
    int year, month, day, hour, minute;

    public Appointment(int year1, int month1, int day1, int hour1, int minute1, String description1) //constructor
    {
        date = new GregorianCalendar(year1, month1, day1, hour1, minute1);
        description = description1;
        year = year1;
        month = month1;
        day = day1;
        hour = hour1;
        minute = minute1;
    }

    public Appointment()    //constructor with no parameters
    {
        date = Calendar.getInstance();
    }

    public String getDescription()  //returns the description
    {
        return description;
    }

    public void setDescription(String newDescription)   //set the description
    {
        description = newDescription;
    }

    public String getDate() //returns the date in sdf format specified
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd YYYY");
        return sdf.format(date.getTime());
    }

    public void setDate(int year, int month, int day, int hour, int minute) //sets that date with passed parameters
    {
        date = new GregorianCalendar(year, month, day, hour, minute);
    }

    public String print()   //prints the time and description
    {
        SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
        return (hm.format(date.getTime()) + " " + description);
    }

    public boolean occursOn(int year, int month, int day, int hour, int minute) //checks if there is an occupied date
    {
        Calendar tempCalendar = new GregorianCalendar(year, month, day, hour, minute);
    
        if ((this.date.compareTo(tempCalendar)) == 0)
        {
            return true;
        }
        else
        {
            return false;
        }   
    }

    public int compareTo(Appointment appointment)   //compareTo method
    {
        return this.date.compareTo(appointment.date); //if this comes before appointment 1, vice versa -1, if equal 0
    }

    public boolean compareDate(Calendar other)  //compareDate method to determine if two dates are the same
    {
        if (date.get(Calendar.YEAR) == other.get(Calendar.YEAR))
        {
            if (date.get(Calendar.MONTH) == other.get(Calendar.MONTH))
            {
                if (date.get(Calendar.DAY_OF_MONTH) == other.get(Calendar.DAY_OF_MONTH))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public Calendar calendarDate()  //return the date
    {
        return date;
    }
}