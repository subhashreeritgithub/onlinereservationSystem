package reservationsyste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Train {
	private static final int min=1000;
	private static final int max=9999;
	
	public static class user{
		private String username;
		private String password;
		
		
		Scanner sc= new Scanner(System.in);
		
		
		
		public String getUserName()
		{
			System.out.println("enter username:");
			username=sc.nextLine();
			return username;
			
		}
		public String getPassword() {
			System.out.println("enter the password:");
			password=sc.nextLine();
			return password;
			
		}
		public static class pnrRecord{
			private int pnrNumber;
			private String passengerName;
			private String trainNumber;
			private String classType;
			private String journeyDate;
			private String start;
			private String end;
		
			Scanner sc=new Scanner(System.in);
			public int getpnrNumber() 
			{
				Random r=new Random();
				pnrNumber=r.nextInt();
				return pnrNumber;

						
			}
			public String getPassengerName()
			{
				System.out.println("enter the passenger name:");
				passengerName=sc.nextLine();
				return passengerName;
			}
			
			public String getTrainNumaber()
			{
				System.out.println("enter the tarin numaber:");
				trainNumber=sc.nextLine();
				return trainNumber;
				
				
			}
			
			public String classType()
			{
				System.out.println("enter the classtype:");
				classType=sc.nextLine();
				return classType;
				
			}
		    
			public String getJourneyDate() {
				System.out.println("Enter the journey date as 'YYYY-MM-DD' fromat: ");
				journeyDate=sc.nextLine();
				return journeyDate;
			}
			
			
			public String getStart() {
				System.out.println("Enter the starting place: ");
				start=sc.nextLine();
				return start;
			}
			
			public String getend() {
				System.out.println("Enter the destination place: ");
			    end=sc.nextLine();
				return end;
			}
			
			
		}
		public static void main(String[]args) 
		{
			Scanner sc=new Scanner(System.in);
			user u1=new user();
			
			boolean q=true;
			String username=u1.getUserName();
			String password=u1.getPassword();
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				try(Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/trainpassengerdets", username, password))
				
				{
				System.out.println("user accessed");
				while(q)
				{
                  String InsertQuery="insert into train values(?,?,?,?,?,?,?)";
					
					String DeleteQuery="delete from train where pnrNumber=?";
					
					String ShowQuery="select * from train";
					
					String ShowpassenDetails="select * from passendetails where pnrNumber=?";
					
					System.out.println("Please Enter the choice : ");
					System.out.println("1.Insert Record\n2.Delete Record\n3.Show All Passenger Details\n4.Show Passenger details\n5.Exit The Application");
				
					int choice =sc.nextInt();
					
					switch(choice)
					{
					case 1:
						pnrRecord p1=new pnrRecord();
						
						int pnrNum=p1.getpnrNumber();
						String pname=p1.getPassengerName();
						String tnum=p1.getTrainNumaber();
						String classtype2=p1.classType();
						String jdate=p1.getJourneyDate();
						String startplace=p1.getStart();
						
				 String endplace=p1.getend(); 
				 
				 
				 
				 try(PreparedStatement ps1=con.prepareStatement(InsertQuery))
				 {
					 ps1.setInt(1, pnrNum);
						ps1.setString(2,pname);
						ps1.setString(3,tnum);
						ps1.setString(4,classtype2);
						ps1.setString(5,jdate);
						ps1.setString(6,startplace);
						ps1.setString(7,endplace);
						
						
						int changerowsintable=ps1.executeUpdate();
						if(changerowsintable>0)
						{
							System.out.println("Passenger Details added successfullyu");
						}
						else
						{
							System.out.println("No Details are added");
						}
						
						
						
				 }catch(SQLException e)
				 
				 {
					 System.out.println("SQLException: "+e.getMessage());
					 
				 }
				 break;
					case 2:
						System.out.println("Enter PNR Number to delete passenger details: ");
						int pnrnum=sc.nextInt();
						try(PreparedStatement ps2=con.prepareStatement(DeleteQuery))
						{
							ps2.setInt(1, pnrnum);
							
							int rowsaffect=ps2.executeUpdate();
							if(rowsaffect>0)
							{
								System.out.println("Passenger Details deleted successfully");
							}
							else
							{
								System.out.println("Passenger are not deleted from DataBase");
								
							}							
						}catch(SQLException e)
						{
							System.out.println("SQL Exception "+e.getMessage());
						}
					 
					break;
					case 3:
						try(PreparedStatement ps3=con.prepareStatement(ShowQuery))
						{
							ResultSet rs3=ps3.executeQuery();
							while(rs3.next())
							{
								int pnrNumb=rs3.getInt(1);
								String PasName=rs3.getString(2);
								String Trainname=rs3.getString(3);
								String classtyp=rs3.getString(4);
								String journeydat=rs3.getString(5);
								String started=rs3.getString(6);
								String ended=rs3.getString(7);
								
								System.out.print("PNR     Number: "+pnrNumb);
								System.out.print("Passe   Name: "+PasName);
								System.out.print("Train   Name: "+Trainname);
								System.out.print("Class   Type: "+classtyp);
								System.out.print("Journey Date: "+journeydat);
								System.out.print("From    Location: "+started);
								System.out.print("To Location: "+ended);
								
								System.out.println();
							}
							
						}catch(SQLException e)
						{
							System.out.println("SQL Exception "+e.getMessage());
							
						}
						break;
						
					case 4:
						System.out.println("Enter PNR Number to get passenger details: ");
						int pnrnum1=sc.nextInt();
						try(PreparedStatement ps4=con.prepareStatement(ShowpassenDetails))
						{
							ps4.setInt(1, pnrnum1);
							ResultSet rs4=ps4.executeQuery();
							rs4.next();
							int pnrNumb1=rs4.getInt(1);
							String PasName1=rs4.getString(2);
							String Trainname1=rs4.getString(3);
							String classtyp1=rs4.getString(4);
							String journeydat1=rs4.getString(5);
							String started1=rs4.getString(6);
							String ended1=rs4.getString(7);
							
							
							System.out.println("PNR     Number: "+pnrNumb1+"   ");
							System.out.println("Passe   Name:   "+PasName1+"   ");
							System.out.println("Train   Name:   "+Trainname1+"   ");
							System.out.println("Class   Type:   "+classtyp1+"   ");
							System.out.println("Journey Date:   "+journeydat1+"   ");
							System.out.println("From    Location:"+started1+"   ");
							System.out.println("To Location:     "+ended1);
						
								
						
						
				 } catch(SQLException e)
				 {
					 System.out.println("SQL Exception "+e.getMessage()); 
				 }
					break;
					case 5:
						
						 System.out.println("Exiting the program - Thank You");
						  q=false;
						  break;
						default:
							System.out.println("Invalid choice, Please Enter valid choice ");
							
					
					
					
				}
			}  
				
				
			
		}catch (SQLException e)
				{
			System.out.println("SQL Exception : "+e.getMessage());
				}
		
		
	}  catch(ClassNotFoundException e) {

		System.out.println("Error Loading JDBC driver : "+e.getMessage());
	}
			
			sc.close();
		}
	
	}
}
