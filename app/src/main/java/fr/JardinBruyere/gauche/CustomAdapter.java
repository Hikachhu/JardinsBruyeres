package fr.JardinBruyere.gauche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import fr.JardinBruyere.gauche.database.sensor.Sensor;

import java.util.ArrayList;

/**
 * Created by hardik on 9/1/17.
 */
public class CustomAdapter  extends BaseAdapter {

    private Context context;
    public static ArrayList<Sensor> modelArrayList;
    public static ArrayList<Integer> list;

    public CustomAdapter(Context context, ArrayList<Sensor> modelArrayList) {

        this.context = context;
        this.modelArrayList = modelArrayList;
        this.list= new ArrayList<>();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvAnimal = (TextView) convertView.findViewById(R.id.animal);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }


        holder.checkBox.setText("ID= "+ modelArrayList.get(position).getId().toString());
        holder.tvAnimal.setText(modelArrayList.get(position).getName());

        holder.checkBox.setChecked(false);

        holder.checkBox.setTag(R.integer.btnplusview, convertView);
        holder.checkBox.setTag( position);
        holder.checkBox.setOnClickListener(v -> {
            if(!list.contains(modelArrayList.get(position).getId())) list.add(modelArrayList.get(position).getId());
            else list.remove(modelArrayList.get(position).getId());

        });

        return convertView;
    }

    private static class ViewHolder {

        protected CheckBox checkBox;
        private TextView tvAnimal;

    }

}