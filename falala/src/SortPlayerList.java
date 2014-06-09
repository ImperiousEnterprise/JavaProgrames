//********************************************************************
//  SortPlayerList.java       Java Foundations
//
//  Demonstrates a selection sort of Comparable objects.
//********************************************************************

public class SortPlayerList
{
   //-----------------------------------------------------------------
   //  Creates an array of Contact objects, sorts them, then prints
   //  them.
   //-----------------------------------------------------------------
   public static void main (String[] args)
   {
      Contact[] players = new Contact[7];

      players[0] = new Contact ("Rodger", "Federer", "610-555-7384");
      players[1] = new Contact ("Andy", "Roddick", "215-555-3827");
      players[2] = new Contact ("Maria", "Sharapova", "733-555-2969");
      players[3] = new Contact ("Venus", "Williams", "663-555-3984");
      players[4] = new Contact ("Lleyton", "Hewitt", "464-555-3489");
      players[5] = new Contact ("Eleni", "Daniilidou", "322-555-2284");
      players[6] = new Contact ("Serena", "Williams", "243-555-2837");
      int list [] ={2,1,8,6,10};
      Sorting.mergeSort(list);
      Sorting.selectionSort(players);

      for (Comparable player : players)
         System.out.println (player);
   }
}
