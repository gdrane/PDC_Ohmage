package edu.ucla.cens.andwellness.pdc.libpdc.stream;

import java.io.IOException;

import org.ccnx.ccn.CCNHandle;

import android.database.sqlite.SQLiteDatabase;

import edu.ucla.cens.andwellness.db.Response;
import edu.ucla.cens.pdc.libpdc.iApplication;
import edu.ucla.cens.pdc.libpdc.stream.DataStream;

public class AndDataStream extends DataStream {

	public AndDataStream(CCNHandle handle, iApplication app, String name)
			throws IOException {
		super(handle, app, name);
		// TODO Auto-generated constructor stub
	}
	

}