package com.example.comicvine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String MOVIE_TABLE = "MOVIE_TABLE";
    public static final String COLUMN_MOVIE_NAME = "MOVIE_NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MOVIE_DESCRIPTION = "COLUMN_MOVIE_DESCRIPTION";
    public static final String COLUMN_MOVIE_RATING = "COLUMN_MOVIE_RATING";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "movie.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MOVIE_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY , " + COLUMN_MOVIE_NAME + " TEXT, " + COLUMN_MOVIE_DESCRIPTION + " TEXT,  " + COLUMN_MOVIE_RATING + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(MovieDBModel movieDBModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, movieDBModel.getId());
        cv.put(COLUMN_MOVIE_NAME, movieDBModel.getMovieName());
        cv.put(COLUMN_MOVIE_DESCRIPTION, movieDBModel.getMovieDescription());
        cv.put(COLUMN_MOVIE_RATING, movieDBModel.getMovieRating());
        long insert = db.insert(MOVIE_TABLE,null,cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean deleteOne(String movieId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + MOVIE_TABLE + " WHERE " + COLUMN_ID + " = " + movieId;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }

    public List<MovieDBModel> getAllFavourites(){
        List<MovieDBModel> moviesDB = new ArrayList<>();
        String queryAll="SELECT * FROM "+ MOVIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryAll,null);
        if(cursor.moveToFirst()){
            do{
                int movieID = cursor.getInt(0);
                String movieName = cursor.getString(1);
                String movieDescription = cursor.getString(2);
                String movieRating = cursor.getString(3);
                MovieDBModel newMovie = new MovieDBModel(movieID,movieName,movieDescription,movieRating);
                moviesDB.add(newMovie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return moviesDB;
    }


}
