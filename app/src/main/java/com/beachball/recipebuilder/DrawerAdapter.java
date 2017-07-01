package com.beachball.recipebuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Peter Emo on 30/06/2017.
 */

public class DrawerAdapter extends BaseAdapter {
    private int[] drawables;
    private LayoutInflater inflater;
    private String[] values;
    private Context context;

    public DrawerAdapter(String[] values, int[] drawables, LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.values = values;
        this.drawables = drawables;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.values.length;
    }

    @Override
    public Object getItem(int position) {
        return Integer.valueOf(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = this.inflater.inflate(R.layout.nav_row, null);
        ImageView icon = (ImageView) vi.findViewById(R.id.nav_icon);
        ((TextView) vi.findViewById(R.id.nav_label)).setText(this.values[position]);
        icon.setImageDrawable(context.getResources().getDrawable(this.drawables[position]));
        return vi;
    }
}
