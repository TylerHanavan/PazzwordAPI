package com.PazzwordAPI.plugin.api.complex;

import com.PazzwordAPI.plugin.api.complex.SignAPI.SignInstance;

public interface SignLoader {

	public boolean handles(SignInstance sign);
	public void handle(SignInstance sign);
	
}
