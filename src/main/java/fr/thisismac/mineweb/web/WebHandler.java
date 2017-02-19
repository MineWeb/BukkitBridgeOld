package fr.thisismac.mineweb.web;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.JsonObject;

import fr.thisismac.mineweb.Core;
import fr.thisismac.mineweb.Core.EnumMethod;


public class WebHandler extends AbstractHandler{

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// LET START HANDLING
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        baseRequest.setHandled(true);
        
        boolean correctKey = baseRequest.getParameter("key") != null && baseRequest.getParameter("key").equals(Core.get().getDB().getKey());
        boolean success = false;
		JsonObject respons = new JsonObject();
		
		
        if(!Core.get().getDB().isInstalled()) {
        	if(baseRequest.getParameter("key") != null && baseRequest.getParameter("domain") != null) {
        		Core.get().getDB().setKey(baseRequest.getParameter("key"));
            	Core.get().getDB().setIpAllowed(baseRequest.getRemoteAddr());
            	Core.get().getDB().setDomain(baseRequest.getParameter("domain"));
            	Bukkit.broadcastMessage(ChatColor.GREEN + "-- Mineweb Bridge est désormais configuré --");
            	Core.get().getDB().setInstalled(true);
            	Core.get().saveDB();
            	respons.addProperty("REQUEST", "INSTALLATION_COMPLETED");
        	}
        	else {
        		respons.addProperty("REQUEST", "NEED_PARAMATER_FOR_INSTALLATION");
        	}
        	
        	response.getWriter().println(respons.toString());
    		response.setStatus(HttpServletResponse.SC_OK);
    		return ;
        	
        }
        	
        // HANDLING PARAMETER
        	for(Entry<String, String[]> entry : baseRequest.getParameterMap().entrySet()) {
        		EnumMethod method = null;
        		try {
            		method = EnumMethod.valueOf(entry.getKey());
        		}
        		catch (Exception e) {}
        		
    			if(method != null) {
    				if(method.isKeyNeeded()) {
    					if(correctKey) {
    						success = Core.get().getMethodHandler().performAction(method, URLDecoder.decode(entry.getValue()[0], "UTF-8"));
    						respons.addProperty(entry.getKey(), String.valueOf(success).toUpperCase());
    						Core.get().getLog().logPerformed(entry.getKey() + "=" + URLDecoder.decode(entry.getValue()[0], "UTF-8"), baseRequest.getRemoteAddr(), String.valueOf(success).toUpperCase());
    					}
    					else {
    						respons.addProperty(entry.getKey(), "NEED_KEY");
    						Core.get().getLog().logPerformed(entry.getKey() + "=" + URLDecoder.decode(entry.getValue()[0], "UTF-8"), baseRequest.getRemoteAddr(), "NEED_KEY");
    					}
    				}
    				else {
    					String resp = Core.get().getMethodHandler().getData(method, URLDecoder.decode(entry.getValue()[0], "UTF-8"));
    					respons.addProperty(entry.getKey(), resp);
    				
    					Core.get().getLog().logRequest(entry.getKey() + "=" + URLDecoder.decode(entry.getValue()[0], "UTF-8"), baseRequest.getRemoteHost(), resp);
    				}
    					
    			}
    			else if(!entry.getKey().equals("key")){
    				respons.addProperty(entry.getValue()[0], "METHOD_DOES_NOT_EXIST");
    			}
    		}
        
		
		response.getWriter().println(respons.toString());
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
