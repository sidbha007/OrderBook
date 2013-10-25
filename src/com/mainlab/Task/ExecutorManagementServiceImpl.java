package com.mainlab.Task;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ExecutorManagementServiceImpl implements ExecutionManagementService{

	private volatile List<Callable<TaskExecutionResult>> executableTaskList ;

	private volatile static ExecutorManagementServiceImpl singleThreadPoolInstance = new ExecutorManagementServiceImpl();

	private ExecutorManagementServiceImpl(){

	}

	public static ExecutorManagementServiceImpl getInstance() {
		return singleThreadPoolInstance;
	}

    ExecutorService executorService = Executors.newFixedThreadPool(10);

	@Override
	public void execute(Runnable task) {
		//     LinkedBlockingQueue <String> s = new LinkedBlockingQueue<String>(); 
        executorService.execute(task);
        //task.run();

	}
	
	

	
}
