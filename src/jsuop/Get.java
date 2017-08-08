package jsuop;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.common.collect.Sets.SetView;

public class Get {

	public ArrayList<ArrayList<String>> contacts(){
		SetView<String> dif	= difUrl();
		ArrayList<ArrayList<String>> wrapper = new ArrayList<ArrayList<String>>();
		Iterator<String> url = dif.iterator();
		
		while(url.hasNext()){
		
		try{
			ArrayList<String> contact = new ArrayList<String>();
			Document doc = Jsoup.connect("http://localhost:3000"+url.next()+"").get();
			Element h1 = doc.select("div.flex-container article.article h1").first();
			Elements contents = doc.select("article.article table.table.table-hover tbody tr td");
			String h = String.valueOf(h1.text());
			contact.add(h);
			
			for(Element title: contents){
				String text = title.text();
				contact.add(text);
			}
			wrapper.add(contact);
			System.out.println("get contacts");
		
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		return wrapper;
	}

	public SetView<String> difUrl(){
		HashSet<String> lastArray = lastArray();
		HashSet<String> nowArray = nowArray();
		SetView<String> difference = com.google.common.collect.Sets.difference(nowArray, lastArray);
		System.out.println("get difference.");
		return difference;
	}
	
	public HashSet<String> lastArray(){
		String lastId = lastId();
		//HashSet<String> nows = new HashSet<String>();
		HashSet<String> nows = new HashSet<String>();
		String Array;
		//String[] values;
		//HashSet<String> nows = null;

		Connection conn;
		Statement stmt;
		//데이터 베이스 접속		
		String url = "jdbc:mysql://localhost:3306/public";
		String username = "root";
		String password = "111311";

		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url,username,password);
			stmt = conn.createStatement();
			String sql ="select Array from urls where id ='"+lastId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Array = rs.getString("Array");
				//nows.add(Array);
				//nows = Array.split(",");
				String[] values = Array.split(",");
				nows = new HashSet<String>(Arrays.asList(values));
				
			}
			System.out.println("get last array.");
			
			
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
		
		return nows;
	}
	
	public HashSet<String> nowArray(){
		HashSet<String> titles = new HashSet<String>();
		//현재 온라인 제목들 가지고 오기
		try{
			Document doc = Jsoup.connect("http://localhost:3000").get();
			Elements contents = doc.select("nav.nav ul li a");
			for(Element title: contents){
				String text = title.attr("href");
				titles.add(text);
			}
			System.out.println("get now array.");
		}catch (IOException i){
			i.printStackTrace();
		}
		
		return titles;
	}
	
	public String lastId(){
		String lastid;
		Connection conn;
		Statement stmt;
		//id값을 담을 배열 선언
		ArrayList<String> lastId = new ArrayList<String>();;
		//데이터 베이스 접속		
		String url = "jdbc:mysql://localhost:3306/public";
		String username = "root";
		String password = "111311";

		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(url,username,password);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id from urls");
			int id;
			while(rs.next()){
				id = rs.getInt("id");
				String intid = String.valueOf(id);
				lastId.add(intid);
			}
			lastid = lastId.get(lastId.size()-1);
			System.out.println("get last Id.");
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		return lastid;
	}
	
	
}
