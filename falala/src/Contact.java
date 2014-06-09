//********************************************************************
//  Contact.java       Java Foundations
//
//  Represents a phone contact that implements Comparable.
//********************************************************************

public class Contact implements Comparable
{
   private String firstName, lastName, phone;

   //-----------------------------------------------------------------
   //  Sets up this contact with the specified information.
   //-----------------------------------------------------------------
   public Contact (String first, String last, String telephone)
   {
      firstName = first;
      lastName = last;
      phone = telephone;
   }

   //-----------------------------------------------------------------
   //  Returns a string representation of this contact.
   //-----------------------------------------------------------------
   public String toString ()
   {
      return lastName + ", " + firstName + ":  " + phone;
   }

   //-----------------------------------------------------------------
   //  Uses both last and first names to determine lexical ordering.
   //-----------------------------------------------------------------
   public int compareTo (Object other)
   {
      int result;

      if (lastName.equals(((Contact)other).lastName))
         result = firstName.compareTo(((Contact)other).firstName);
      else
         result = lastName.compareTo(((Contact)other).lastName);

      return result;
   }
}
