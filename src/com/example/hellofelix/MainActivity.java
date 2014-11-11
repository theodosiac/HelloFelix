package com.example.hellofelix;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

import com.javaworld.sample.service.HelloService;
import com.javaworld.sample.service.impl.HelloServiceActivator;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract.Directory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {


	//EditText editText = (EditText) findViewById(R.id.text_view);
	TextView label = null;//(TextView) findViewById(R.id.text_view);
	private Felix m_felix =null;
	public void LogEx(String info)
	{
		Log.d("Felix",info);
		if(label == null)
			label = (TextView) findViewById(R.id.text_view);
		
		if(label != null)
			label.setText(label.getText()+info+"\n");
	}
	
	void launchFelix() {
		
		LogEx("About to start Felix");
		String cacheDir= null;
		try
		{
			cacheDir = File.createTempFile("skifta", ".tmp").getParent();
		}
		catch(IOException e)
		{
			Log.d("Felix", "Unable to create temp file",e);
		}
		
		Map configMap = new HashMap();
		configMap.put("org.osgi.framework.storage", cacheDir);
		configMap.put("felix.embedded.execution", "true");
		configMap.put("org.osgi.service.http.port", "9990");
		configMap.put("org.osgi.framework.startlevel.beginning", "5");		
		
		try
		{
			m_felix = new Felix(configMap);
			m_felix.start();
			LogEx("Felix is started");
			
			
			for(org.osgi.framework.Bundle b : m_felix.getBundleContext().getBundles())
				LogEx("Bundle: "+ b.getSymbolicName()+ ",ID: "+b.getBundleId());
		
	
			
		}
		catch(Throwable ex)
		{
			Log.d("Felix", "Could not create Framework: "+ex.getMessage(),ex);
		}
		
		
		
		BundleContext context = m_felix.getBundleContext();
		List<org.osgi.framework.Bundle> installedBundles = new LinkedList<org.osgi.framework.Bundle>();

		// Install the bundles
		try {
			//com.google.dexmaker.DexMakerDexMaker dsfds;
			//installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/com.javaworld.sample.HelloService_1.0.0.201410011449.jar"));
			//installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/com.javaworld.sample.HelloWorld_1.0.0.201410011449.jar"));
			
			installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/android-felix/bundle/org.apache.felix.shell.tui-1.0.2.jar"));
			installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/android-felix/bundle/org.apache.felix.shell-1.0.2.jar"));
			installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/android-felix/bundle/EnglishDictionary.jar"));
			installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/android-felix/bundle/FrenchDictionary.jar"));
			installedBundles.add(context.installBundle("file:storage/extSdCard/Files/temp/android-felix/bundle/SpellChecker.jar"));

		} 
		catch (BundleException e) 
		{

			LogEx("Additional Bundles were not installed");
			LogEx(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (org.osgi.framework.Bundle bundle : installedBundles) 
		{ 
		    try {
				bundle.start();
			} catch (BundleException e) {
				LogEx("Bundle not started: ");
				// TODO Auto-generated catch block
				LogEx(e.getMessage());
			}
		}
		for(org.osgi.framework.Bundle b : m_felix.getBundleContext().getBundles())
			LogEx("Bundle: "+ b.getSymbolicName()+ ",ID: "+b.getBundleId());
		// Start installed bundles
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		launchFelix();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
