package com.tweetTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TweetApp {
	
	static final String D_URL="jdbc:mysql://localhost:3306/tweetdemo";
	static final String User="root";
	static final String Pass="pass@word1";
	static final String QueryReg="insert into reg (name,email,password) values(?,?,?)";
	static final String QueryLogin="select * from reg where email=? AND password=?;";
	static final String QueryForgotPwd="update reg set password=? where email=?";
	static final String QueryPostTweet="insert into tweet (email,tweets) values(?,?);";
	static final String QueryViewMyTweets="select tweets from tweet where email=?;";
	static final String QueryViewAllTweets="select tweets from tweet;";
	static final String QueryViewAllUser="select email from tweet;";
	static final String QueryResetPwd="update reg set password=? where email=? AND password=?;";
	

	public void registration() throws Exception {
		
		try(Connection con=DriverManager.getConnection(D_URL,User,Pass);
				Statement st=con.createStatement();
				PreparedStatement ps=con.prepareStatement(QueryReg);){
			//System.out.println("Connection Succesfully");
			System.out.println("Enter Registration Details");
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter first name");
			String name=sc.nextLine();
			System.out.println("Enter email");
			String email=sc.nextLine();
			System.out.println("Enter password");
			String pas=sc.nextLine();
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, pas);
			ps.executeUpdate();
			
		}
		catch(Exception e) {
			System.out.println("Exception encountered at forget password ::"+e.getMessage());
			throw new Exception("Exception"+e);

		}
		
	}
	
	public void login() throws Exception {
		
		try(Connection con=DriverManager.getConnection(D_URL,User,Pass);
				Statement st=con.createStatement();
				PreparedStatement ps=con.prepareStatement(QueryLogin);){
			Scanner sc=new Scanner(System.in);
			System.out.println("Enter Email");
			String email=sc.nextLine();
			System.out.println("Enter passowrd");
			String pass=sc.nextLine();
			ps.setString(1, email);
			ps.setString(2, pass);
			ResultSet rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("Login Success");
				while(true) {
				System.out.println("Enter 1.Post tweets  2.View my tweets  "
						+ "3.View all tweets  4.View all Users  5.Reset Password  6.Logout");
				Scanner sc1=new Scanner(System.in);
				int num=sc1.nextInt();
				if(num==1) {
					PreparedStatement ps1=con.prepareStatement(QueryPostTweet);
					Scanner sc2=new Scanner(System.in);
					System.out.println("Enter or Post a tweet here");
					String tweetPost=sc2.nextLine();
					ps1.setString(1,email);
					ps1.setString(2, tweetPost);
					ps1.executeUpdate();
					System.out.println("Tweet Posted Succesfully");
					
	
				}
				if(num==2) {
					System.out.println("View my tweets");
					PreparedStatement ps1=con.prepareStatement(QueryViewMyTweets);
					ps1.setString(1,email);
					//Scanner sc3=new Scanner(System.in);
					rs=ps1.executeQuery();
					while(rs.next()) {
						String twt=rs.getString("tweets");
						System.out.println(twt);
					}
					
				}
				if(num==3) {
					System.out.println("View all tweets");
					PreparedStatement ps2=con.prepareStatement("select tweets from tweet;");
					rs=ps2.executeQuery();
					System.out.println("Tweets");
					while(rs.next()) {
						String twt=rs.getString("tweets");
						System.out.println(twt);
					}
				}
				if(num==4) {
					System.out.println("View all tweets");
					PreparedStatement ps3=con.prepareStatement("select email from tweet;");
					rs=ps3.executeQuery();
					System.out.println("All User Email");
					while(rs.next()) {
						String userEmail=rs.getString("email");
						System.out.println(userEmail);
					}
				}
				if(num==5) {
					PreparedStatement ps4=con.prepareStatement(QueryResetPwd);
					
					System.out.println("Reset Password");
					Scanner sc3=new Scanner(System.in);
					System.out.println("Enter Old Password");
					String oPass=sc3.nextLine();
					System.out.println("Enter New Password");
					String nPass=sc3.nextLine();
					System.out.println("Enter confirm password");
					String cPass=sc3.nextLine();
					if(nPass.equals(cPass)) {
						ps4.setString(1,nPass);	
					}else {
						System.out.println("Password is not same");
						break;
					}
					ps4.setString(2,email);
					ps4.setString(3, oPass);
				  int rowCount=ps4.executeUpdate();
					if(rowCount>0) {
						System.out.println("Updated Succesfully");
						break;
					}else {
						System.out.println("Old Password is Incorrect");
						break;
					}
					
				}
				if(num==6) {
					System.out.println("Logout Succesfully");
					con.close();
					break;
				}
				}
				
				}else {
					System.out.println("login fail");
				}
			
			} catch (SQLException e) {
				System.out.println("Exception encountered at reset password ::"+e.getMessage());
				throw new Exception("Exception"+e);

			}
	}
	public void forgetPassword() throws Exception {
		try(Connection con=DriverManager.getConnection(D_URL,User,Pass);
				Statement st=con.createStatement();
				//PreparedStatement ps4=con.prepareStatement("select email from reg;");
		PreparedStatement ps5=con.prepareStatement(QueryForgotPwd);){
		
		//	ResultSet rs=ps4.executeQuery();
			System.out.println("Enter email");
			Scanner sc3=new Scanner(System.in);
			String email=sc3.nextLine();
			ps5.setString(2,email);

		System.out.println("Enter New Password");
		String nPass=sc3.nextLine();
		System.out.println("Enter confirm password");
		String cPass=sc3.nextLine();
		if(nPass.equals(cPass)) {
			ps5.setString(1,nPass);	
		}else {
			System.out.println("Password is not same");
		}
	  int rowCount=ps5.executeUpdate();
		if(rowCount>0) {
			System.out.println("Updated Succesfully");
		}else {
			System.out.println("mail id is not found");
		}
		}
		catch(Exception e) {
			System.out.println("Exception encountered at forget password ::"+e.getMessage());
			throw new Exception("Exception"+e);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		TweetApp tApp=new TweetApp();
		while(true) {
			System.out.println("Welcome to twitter App");
			System.out.println("Enter 1.Registration  2.Login  3.Forgot Password");
			Scanner sc=new Scanner(System.in);
			int num=sc.nextInt();
			if(num==1) {
				tApp.registration();
			}
			if(num==2) {
				tApp.login();
			}
			if(num==3) {
				tApp.forgetPassword();
			}
		}

		
	}

}
