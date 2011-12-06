package edu.ucla.cens.andwellness.pdc;

public interface OhmageNDNManagerCallback {
	void postProcess(boolean status);
	void postProcess(String text, boolean show_longer);
	void postProcess(String text);
}
