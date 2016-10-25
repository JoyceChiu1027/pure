package com.bupt.chengde.ui.se;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.adapter.BianminAdapter;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.entity.ResponseBenPeo;
import com.bupt.chengde.ui.BaseFragment;
import com.bupt.chengde.util.LogUtil;
import com.bupt.chengde.util.WebserviceUtils;
import com.bupt.chengde.widget.CustomFrameLayout;
import com.bupt.chengde.widget.CustomProgressDialog;
import com.bupt.chengde.widget.XListView;
import com.bupt.chengde.widget.XListView.IXListViewListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class BianminFragment extends BaseFragment implements
        IXListViewListener, OnItemClickListener {
    private static final String TAG = BianminFragment.class.getSimpleName();
    CustomProgressDialog pd;
    private XListView listView;
    private CustomFrameLayout customFrameLayout;
    private View refresh;// 刷新按钮
    private TextView errorTxt;
    private BianminAdapter adapter;
    private List<ResponseBenPeo> returnList = new ArrayList<ResponseBenPeo>();
    private String moduleId;
    private int type;
    private BianminTask task = null;
    private int page = 1;
    private boolean isRefresh = false;
    //public BianminFragment(int type) {
    //this.type=type;
//}

     static BianminFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(CodeConstants.TYPE, type);
        BianminFragment f = new BianminFragment();
        f.setArguments(args);
        return f ;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.type=getArguments().getInt(CodeConstants.TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comm, null, false);
        listView = (XListView) view.findViewById(R.id.comm_listview);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        customFrameLayout = (CustomFrameLayout) view
                .findViewById(R.id.web_frame);
        customFrameLayout.setList(new int[]{R.id.comm_listview,
                R.id.common_net_error});
        refresh = view.findViewById(R.id.error_btn);
        errorTxt = (TextView) view.findViewById(R.id.error_txt);

        refresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                customFrameLayout.show(R.id.comm_listview);
                pd.show();
                pd.setCancelable(false);
                com.bupt.chengde.util.LogUtil.d(TAG, "refresh onclick时page=" + page);
                new BianminTask().execute(type, page);
            }
        });
        pd = CustomProgressDialog.createDialog(getActivity());
        pd.setMessage("正在请求数据");
        task = new BianminTask();
        if (type == 1) {
            pd.show();
        }
        task.execute(type, page);
        // getData(type);
        // adapter = new BianminAdapter(activity, returnList);
        // listView.setAdapter(adapter);
        listView.setOnItemClickListener(BianminFragment.this);
        return view;
    }


class BianminTask extends AsyncTask<Integer, Void, List<ResponseBenPeo>> {
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    @Override
    protected List<ResponseBenPeo> doInBackground(Integer... params) {
        // TODO Auto-generated method stub
        try {
            return WebserviceUtils.getBenInfo(params[0], params[1]);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<ResponseBenPeo> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        pd.dismiss();
        listView.stopRefresh();
        listView.stopLoadMore();

        if (result != null) {
            if (result.size() > 0) {
                if (isRefresh) {
                    returnList.clear();
                }
                returnList.addAll(result);
                if (adapter == null) {
                    LogUtil.d(TAG,
                            "onPostExecute is called and adapter==null");
                    adapter = new BianminAdapter(getActivity(), returnList);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } else {
                if (page > 1) {
                    page--;
                    if (isAdded())
                        activity.application.showToast(getResources().getString(R.string.server_no_more));

                } else {
                    if (!isRefresh) {
                        customFrameLayout.show(R.id.common_net_error);
                        refresh.setVisibility(View.VISIBLE);
                        if (isAdded())
                            errorTxt.setText(getResources().getString(R.string.server_no_data));
                    }
                }
            }
        } else {
            if (page > 1) {
                page--;
                if (isAdded())
                    activity.application.showToast(getResources().getString(R.string.server_time_out));

            } else {
                if (!isRefresh) {
                    customFrameLayout.show(R.id.common_net_error);
                    refresh.setVisibility(View.VISIBLE);
                    if (isAdded())
                        errorTxt.setText(getResources().getString(R.string.server_time_out));
                }
            }
        }

    }

}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO Auto-generated method stub
        com.bupt.chengde.util.LogUtil.d(TAG, "onItemClick is called");
        Intent intent = new Intent();
        intent.setClass(getActivity(), BianminDetailActivity.class);
        if (type == 1) {
            intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.BEN_CLEANER);
        } else if (type == 2) {
            intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.BEN_REMOVER);
        } else if (type == 3) {
            intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.BEN_REPAIR);
        } else if (type == 4) {
            intent.putExtra(CodeConstants.MODULE_ID, CodeConstants.BEN_WEDDING);

        } else {
            activity.application.showToast("类型错误");
            return;
        }
        intent.putExtra(CodeConstants.BUSI_ID, returnList.get(position - 1)
                .getBenPeoId() + "");
        intent.putExtra(CodeConstants.BUSI_NAME, returnList.get(position - 1)
                .getBenPeoName());
        intent.putExtra(CodeConstants.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefresh = true;
        task = new BianminTask();
        task.execute(type, page);
    }

    @Override
    public void onLoadMore() {
        page++;
        isRefresh = false;
        task = new BianminTask();
        task.execute(type, page);

    }


    @Override
    public void onDestroy() {
        if (this.task != null && this.task.getStatus() == Status.RUNNING)
            this.task.cancel(true);
        super.onDestroy();
    }
}
