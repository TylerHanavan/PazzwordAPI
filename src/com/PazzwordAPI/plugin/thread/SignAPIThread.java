package com.PazzwordAPI.plugin.thread;

import com.PazzwordAPI.plugin.api.complex.SignAPI;

public class SignAPIThread implements Runnable {
	
	public SignAPIThread() {
		
		
		
	}
	
	@Override
	public void run() {
		SignAPI.tick();
	}
	
}
