package com.android.heyrecipes.RoomPersistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.android.heyrecipes.Constants.Utils.Converters;
import com.android.heyrecipes.DataModals.RecipeModal;

@Database(entities ={RecipeModal.class},version = 1)
@TypeConverters({Converters.class})
public abstract class  RecipeDatabase extends RoomDatabase {

    public static final String DATABASE_NAME="recipes_db";
    public static RecipeDatabase instance;

    public static RecipeDatabase getInstance(final Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    RecipeDatabase.class,DATABASE_NAME ).build();
        }
        return instance;
    }

    public abstract RecipeDAO getRecipeDAO();
}
