package com.faultcodeguide;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    String DB_PATH ="/data/data/ com.faultcodeguide/databases/";

    private static String DB_NAME = "FaultCodeGuide.db";
    public static final String KEY_ROWID = "_id";
    private SQLiteDatabase myDataBase; 
    private DataBaseHelper DBHelper;
    private SQLiteDatabase db;
    Cursor c=null;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

    	super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH="/data/data/"+context.getPackageName()+"/"+"databases/";
    }	

  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

    	boolean dbExist = checkDataBase();

    	if(dbExist){
    		//do nothing - database already exist
    		System.out.println("DATABASE EXISTING ");
    					
			
			
    	}else{
    		
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getWritableDatabase(); 

        	try {
        		System.out.println("COPY DATABASE CALLED ");
    			copyDataBase();
    			
    			
    			
    			
//    			myDataBase.rawQuery("UPDATE purchase_details SET expiry_date = '20-20-201' WHERE _id=1", null);
//    	    	long rowId=1;
//    	    	ContentValues args = new ContentValues();
//    	    	args.put("expiry_date", "20121021");
//    	    	args.put("purchase_date", "989898");
//    	    	args.put("duration_days", "7777");
//    			myDataBase.update("purchase_details", args,	KEY_ROWID + "=" + rowId, null);    	
   		} catch (IOException e) {

        		throw new Error("Error copying database");

        	}
    	} ///end of ellse
    	
    }///end of function copy database

/**
 * 
 */
public String getExpiryDate() {
	System.out.println("******CHECKING EXPIRY DATE");
	String sid="";
	
	SQLiteDatabase db = this.getReadableDatabase();
	
	c=db.query("purchase_details", null, null, null,null,null,null);
	
	
	if(c.moveToFirst())
	{
	 
		do {
				sid= c.getString(3);
				System.out.println("PURCHASE DETAILS " + sid);
//	  					  
	    } while (c.moveToNext());
	    

	}
	System.out.print("******CHECKING EXPIRY DATE END************");

	return sid;
}//end of method get expiry date


    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

    	SQLiteDatabase checkDB = null;

    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    	}catch(SQLiteException e){

    		//database does't exist yet.

    	}

    	if(checkDB != null){

    		checkDB.close();

    	}

    	return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);

    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;

    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);

    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}

    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();	

    }

    public void openDataBase() throws SQLException{

    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	
    	
    	//myDbHelper.rawQuery("UPDATE purchase_details SET expiry_date = '20-20-2012' WHERE _id='1'", null);
    	/*
    	
    	myDataBase.rawQuery("UPDATE purchase_details SET expiry_date = '20-20-201' WHERE _id=1", null);
    	long rowId=1;
    	ContentValues args = new ContentValues();
    	args.put("expiry_date", "20121021");
    	args.put("purchase_date", "989898");
    	args.put("duration_days", "7777");
    	myDataBase.update("purchase_details", args,	KEY_ROWID + "=" + rowId, null);    	
    	*/
    	
}

    @Override
	public synchronized void close() {

    	    if(myDataBase != null)
    		    myDataBase.close();

    	    super.close();
 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		String myPath = DB_PATH + DB_NAME;
myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    	    	
    }


}