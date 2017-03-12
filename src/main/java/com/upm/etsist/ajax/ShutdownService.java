package com.upm.etsist.ajax;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ShutdownService {

	private static final Logger logger = Logger.getLogger(ShutdownService.class);

	private final List<Hook> hooks;

	public ShutdownService() {
		logger.debug("Creating shutdown service");
		hooks = new ArrayList<Hook>();
		createShutdownHook();
	}

	protected void createShutdownHook() {
		ShutdownDaemonHook shutdownHook = new ShutdownDaemonHook();
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	protected class ShutdownDaemonHook extends Thread {

		@Override
		public void run() {

			logger.info("Running shutdown sync");

			for (Hook hook : hooks) {
				hook.shutdown();
			}
		}
	}

	public Hook createHook(Thread thread) {

		thread.setDaemon(true);
		Hook retVal = new Hook(thread);
		hooks.add(retVal);
		return retVal;
	}

	List<Hook> getHooks() {
		return hooks;
	}
}
