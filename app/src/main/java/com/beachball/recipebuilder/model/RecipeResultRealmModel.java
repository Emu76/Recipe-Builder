package com.beachball.recipebuilder.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Peter Emo on 02/07/2017.
 */

public class RecipeResultRealmModel extends RealmObject {

    public RecipeResultRealmModel() {
        results = new RealmList<>();
    }

    public String id;
    public String name;
    public RealmList<RecipeResult> results;

    public RealmList<RecipeResult> getResults() {
        return results;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResults(RecipeResult[] results) {
        this.results.clear();
        for(int i=0; i<results.length; i++) {
            this.results.add(results[i]);
        }
    }
}
