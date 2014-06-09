
import java.util.Scanner;
public class test 
{
	private static int row;
	private static int col; 
	public static void main(String args[])
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the number of rows (2-20): ");
		row = keyboard.nextInt();
		col = keyboard.nextInt();
		String[][] arr = new String[row][col];
		
		for(int i=0;i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				if(i==0)
				{
					arr[i][j]="*";
				}
				else if(i == row-1)
				{
					arr[i][j]="*";
				}
				else if(j == 0)
				{
					arr[i][j]="*";
				}
				else if(j == col-1)
				{
					arr[i][j]="*";
				}
				else
				{
					arr[i][j]=" ";
				}
				
			}
			
		}
		
		
		
		for(int i=0;i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		
		
		
	}

}
