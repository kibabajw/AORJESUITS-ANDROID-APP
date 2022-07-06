package org.easternafricajesuits.adusum.databases;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import org.easternafricajesuits.R;

public class AdusumCursorAdapter extends CursorAdapter {
    public AdusumCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adusum_brother_info_list_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView dataTextUsername = (TextView) view.findViewById(R.id.adusum_brother_info_list_item_info_data);

          int dataColumnIndex = cursor.getColumnIndex(AdusumDatabaseContract.BrotherEntry.COLUMN_BROTHER_USER_NAME);

          String brotherUsername = cursor.getString(dataColumnIndex);
          dataTextUsername.setText(brotherUsername);





    }
}
