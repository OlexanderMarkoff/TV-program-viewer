package example.m1.tv_program_viewer.view.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import example.m1.tv_program_viewer.R;

import static android.provider.BaseColumns._ID;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TIME;
import static example.m1.tv_program_viewer.model.db.ContractClass.ProgramsContract.PROGRAMS_COLUMN_NAME_TITLE;

/**
 * Created by M1 on 14.11.2016.
 */

public class ProgramsCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public ProgramsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View root = mInflater.inflate(R.layout.item_program, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.txtTitle = (TextView) root.findViewById(R.id.txt_title);
        holder.txtTime = (TextView) root.findViewById(R.id.txt_time);
        root.setTag(holder);
        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null) {
            holder.txtTime.setText(cursor.getString(cursor.getColumnIndex(PROGRAMS_COLUMN_NAME_TIME)));
            holder.txtTitle.setText(cursor.getString(cursor.getColumnIndex(PROGRAMS_COLUMN_NAME_TITLE)));
            holder.classID = cursor.getColumnIndex(_ID);
        }
    }

    public static class ViewHolder {
        public TextView txtTime;
        public TextView txtTitle;
        public long classID;
    }
}
