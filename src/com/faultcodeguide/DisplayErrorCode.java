package com.faultcodeguide;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class DisplayErrorCode extends Activity{
	protected SQLiteDatabase db;
	protected Cursor cursor;


	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setTitle("Possible Error Codes");
    	
    	db = (new DataBaseHelper(this)).getReadableDatabase();
		Intent intent = getIntent();
		System.out.println("**********************************************");
		
		String brand_id_string=intent.getExtras().getString("errorcode_brand_id");
		String error_code_id_string=intent.getExtras().getString("error_code_id");

		int error_code_id_int=Integer.parseInt(error_code_id_string);
		
		System.out.println("Printing the brand_id_string :"+brand_id_string);
		
		System.out.println("I am in DisplayErrorCode");
 
		
		System.out.println("Printing the error_code_id");
		System.out.println(error_code_id_int);
		System.out.println("**********************************************");
		
		String table_name="brand_" + brand_id_string+ "_errorcode";
		String select_errorcodes="SELECT  display_panel_code,number_of_leds,led_code,summary,description,possible_cause,possible_solution,remarks  FROM " +table_name +" WHERE _id='"+error_code_id_string+"'";
		cursor = db.rawQuery(select_errorcodes,null);
		
		 
	  	if (cursor.moveToFirst())
	  	{
		do {
			String dispaly_panel_code= cursor.getString(0);
			String number_of_leds= cursor.getString(1);
			String led_code= cursor.getString(2);
			String summary= cursor.getString(3);
			String description= cursor.getString(4);
			String possible_cause= cursor.getString(5);
			String possible_solution= cursor.getString(6);
			String remarks= cursor.getString(7);
			
				System.out.println("dispaly_panel_code are : " + dispaly_panel_code);
				System.out.println("number_of_leds are :" + number_of_leds);
				System.out.println("led_code are : " + led_code);
				System.out.println("summary are : " + summary);
				System.out.println("description are : " + description);
				System.out.println("possible_cause are : " + possible_cause);
				System.out.println("possible_solution are : " + possible_solution);
				System.out.println("remarks are :" +remarks);
				
			
		}while (cursor.moveToNext());
 
	  	}
	  	}
	  	
}