package ch.heig.cashflow.adapters;

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
import ch.heig.cashflow.models.Category;

public class CategorySelectAdapter extends ArrayAdapter<Category> {
    private static final String TAG = "CategorySelectAdapter";

    List<Category> myList;

    public CategorySelectAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        myList = categories;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_select_listitem, null);
        }

        Category category = getItem(position);
        ImageView iconImage = v.findViewById(R.id.catSelectIcon);
        TextView title = v.findViewById(R.id.catSelectName);
        ImageView etatImage = v.findViewById(R.id.catSelectActiveIcon);

        int iconImageId = this.getDrawableResIdByName(category.getIconName());
        iconImage.setImageResource(iconImageId);

        title.setText(category.getName());

        int enabledImageID, colorEnabled, colorIcon;

        if (category.isEnabled()) {
            enabledImageID = this.getDrawableResIdByName("cat_button_remove");
            colorEnabled = Color.RED;
            colorIcon = Color.GREEN;
        } else {
            enabledImageID = this.getDrawableResIdByName("cat_button_add");
            colorEnabled = Color.GREEN;
            colorIcon = Color.RED;
        }

        etatImage.setImageResource(enabledImageID);
        etatImage.getDrawable().setTint(colorEnabled);
        iconImage.getDrawable().setTint(colorIcon);

        return v;
    }

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public int getDrawableResIdByName(String resName) {
        String pkgName = getContext().getPackageName();
        // Return 0 if not found.
        return getContext().getResources().getIdentifier(resName, "drawable", pkgName);
    }

    public void refreshList(List<Category> newList) {
        this.myList.clear();
        this.myList.addAll(newList);
        notifyDataSetChanged();
    }
}