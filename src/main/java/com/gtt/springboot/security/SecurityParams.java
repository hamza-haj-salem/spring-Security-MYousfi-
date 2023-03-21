package com.gtt.springboot.security;

public interface SecurityParams {
	
	public static final String JWT_HEADER_NAME = "authorization";
	public static final String SECRET = "hamza@hajsalem.net";
	public static final long EXPIRATION = 10*24*3600;
	public static final String HEADER_PREFIX = "Bearer ";

}
