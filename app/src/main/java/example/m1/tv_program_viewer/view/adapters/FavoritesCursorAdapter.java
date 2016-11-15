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
import static example.m1.tv_program_viewer.model.db.ContractClass.ChannelContract.CHANNEL_COLUMN_NAME_NAME;

/**
 * Created by M1 on 14.11.2016.
 */

public class FavoritesCursorAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public FavoritesCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View root = mInflater.inflate(R.layout.item_text, viewGroup, false);
        ViewHolder holder = new ViewHolder();
        holder.txtTitle = (TextView) root.findViewById(R.id.txt_title);
        root.setTag(holder);
        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder != null) {
            holder.txtTitle.setText(cursor.getString(cursor.getColumnIndex(CHANNEL_COLUMN_NAME_NAME)));
            holder.classID = cursor.getColumnIndex(_ID);
        }
    }

    public static class ViewHolder {
        public TextView txtTitle;
        public long classID;
    }
}
