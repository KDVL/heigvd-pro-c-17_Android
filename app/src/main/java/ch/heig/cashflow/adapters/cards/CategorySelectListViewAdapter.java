package ch.heig.cashflow.adapters.cards;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.utils.ApplicationResources;

/**
 * La classe adaptateur pour gérer l'affichage des catégories lors du choix client
 */
public class CategorySelectListViewAdapter extends ArrayAdapter<String> {
    private ApplicationResources appRes;
    private Context context;

    /**
     * Constructeur
     *
     * @param context    contexte de l'application
     * @param resource   layout ressource
     * @param categories la liste de categories à afficher
     */
    public CategorySelectListViewAdapter(Context context, int resource, List<String> categories) {
        super(context, resource, categories);
        this.context = context;
        appRes = new ApplicationResources(context);
    }

    /**
     * Affichage personalisé pour chaque élément de la vue
     *
     * @param position    position de l'élément
     * @param convertView
     * @param parent
     * @return la vue
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_select_list_item, null);
        }
        String c = getItem(position);
        ImageView iconImage = v.findViewById(R.id.cat_select_image_view);
        TextView title = v.findViewById(R.id.cat_select_title);

        int iconImageId = appRes.getDrawableResIdByName(c);
        iconImage.setImageResource(iconImageId);
        iconImage.getDrawable().setTint(Color.parseColor("#000000")); // TODO: color

        title.setText(c.substring(4));

        return v;
    }
}
