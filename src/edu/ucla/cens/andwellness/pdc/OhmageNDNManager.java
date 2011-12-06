package edu.ucla.cens.andwellness.pdc;

import java.io.IOException;
import java.util.logging.Level;

import org.ccnx.android.ccnlib.CCNxServiceCallback;
import org.ccnx.android.ccnlib.CCNxServiceControl;
import org.ccnx.android.ccnlib.CCNxServiceStatus.SERVICE_STATUS;
import org.ccnx.android.ccnlib.CcndWrapper;
import org.ccnx.android.ccnlib.CcndWrapper.CCND_OPTIONS;
import org.ccnx.android.ccnlib.RepoWrapper.REPO_OPTIONS;
import org.ccnx.ccn.profiles.ccnd.CCNDaemonException;
import org.ccnx.ccn.profiles.ccnd.SimpleFaceControl;
import org.ccnx.ccn.protocol.ContentName;
import org.ccnx.ccn.protocol.MalformedContentNameStringException;

import edu.ucla.cens.andwellness.pdc.libpdc.OhmageApplication;
import edu.ucla.cens.pdc.libpdc.Application;
import edu.ucla.cens.pdc.libpdc.core.GlobalConfig;
import edu.ucla.cens.pdc.libpdc.core.PDVInstance;
import edu.ucla.cens.pdc.libpdc.exceptions.PDCException;
import edu.ucla.cens.pdc.libpdc.exceptions.PDCTransmissionException;
import edu.ucla.cens.pdc.libpdc.stream.DataStream;
import edu.ucla.cens.pdc.libpdc.transport.PDCReceiver;

import android.content.Context;
import android.util.Log;

/**
 * Top level class for NDN code.
 * 
 * @author Gauresh Rane
 */
public class OhmageNDNManager implements CCNxServiceCallback {
	
	static {
		GlobalConfig.setDataStorage(SQLiteStorage.class);
		GlobalConfig.setConfigStorage(SQLiteConfigStorage.class);
	}
	
	private OhmageNDNManager(Context ctx, OhmageNDNManagerCallback callback) {
		_context = ctx; 
		_callback_interface = callback;
		_ccnx_service = new CCNxServiceControl(_context);
		_ccnx_service.registerCallback(this);
		_ccnx_service.setCcndOption(CCND_OPTIONS.CCND_DEBUG, "INFO");
		_ccnx_service.setRepoOption(REPO_OPTIONS.REPO_DEBUG, "INFO");

		_ccnx_service.startAllInBackground();
	}
	
	public void setNamespace(String namespace)
	{
		_namespace = namespace;
	}

	public void setRemotehost(String remotehost)
	{
		_remotehost = remotehost;
	}

	public void setRemoteport(int remoteport)
	{
		_remoteport = remoteport;
	}

	public void setPubUrl(String pubUrl)
	{
		_pub_url = pubUrl;
	}

	public void setRecAuth(String recAuth)
	{
		_rec_auth = recAuth;
	}
	
	public void setRecUrl(String recUrl)
	{
		_rec_url = recUrl;
	}

	public Context getContext()
	{
		return _context;
	}
	
	public static OhmageNDNManager getInstance()
	{
		if (_ohmage_NDN_manager == null)
			throw new Error("AndroidNDNManager is not instantiated");

		return _ohmage_NDN_manager;
	}
	
	public static OhmageNDNManager createNewInstance(Context ctx,
			OhmageNDNManagerCallback callback)
	{
		if (_ohmage_NDN_manager != null) {
			_ohmage_NDN_manager._context = ctx;
		} else
			_ohmage_NDN_manager = new OhmageNDNManager(ctx, callback);

		return _ohmage_NDN_manager;
	}
	
