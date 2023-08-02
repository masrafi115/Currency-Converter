package com.codesw.convertmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.app.Activity;
import android.content.SharedPreferences;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class MarketRatesFragmentActivity extends  Fragment  { 
	
	
	private HashMap<String, Object> map = new HashMap<>();
	private HashMap<String, Object> map2 = new HashMap<>();
	private double n = 0;
	private HashMap<String, Object> map3 = new HashMap<>();
	private String BASE_URL = "";
	private String ACCESS_KEY = "";
	private String ENDPOINT = "";
	private  int resId;
	private String res = "";
	private double key = 0;
	
	private ArrayList<String> list1 = new ArrayList<>();
	private ArrayList<Double> list2 = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
	private LinearLayout linear1;
	private ListView listview1;
	
	private RequestNetwork req;
	private RequestNetwork.RequestListener _req_request_listener;
	private SharedPreferences offline;
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.market_rates_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		listview1 = (ListView) _view.findViewById(R.id.listview1);
		req = new RequestNetwork((Activity)getContext());
		offline = getContext().getSharedPreferences("offline", Activity.MODE_PRIVATE);
		
		_req_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				map2 = new Gson().fromJson(map.get("quotes").toString(), new TypeToken<HashMap<String, Object>>(){}.getType());
				SketchwareUtil.getAllKeysFromMap(map2, list1);
				getAllNumberValuesFromMap(map2, list2);
				n = 0;
				for(int _repeat67 = 0; _repeat67 < (int)(list1.size()); _repeat67++) {
					map3 = new HashMap<>();
					map3.put("data_a", list1.get((int)(n)));
					map3.put("data_b", String.valueOf(list2.get((int)(n)).doubleValue()));
					list.add(map3);
					n++;
				}
				listview1.setAdapter(new Listview1Adapter(list));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				offline.edit().putString(String.valueOf((long)(key)), new Gson().toJson(list)).commit();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		BASE_URL = "http://api.currencylayer.com/";
		ACCESS_KEY = "11bd38f1769733e6b5c09c9314d26d96";
		ENDPOINT = "live";
		req.startRequestNetwork(RequestNetworkController.GET, BASE_URL.concat(ENDPOINT).concat("?access_key=".concat(ACCESS_KEY)), "a", _req_request_listener);
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _getAllNumberValusFromMap () {
	} public static void getAllNumberValuesFromMap(Map<String, Object> map, ArrayList<Double> output) {
						if (output == null) return;
						output.clear();
		
						if (map == null || map.size() <= 0) return;
		
						Iterator itr = map.entrySet().iterator();
						while (itr.hasNext()) {
									Map.Entry<String, Double> entry = (Map.Entry) itr.next();
									output.add(entry.getValue());
						}
	}
	
	
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.rates, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			
			textview1.setText(_data.get((int)_position).get("data_a").toString());
			textview2.setText(new DecimalFormat("#.##").format(Double.parseDouble(_data.get((int)_position).get("data_b").toString())));
			res = _data.get((int)_position).get("data_a").toString().replace("USD", "").toLowerCase();
			resId = getContext().getResources().getIdentifier(res , "drawable", getContext().getPackageName());
			
			imageview1.setImageResource(resId);
			
			return _view;
		}
	}
	
	
}