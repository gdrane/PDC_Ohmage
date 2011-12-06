package edu.ucla.cens.andwellness.pdc.libpdc;

import java.io.IOException;

import android.content.Context;
import edu.ucla.cens.pdc.libpdc.Application;

public class OhmageApplication extends Application {
	
	public OhmageApplication(Context ctx, String name) throws IOException {
		super(name);
		_context = ctx;
	}
	
	public Context getApplicationContext() {
		return _context;
	}
	
	private Context _context;
	
}
