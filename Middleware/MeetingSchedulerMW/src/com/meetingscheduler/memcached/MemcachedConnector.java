package com.meetingscheduler.memcached;

import com.p5.xmemcache.MemcachedClient;
import com.p5.xmemcache.SockIOPool;

/**
 * @author : Prashant Kagwad Copyright (c) 2014 All rights reserved.
 * 
 */
public class MemcachedConnector {

	public MemcachedConnector() {
		// TODO Auto-generated constructor stub
	}

	public MemcachedClient getMemcachedConnector() {

		MemcachedClient mcc = null;
		try {
			// initialize the SockIOPool that maintains the Mem-cached Server
			// Connection Pool
			String[] servers = { "localhost:11211" };
			SockIOPool pool = SockIOPool.getInstance("memcache_instance");
			pool.setServers(servers);
			pool.setFailover(true);
			pool.setInitConn(10);
			pool.setMinConn(5);
			pool.setMaxConn(250);
			pool.setMaintSleep(30);
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setAliveCheck(true);
			pool.initialize();

			// Get the Mem-cached Client from SockIOPool named memcache_instance
			mcc = new MemcachedClient("memcache_instance");
		} catch (Exception e) {
			System.out
					.println("Exception occured with getting cache connection!");
			// e.printStackTrace();
		}
		return mcc;
	}
}