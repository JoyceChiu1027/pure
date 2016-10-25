package com.bupt.chengde.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.ResponseCommonProblem;

/**
 * Created by wyf on 2015/12/17.
 * 常见问题列表
 */
public class ProblemAdapter extends BaseAdapter{

    private List<ResponseCommonProblem> mProblemList;
    private Context mContext;
    LayoutInflater inflater;

    public ProblemAdapter(List<ResponseCommonProblem> problemList, Context context) {
        super();
        this.mProblemList = problemList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mProblemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProblemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if (convertView == null){
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.problemlist_item,null);
            holder.problem = (TextView) convertView.findViewById(R.id.problem);
            holder.solution = (TextView) convertView.findViewById(R.id.solution);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }

        ResponseCommonProblem commonProblem = mProblemList.get(position);

   
        holder.problem.setText(commonProblem.getCommonProblem());
        holder.solution.setText(commonProblem.getCommonSolution());
        return convertView;
    }

    public static class viewHolder{
        private TextView problem;
        private TextView solution;
    }
}
