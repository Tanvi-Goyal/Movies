package com.tmovies.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tmovies.model.Movie;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String IMDB_BASE_URL = "https://www.imdb.com/title/";

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MovieDetails";
    private static final String TABLE_NAME = "movies";
    private static final String KEY_MOVIE_ID = "movie_id";
    private static final String KEY_MOVIE_IMDB_ID = "movie_imdb_id";
    private static final String KEY_MOVIE_TITLE = "movie_title";
    private static final String KEY_YEAR = "year";
    private static final String KEY_RATING = "rating";
    private static final String KEY_POSTER_URL = "poster_url";
    private static final String KEY_DETAIL_URL = "detail_url";
    private static final String SQL_DELETE_MOVIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SQL_CREATE_MOVIES =
            "CREATE TABLE movies (" + KEY_MOVIE_ID + "  INTEGER PRIMARY KEY, "
                    + KEY_MOVIE_IMDB_ID + "  TEXT, " + KEY_MOVIE_TITLE + " TEXT, "
                    + KEY_YEAR + "  INTEGER, " + KEY_RATING + "  REAL, "
                    + KEY_POSTER_URL + "  TEXT, " + KEY_DETAIL_URL + "  TEXT );";


    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public SQLiteDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MOVIES);
        this.onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onUpgrade(db, oldVersion, newVersion);
    }

    public void clear() {

    }

    public long addMovie(Movie movie) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        //Create a map having movie details to be inserted
        ContentValues movie_details = new ContentValues();
        movie_details.put(KEY_MOVIE_ID, movie.getId());
        movie_details.put(KEY_MOVIE_IMDB_ID, movie.getImdb_id());
        movie_details.put(KEY_MOVIE_TITLE, movie.getTitle());
        movie_details.put(KEY_YEAR, movie.getReleaseDate());
        movie_details.put(KEY_RATING, movie.getRating());
        movie_details.put(KEY_POSTER_URL, movie.getPosterPath());
        movie_details.put(KEY_DETAIL_URL, IMDB_BASE_URL + movie.getImdb_id());


        long newRowId = db.insert(TABLE_NAME, null, movie_details);
        db.close();
        return newRowId;

    }

    public Movie getMovie(int movie_id) {

        Movie movieDetails = new Movie();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {KEY_MOVIE_ID, KEY_MOVIE_IMDB_ID, KEY_MOVIE_TITLE, KEY_YEAR, KEY_RATING, KEY_POSTER_URL, KEY_DETAIL_URL};
        //Select condition
        String selection = KEY_MOVIE_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(movie_id)};


        Cursor cursor = db.query(TABLE_NAME, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            movieDetails.setId(cursor.getInt(0));
            movieDetails.setImdb_id(cursor.getString(1));
            movieDetails.setTitle(cursor.getString(2));
            movieDetails.setReleaseDate(cursor.getString(3));
            movieDetails.setRating(cursor.getFloat(4));
            movieDetails.setPosterPath(cursor.getString(5));
            movieDetails.setDetail_url(cursor.getString(6));

        }
        db.close();
        return movieDetails;

    }
}
