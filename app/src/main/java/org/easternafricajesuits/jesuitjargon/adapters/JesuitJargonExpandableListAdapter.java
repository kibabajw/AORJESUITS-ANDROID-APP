package org.easternafricajesuits.jesuitjargon.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.easternafricajesuits.R;

import java.util.List;
import java.util.Map;

public class JesuitJargonExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupListTitles;
    private Map<String, List<String>> mapGroupWithChildren;

    public JesuitJargonExpandableListAdapter(Context context, List<String> groupListTitles, Map<String, List<String>> mapGroupWithChildren) {
        this.context = context;
        this.groupListTitles = groupListTitles;
        this.mapGroupWithChildren = mapGroupWithChildren;
    }

    @Override
    public int getGroupCount() {
        return mapGroupWithChildren.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapGroupWithChildren.get(groupListTitles.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupListTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapGroupWithChildren.get(groupListTitles.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String jargontitle = getGroup(groupPosition).toString();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jesuit_jargon_list_group, null);
        }
        TextView textViewtitle = convertView.findViewById(R.id.jjlistTitle);
        textViewtitle.setTypeface(null, Typeface.BOLD);
        textViewtitle.setText(jargontitle);
        textViewtitle.setPadding(40, 2, 2, 2);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String lsttitle = getChild(groupPosition, childPosition).toString();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.jesuit_jargon_list_item, null);
        }
        TextView textView = convertView.findViewById(R.id.jjexpandableListItem);
        textView.setText(lsttitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
