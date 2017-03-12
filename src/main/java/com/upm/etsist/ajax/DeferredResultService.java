package com.upm.etsist.ajax;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.async.DeferredResult;

import com.upm.etsist.executor.HarvestExecutor;

public class DeferredResultService implements Runnable {

	private static final Logger logger = Logger.getLogger(DeferredResultService.class);

	private final BlockingQueue<DeferredResult<Message>> resultQueue = new LinkedBlockingQueue<>();

	private Thread thread;

	private volatile boolean start = true;

	private ShutdownService shutdownService;

	private Hook hook;

	private LinkedBlockingQueue<Message> messagesQueue;

	private HarvestExecutor harvestExecutor;

	private MessageSource messageSource;

	public void subscribe() {
		logger.info("Starting server");
		harvestExecutor.setLocale(LocaleContextHolder.getLocale());
		harvestExecutor.setMessageSource(messageSource);
		harvestExecutor.start();
		startThread();
	}

	private void startThread() {

		if (start) {
			synchronized (this) {
				if (start) {
					start = false;
					thread = new Thread(this, "Hilo padre");
					hook = shutdownService.createHook(thread);
					thread.start();
				}
			}
		}
	}

	@Override
	public void run() {

		logger.info("DeferredResultService.run - Inicio");
		while (hook.keepRunning()) {
			try {

				DeferredResult<Message> result = resultQueue.take();
				Message message = messagesQueue.take();

				result.setResult(message);

			} catch (InterruptedException e) {
				System.out.println("Interrupted when waiting for latest update. "
						+ e.getMessage());
			}
		}
		System.out.println("DeferredResultService.run Fin");
	}

	public void getUpdate(DeferredResult<Message> result) {
		resultQueue.add(result);
	}

	public ShutdownService getShutdownService() {
		return shutdownService;
	}

	public void setShutdownService(ShutdownService shutdownService) {
		this.shutdownService = shutdownService;
	}

	public LinkedBlockingQueue<Message> getMessagesQueue() {
		return messagesQueue;
	}

	public void setMessagesQueue(LinkedBlockingQueue<Message> messagesQueue) {
		this.messagesQueue = messagesQueue;
	}

	public HarvestExecutor getHarvestExecutor() {
		return harvestExecutor;
	}

	public void setHarvestExecutor(HarvestExecutor harvestExecutor) {
		this.harvestExecutor = harvestExecutor;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
