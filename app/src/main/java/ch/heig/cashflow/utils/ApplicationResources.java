package ch.heig.cashflow.utils;

import android.content.Context;

/**
 * La classe qui nous fourni les éléments de l'application
 */
public class ApplicationResources {
    private Context context;

    public ApplicationResources(Context context) {
        this.context = context;
    }

    /**
     * Cherche le id de l'élément dans le dossier drawable
     *
     * @param resName Le nom de l'élément
     * @return le id de l'élément
     */
    public int getDrawableResIdByName(String resName) {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        return context.getResources().getIdentifier(resName, "drawable", pkgName);
    }
}