	public void create(Context ctx)
	{
		try {
			org.ccnx.ccn.impl.support.Log.setDefaultLevel(
					org.ccnx.ccn.impl.support.Log.FAC_ALL, Level.INFO);

			GlobalConfig config = GlobalConfig.loadState();
			if (config == null) {
				Log.i(TAG,"Generating new config");
				config = GlobalConfig.getInstance();
				config.setRoot(ContentName.parse(_namespace));

				final OhmageApplication app = new OhmageApplication(ctx, "andwellness");
				config.addApplication(app);

				_stream = new DataStream(config.getCCNHandle(), app, "andwellnessdbresponses");
				app.addDataStream(_stream);
				
				// final DataStream ds_2 = new DataStream(handle, app, "andwellness.db.campaingns");

				config.storeStateRecursive();
				Log.e(TAG, "New Config.");
			} else
				Log.i(TAG, "Using existing config");

			// final Application app = config.getApplication("AndWellness");
			// _stream = app.getDataStream("myds");
		}
		catch (final MalformedContentNameStringException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void startCCNx()
	{
		Log.i(TAG, "RemoteHost is: " + _remotehost);
		Log.i(TAG, "Remote Port is:" + _remoteport);
		if (_ccnx_service == null || !_ccnx_service.allReady()) {
			if(!SERVICE_STATUS.SERVICE_RUNNING.equals(
					_ccnx_service.getCcndStatus())) {
				_ccnx_service.startCcnd();
				_callback_interface.postProcess(
						"CCND wasn't running, starting CCND..");
			}
			if(!SERVICE_STATUS.SERVICE_RUNNING.equals(
					_ccnx_service.getRepoStatus())) {
				_ccnx_service.startRepo();
				_callback_interface.postProcess(
						"Key Repository wasn't running, starting Repo..");
			}
			_callback_interface.postProcess("Calm down, CCNx is not up yet", false);
			return;
		}

		// DataGenerator.start(_context,
		// 		_context.getResources().openRawResource(R.raw.test), 1, 1);

		_ccnx_service.connect();
		try {
		
			SimpleFaceControl.getInstance().connectTcp(_remotehost, _remoteport);
		}
		catch (CCNDaemonException e) {
			Log.e(TAG, "Unable to make connection", e);
			_callback_interface.postProcess(
					"Unable to connect to hub: " + e.getLocalizedMessage(), true);
			return;
		}

		_callback_interface.postProcess(true);
	}
	
	public synchronized void startListening()
	{
		if (_listening) {
			return;
		}
		
		assert _pdv_instance != null;

		_pdv_instance = new PDVInstance();
		try {
			_pdv_instance.startListening();
			_listening = true;
		}
		catch (MalformedContentNameStringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_callback_interface.postProcess("PDV Started listening..");
	}
	
	public synchronized void shutdown()
	{
		if (!_listening)
			return;

		if (_pdv_instance == null)
			throw new Error("There's no PDV instance!");

		_pdv_instance.stopListening();
		
		_listening = false;
	}
	
	
	
	public synchronized void setup() throws PDCException
	{
		Log.d(TAG, "Running: " + _listening);
		
		if (!_listening)
			return;

		if (_pdv_instance == null)
			throw new Error("There's no PDV instance!");

		if (_stream == null)
			throw new Error("There's no Stream instance!");
		
		try {
			// ContentName pub_uri = ContentName.parse(_pub_url);
			_pdv_receiver = new PDCReceiver(_rec_url);
			_stream.addReceiver(_pdv_receiver);
			_stream.setupReceiver(_pdv_receiver, _rec_auth);
		} catch (MalformedContentNameStringException e) {
			Log.e(TAG, "Invalid URI: " + _pub_url, e);
			return;
		}
	}
	
	// Accessors 
	
	boolean isListening() {
		return _listening;
	}
	
	void postProcess(String str) {
		_callback_interface.postProcess(str);
	}
	

	@Override
	public void newCCNxStatus(SERVICE_STATUS st) {
		// TODO Auto-generated method stub
		switch (st) {
		case CCND_INITIALIZING:
			_callback_interface.postProcess("Initializing CCNd ...");
			break;
		case CCND_OFF:
			_callback_interface.postProcess("CCNd is off.");
			break;
		case CCND_RUNNING:
			_callback_interface.postProcess("CCNd is running.");
			break;
		case CCND_TEARING_DOWN:
			_callback_interface.postProcess("Tearing down CCNd ...");
			break;
		case START_ALL_DONE:
			Log.i(TAG, "CCNx started. START_ALL_DONE");
			_callback_interface.postProcess("CCNx started", false);
			break;
		case START_ALL_ERROR:
			_callback_interface.postProcess("Error while starting CCNx");
			_callback_interface.postProcess("Error while starting CCNx", false);
			Log.e(TAG, "CCNx failed to start. START_ALL_ERROR");
			break;
		}
	}
	
	
	static String TAG = "OHMAGE_NDN_MANAGER_CLASS";
	
	static CcndWrapper _ccnd_wrapper;
	
	private CCNxServiceControl _ccnx_service;
	
	private boolean _listening = false;

	private String _namespace;

	private String _remotehost;

	private int _remoteport;

	private String _pub_url;

	private String _rec_auth;
	
	private String _rec_url;

	private Context _context;

	private PDVInstance _pdv_instance;
	
	private PDCReceiver _pdv_receiver;

	private DataStream _stream;

	private static OhmageNDNManager _ohmage_NDN_manager;
	
	private static OhmageNDNManagerCallback _callback_interface;

}