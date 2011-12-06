package edu.ucla.cens.andwellness.pdc;

import org.ccnx.android.ccnlib.CCNxConfiguration;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.ucla.cens.andwellness.R;
import edu.ucla.cens.pdc.libpdc.exceptions.PDCException;

/**
 * Screen to make configuration choices. No CCNx code here. After user presses
 * Connect button, we startup the Android Manager.
 */
public final class NDNActivity extends Activity implements
		OnClickListener, OhmageNDNManagerCallback {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ndn_settings);

		Log.i(TAG, "onCreate()");

		_start_ccnx_button = (Button) findViewById(R.id.btnStartCCNx);
		if (_start_ccnx_button == null)
			throw new Error("Could not find btnStartCCNx");

		_listen_button = (Button) findViewById(R.id.btnListen);
		if (_listen_button == null)
			throw new Error("Could not find btnSave");

		_connect_button = (Button) findViewById(R.id.btnConnect);
		if (_connect_button == null)
			throw new Error("Could not find btnConnect");

		_setup_button = (Button) findViewById(R.id.btnSetup);
		if (_setup_button == null)
			throw new Error("Could not find btnSetup");

		_tv_status = (TextView) findViewById(R.id.tvStatus);
		if (_tv_status == null)
			throw new Error("Could not find tvStatus");

		_start_ccnx_button.setOnClickListener(this);
		_listen_button.setOnClickListener(this);
		_connect_button.setOnClickListener(this);
		_setup_button.setOnClickListener(this);

		_etNamespace = (EditText) findViewById(R.id.etNamespace);
		_etRecURL = (EditText) findViewById(R.id.etRecURL);
		_etRemoteHost = (EditText) findViewById(R.id.etRemoteHost);
		_etRemotePort = (EditText) findViewById(R.id.etRemotePort);
		_etRecAuth = (EditText) findViewById(R.id.etAuth);

		restorePreferences();

		enableView(false);

		_ohmage_NDN_manager = OhmageNDNManager.createNewInstance(
				this.getApplicationContext(), this);

		// switch (AndroidNDNManager.getStatus()) {
		// case NOT_INSTANTIATED:
		// _android_NDN_manager = AndroidNDNManager.createNewInstance(
		// this.getApplicationContext(), this);
		// break;
		// case STOPPED:
		// _android_NDN_manager = AndroidNDNManager.getInstance();
		// _android_NDN_manager.setCallbackInterface(this);
		// AndroidNDNManager.setContext(this.getApplicationContext());
		// break;
		// case STARTED_WAIT:
		// enableView(false);
		// _connect_button.setText("Processing");
		// _android_NDN_manager = AndroidNDNManager.getInstance();
		// _android_NDN_manager.setCallbackInterface(this);
		// AndroidNDNManager.setContext(this.getApplicationContext());
		// break;
		// case STARTED_ACTIVE:
		// _android_NDN_manager = AndroidNDNManager.getInstance();
		// _android_NDN_manager.setCallbackInterface(this);
		// AndroidNDNManager.setContext(this.getApplicationContext());
		// enableView(false);
		// _connect_button.setEnabled(true);
		// _connect_button.setText("Disconnect");
		// }
	}

	@Override
	public void onStop()
	{
		super.onStop();
		savePreferences();
	}

	@Override
	public void onClick(View v)
	{
		Log.d(TAG, "OnClickListener " + String.valueOf(v.getId()));

		switch (v.getId()) {
		case R.id.btnStartCCNx:
			if (_ohmage_NDN_manager == null)
				return;
			
			if (ccnxConfigured == false) {
				ccnxConfigured = true;
				Log.i(TAG, "Configuring CCNX");
				CCNxConfiguration.config(getApplicationContext());
			}

			_ohmage_NDN_manager.setNamespace(_etNamespace.getText().toString());
			_ohmage_NDN_manager.setRemotehost(
					_etRemoteHost.getText().toString());
			_ohmage_NDN_manager.setRemoteport(Integer.parseInt(
					_etRemotePort.getText().toString()));
			// CCNX started
			_ohmage_NDN_manager.startCCNx();
			// Now create application and the 
			_ohmage_NDN_manager.create(this);
			break;
		case R.id.btnListen:
			_ohmage_NDN_manager.startListening();
			assert _ohmage_NDN_manager.isListening() == true;
			break;
		case R.id.btnConnect:
			if (_connect_button.getText().toString().
					equalsIgnoreCase("Connect")) {
				//connect();
			}
			else if (_connect_button.getText().toString()
					.equalsIgnoreCase("Disconnect"))
				disconnect();
			break;

		case R.id.btnSetup:
			Log.i(TAG, "Setup button pressed");
			if(_ohmage_NDN_manager.isListening()) {
				_ohmage_NDN_manager.setRecUrl(_etRecURL.getText().toString());
				Log.i(TAG, _etRecURL.getText().toString());
				_ohmage_NDN_manager.setRecAuth(_etRecAuth.getText().toString());
				Log.i(TAG, _etRecAuth.getText().toString());
				try {
					_ohmage_NDN_manager.setup();
				} catch (PDCException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Log.i(TAG, "Why are you not listenign");
				_ohmage_NDN_manager.postProcess(
						"PDV is not yet Listening. Please make it start listening");
			}
			break;

		default:
			break;
		}
	}

	// ==========================================================
	// Internal stuff
	/*
	private void connect()
	{
		try {
			// enableView(false);
			// _connect_button.setText("Processing");

			// final File ff = getDir("storage", Context.MODE_WORLD_READABLE);
			// Log.i(TAG, "getDir = " + ff.getAbsolutePath());

			// UserConfiguration.setUserConfigurationDirectory(ff.getAbsolutePath());
			
			if (ccnxConfigured == false) {
				ccnxConfigured = true;
				Log.i(TAG, "Configuring CCNX");
				CCNxConfiguration.config(getApplicationContext());
			}
			Log.i(TAG, "Streams started.");

			_ohmage_NDN_manager.setNamespace(_etNamespace.getText().toString());
			_ohmage_NDN_manager.setRemotehost(_etRemoteHost.getText().toString());
			_ohmage_NDN_manager.setRemoteport(Integer.parseInt(_etRemotePort
					.getText().toString()));
			_ohmage_NDN_manager.setRecUrl(_etRecURL.getText().toString());
			_ohmage_NDN_manager.setRecAuth(_etRecAuth.getText().toString());

			_ohmage_NDN_manager.start();
		}
		catch (final Exception e) {
			Log.e(TAG, "Error with ContentName", e);
			return;
		}

	}*/

	private void disconnect()
	{
		Log.i(TAG, "Streams Stopped.");

		_ohmage_NDN_manager.shutdown();

		_connect_button.setText("Connect");
		enableView(true);
	}

	private void enableView(boolean enable)
	{
		switch (_conn_state) {
		case INITIAL:
			_etNamespace.setEnabled(true);
			_etRemoteHost.setEnabled(true);
			_etRemotePort.setEnabled(true);
			_start_ccnx_button.setEnabled(true);
			_etRecURL.setEnabled(false);
			_etRecAuth.setEnabled(false);
			_listen_button.setEnabled(false);
			_connect_button.setEnabled(false);
			_setup_button.setEnabled(false);
			break;
		case STARTED:
			_etNamespace.setEnabled(false);
			_etRemoteHost.setEnabled(false);
			_etRemotePort.setEnabled(false);
			_start_ccnx_button.setEnabled(false);
			_etRecURL.setEnabled(true);
			_etRecAuth.setEnabled(true);
			_listen_button.setEnabled(true);
			_connect_button.setEnabled(true);
			_setup_button.setEnabled(enable);
		}
	}

	private void restorePreferences()
	{
		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final String namespace = settings.getString(PREF_NAMESPACE,
				DEFAULT_NAMESPACE);
		final String remotehost = settings.getString(PREF_REMOTEHOST,
				DEFAULT_REMOTEHOST);
		final String remoteport = settings.getString(PREF_REMOTEPORT,
				DEFAULT_REMOTEPORT);
		final String recURL = settings.getString(PREF_REC_URL, DEFAULT_REC_URL);
		final String recAuth = settings.getString(PREF_REC_AUTH,
		 DEFAULT_REC_AUTH);

		_etNamespace.setText(namespace);
		_etRemoteHost.setText(remotehost);
		_etRemotePort.setText(remoteport);
		 _etRecURL.setText(recURL);
		_etRecAuth.setText(recAuth);
	}

	private void savePreferences()
	{
		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		final SharedPreferences.Editor editor = settings.edit();

		editor.putString(PREF_NAMESPACE, _etNamespace.getText().toString());
		editor.putString(PREF_REMOTEHOST, _etRemoteHost.getText().toString());
		editor.putString(PREF_REMOTEPORT, _etRemotePort.getText().toString());
		editor.putString(PREF_REC_URL, _etRecURL.getText().toString());
		editor.putString(PREF_REC_AUTH, _etRecAuth.getText().toString());
		editor.commit();
	}

	@Override
	public void postProcess(boolean status)
	{
		final Message msg = Message.obtain();
		msg.what = 1;
		msg.obj = status;
		_handler.sendMessage(msg);
	}

	@Override
	public void postProcess(String text, boolean show_longer)
	{
		final Message msg = Message.obtain();
		msg.what = 0;
		msg.arg1 = show_longer ? 1 : 0;
		msg.obj = text;
		_handler.sendMessage(msg);
	}

	@Override
	public void postProcess(String text)
	{
		final Message msg = Message.obtain();
		msg.what = 2;
		msg.obj = text;
		_handler.sendMessage(msg);
	}

	private final Handler _handler = new Handler() {
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what) {
			case 0: {
				final String text = (String) msg.obj;
				final Toast toast = Toast.makeText(NDNActivity.this, text,
						msg.arg1 == 0 ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
				toast.show();
				break;
			}
			case 1:
				final boolean status = (Boolean) msg.obj;
				if (status) {
					_conn_state = ConnectionState.STARTED;
				} else {
					_conn_state = ConnectionState.INITIAL;
				}
				enableView(status);
				break;
			case 2:
				final String text = (String) msg.obj;
				_tv_status.setText(text);
			}
		}
	};

	enum ConnectionState {
		INITIAL, STARTED
	}

	private ConnectionState _conn_state = ConnectionState.INITIAL;

	protected final static String TAG = "NDNSettingsActivity";

	private OhmageNDNManager _ohmage_NDN_manager;

	private static final String PREFS_NAME = "ccnChatPrefs";

	private static final String DEFAULT_NAMESPACE = 
			"ccnx:/ucla.edu/apps/andwellness";

	private static final String DEFAULT_REC_URL = 
			"ccnx:/ucla.edu/apps/server_pdv";

	private static final String DEFAULT_REMOTEHOST = "131.179.210.25";

	private static final String DEFAULT_REMOTEPORT = "9695";

	private static final String DEFAULT_REC_AUTH = "test";

	protected static final String PREF_NAMESPACE = "namespace";

	protected static final String PREF_REC_URL = "recURL";

	protected static final String PREF_REMOTEHOST = "remotehost";

	protected static final String PREF_REMOTEPORT = "remoteport";

	protected static final String PREF_REC_AUTH = "recAuth";
	
	private static boolean ccnxConfigured = false;

	private Button _start_ccnx_button, _listen_button, _connect_button,
			_setup_button;

	private TextView _tv_status;

	private EditText _etNamespace, _etRemoteHost, _etRemotePort, _etRecURL,
			_etRecAuth;
}
