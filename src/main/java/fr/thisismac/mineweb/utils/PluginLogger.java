package fr.thisismac.mineweb.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import fr.thisismac.mineweb.Core;

public class PluginLogger {
	
	private File requestLog;
	private File performedLog;
	private PrintWriter outRequest;
	private PrintWriter outPerformed;
	private Calendar cal;
	
	public PluginLogger() {
		this.requestLog = new File(Core.get().getDataFolder() + File.separator + "request.log");
		this.performedLog = new File(Core.get().getDataFolder() + File.separator + "performed.log");
		
		try {
			if(!requestLog.exists()) requestLog.createNewFile();
			if(!performedLog.exists()) performedLog.createNewFile();
			
			this.outRequest = new PrintWriter(new BufferedWriter(new FileWriter(requestLog, true)));
			this.outPerformed = new PrintWriter(new BufferedWriter(new FileWriter(performedLog, true)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void logRequest(String request, String from, String returned) {
		cal = Calendar.getInstance();
		outRequest.println(String.format("%d/%d/%d - %d:%d:%d from %s requested %s >> returned %s", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,cal.get(Calendar.YEAR), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), from, request, returned));
		outRequest.flush();
	}
	
	public void logPerformed(String request, String from, String returned) {
		cal = Calendar.getInstance();
		outPerformed.println(String.format("%d/%d/%d - %d:%d:%d from %s requested %s >> returned %s", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,cal.get(Calendar.YEAR), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), from, request, returned));
		outPerformed.flush();
	}
	
	public void close() {
		this.outPerformed.close();
		this.outRequest.close();
	}

}
