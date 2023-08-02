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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ConvertCurrencyFragmentActivity extends  Fragment  { 
	
	
	private String BASE_URL = "";
	private String ACCESS_KEY = "";
	private String FROM = "";
	private String TO = "";
	private  int resIds;
	private  int resId;
	private HashMap<String, Object> map = new HashMap<>();
	private double Swap = 0;
	
	private LinearLayout linear9;
	private LinearLayout linear1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private TextView textview3;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear8;
	private ImageView imageview4;
	private EditText edittext1;
	private TextView textview4;
	private ImageView imageview2;
	private TextView textview2;
	private ImageView imageview3;
	private TextView textview5;
	private Button button1;
	
	private Intent i = new Intent();
	private SharedPreferences currencys;
	private RequestNetwork convert;
	private RequestNetwork.RequestListener _convert_request_listener;
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.convert_currency_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		
		linear9 = (LinearLayout) _view.findViewById(R.id.linear9);
		linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
		linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
		linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
		linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
		linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
		linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
		linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
		textview3 = (TextView) _view.findViewById(R.id.textview3);
		imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
		textview1 = (TextView) _view.findViewById(R.id.textview1);
		linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
		imageview4 = (ImageView) _view.findViewById(R.id.imageview4);
		edittext1 = (EditText) _view.findViewById(R.id.edittext1);
		textview4 = (TextView) _view.findViewById(R.id.textview4);
		imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
		textview2 = (TextView) _view.findViewById(R.id.textview2);
		imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
		textview5 = (TextView) _view.findViewById(R.id.textview5);
		button1 = (Button) _view.findViewById(R.id.button1);
		currencys = getContext().getSharedPreferences("currencys", Activity.MODE_PRIVATE);
		convert = new RequestNetwork((Activity)getContext());
		
		imageview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getContext(), CurrencyActivity.class);
				i.putExtra("key", "From");
				startActivity(i);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getContext(), CurrencyActivity.class);
				i.putExtra("key", "To");
				startActivity(i);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				convert.startRequestNetwork(RequestNetworkController.GET, BASE_URL.concat(FROM.concat("_".concat(TO)).concat(",").concat(TO.concat("_".concat(FROM))).concat("&compact=ultra&apiKey=".concat(ACCESS_KEY))), "A", _convert_request_listener);
			}
		});
		
		_convert_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
				textview5.setText(new DecimalFormat("#.###").format((double)map.get(FROM.concat("_".concat(TO))) * Double.parseDouble(edittext1.getText().toString())));
				Swap = (double)map.get(TO.concat("_".concat(FROM)));
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		BASE_URL = "https://free.currconv.com/api/v7/convert?q=";
		ACCESS_KEY = "69d59ea126b2a28be965";
		FROM = "USD";
		TO = "GBP";
		//free.currconv.com/api/v7/convert?q=USD_PHP,PHP_USD&compact=ultra&apiKey=69d59ea126b2a28be965
		_setRipple(imageview4);
		_setRipple(imageview3);
	}
	
	@Override
	public void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (!currencys.getString("From", "").equals("")) {
			FROM = currencys.getString("From", "");
			currencys.edit().remove("From").commit();
			textview1.setText(FROM);
			resId = getContext().getResources().getIdentifier(FROM.toLowerCase() , "drawable", getContext().getPackageName());
			imageview1.setImageResource(resId);
		}
		if (!currencys.getString("To", "").equals("")) {
			TO = currencys.getString("To", "");
			currencys.edit().remove("To").commit();
			textview2.setText(TO);
			resIds = getContext().getResources().getIdentifier(TO.toLowerCase() , "drawable", getContext().getPackageName());
			imageview2.setImageResource(resIds);
		}
	}
	public void _setRipple (final View _view) {
		TypedValue typedValue = new TypedValue();
		
		getContext().getTheme().resolveAttribute(16843868, typedValue, true);
		
		_view.setBackgroundResource(typedValue.resourceId);
		
		_view.setClickable(true);
	}
	
	
	
}