package com.android.heyrecipes.RoomPersistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.heyrecipes.DataModals.RecipeModal;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDAO {
    @Insert(onConflict = IGNORE)
    long [] insertRecipes(RecipeModal...recipes);

    @Insert(onConflict = REPLACE)
    void insertRecipe(RecipeModal recipe);

    @Query("UPDATE recipes SET title=:title,publisher=:publisher,image_url=:image_url,social_rank=:social_rank " +
            "WHERE recipe_id=:recipe_id")
    void updateRecipe(String recipe_id,String title,
                                             String publisher,String image_url,float social_rank);

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query ||'%' OR ingredients LIKE '%'||:query||'%' " +
    "ORDER BY social_rank DESC LIMIT(:pageNumber*30)")
    LiveData<List<RecipeModal>> searchRecipes(String query ,int pageNumber);

    @Query("SELECT * FROM recipes WHERE recipe_id=:recipe_id")
    LiveData<RecipeModal> getRecipe(String recipe_id);

}
