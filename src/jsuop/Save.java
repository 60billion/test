package jsuop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

public class Save{

	public void saveContacts(ArrayList<ArrayList<String>> con){
		//ArrayList<ArrayList<String>> con = contacts();
		for(int i = 0; i<con.size(); i++){
			
			Connection conn;
			PreparedStatement ps;
			String url = "jdbc:mysql://localhost:3306/public";
			String username = "root";
			String password = "111311";
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try{
				String sql = "insert into temp (title,name,email,phone,contents) values(?,?,?,?,?)";
				conn = DriverManager.getConnection(url,username,password);
				ps = (PreparedStatement) conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, con.get(i).get(0).toString());
				ps.setString(2, con.get(i).get(1).toString());
				ps.setString(3, con.get(i).get(2).toString());
				ps.setString(4, con.get(i).get(3).toString());
				ps.setString(5, con.get(i).get(4).toString());
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				int insert = 0;
				if(rs.next()){
					insert=rs.getInt(1);
					System.out.println("SaveContacts.");
				}
				String a = String.valueOf(insert);
				con.get(i).add(a);
				
			}catch (SQLException e) {
			    throw new IllegalStateException("Cannot connect the database!", e);
			}
			}
	}

	public void saveNowArray(HashSet<String> nowArray){
		//HashSet<String> nowArray = nowArray();
		Connection conn;
		PreparedStatement ps;
		String url = "jdbc:mysql://localhost:3306/public";
		String username = "root";
		String password = "111311";
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String com ="";
		StringBuilder up = new StringBuilder();
		for(String g:nowArray){
			up.append(com);
			up.append(g);
			com=",";
		}
		
		try{
			String sql = "insert into urls (Array) values(?)";
			conn = DriverManager.getConnection(url,username,password);
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, up.toString());
			ps.executeUpdate();
			
		}catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		System.out.println("Save last Array");
	}
}
