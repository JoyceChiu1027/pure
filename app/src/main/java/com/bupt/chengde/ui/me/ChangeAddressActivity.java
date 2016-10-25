package com.bupt.chengde.ui.me;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bupt.chengde.R;
import com.bupt.chengde.entity.Province;
import com.bupt.chengde.ui.BaseActivity;
import com.bupt.chengde.util.MyHandler;

/**
 * @author wyf
 * @类 :修改地址
 * @version 1.0
 */
public class ChangeAddressActivity extends BaseActivity {

	private TextView cancleTextView;
	private TextView saveTextView;
	private Spinner province, city, area;

	private int[] item = new int[3];
	private List<Province> provinceList = new ArrayList<Province>();
	private ArrayAdapter<String> p_adapter, c_adapter, d_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_address);

		initView();
		try {
			// 解析XML
			MyHandler handler = new MyHandler(provinceList);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			InputStream is = getAssets().open("citys_weather.xml");
			parser.parse(is, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		p_adapter = new ArrayAdapter<String>(this, R.layout.item2);
		for (int i = 0; i < provinceList.size(); i++) {
			p_adapter.add(provinceList.get(i).getName());
		}
		province.setAdapter(p_adapter);
		province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				item[0] = arg2;
				c_adapter = new ArrayAdapter<String>(
						ChangeAddressActivity.this, R.layout.item2);
				for (int i = 0; i < provinceList.get(arg2).getList().size(); i++) {
					c_adapter.add(provinceList.get(arg2).getList().get(i)
							.getName());
				}
				city.setAdapter(c_adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				item[1] = arg2;
				d_adapter = new ArrayAdapter<String>(
						ChangeAddressActivity.this, R.layout.item2);
				for (int i = 0; i < provinceList.get(item[0]).getList()
						.get(arg2).getList().size(); i++) {
					d_adapter.add(provinceList.get(item[0]).getList().get(arg2)
							.getList().get(i).getName());
				}
				area.setAdapter(d_adapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		area.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				item[2] = arg2;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void initView() {
		cancleTextView = (TextView) findViewById(R.id.top_cancle);
		saveTextView = (TextView) findViewById(R.id.top_sub);
		((LinearLayout) findViewById(R.id.top_back)).setVisibility(View.GONE);
		cancleTextView.setVisibility(View.VISIBLE);
		saveTextView.setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.top_name)).setText("地址");
		saveTextView.setText("保存");
		province = (Spinner) findViewById(R.id.ch_add_provice);
		city = (Spinner) findViewById(R.id.ch_add_city);
		area = (Spinner) findViewById(R.id.ch_add_area);
		saveTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				application.showToast(provinceList.get(item[0]).getName()
						+ "省"
						+ provinceList.get(item[0]).getList().get(item[1])
								.getName()
						+ "市"
						+ provinceList.get(item[0]).getList().get(item[1])
								.getList().get(item[2]).getName()+"区");
			}
		});
		cancleTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

}
