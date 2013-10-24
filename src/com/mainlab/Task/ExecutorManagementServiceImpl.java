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

	private volatile static ExecutorManagementServiceImpl singleThreadPoolInstance;

	private ExecutorManagementServiceImpl(){

	}

	public static ExecutorManagementServiceImpl getInstance() {
		if (singleThreadPoolInstance == null) {
			synchronized (ExecutorManagementServiceImpl.class) {
				if (singleThreadPoolInstance == null) {
					singleThreadPoolInstance = new ExecutorManagementServiceImpl();
				}
			}
		}
		return singleThreadPoolInstance;
	}


	@Override
	public void execute() {
		ExecutorService executorService = Executors.newFixedThreadPool(10); 
		//     LinkedBlockingQueue <String> s = new LinkedBlockingQueue<String>(); 

		CompletionService<TaskExecutionResult> taskCompletionService = new ExecutorCompletionService<TaskExecutionResult>(
				executorService);

		try {
			List<Callable<TaskExecutionResult>> executableTasks = getTaskList();
			for (Callable<TaskExecutionResult> callable : executableTasks) {
				taskCompletionService.submit(callable);
			}
			for (int i = 0; i < executableTasks.size(); i++) {
				Future<TaskExecutionResult> result = taskCompletionService.take();
				System.out.println(result.get());
			}
		} catch (InterruptedException e) {
			// 
			e.printStackTrace();
		} catch (ExecutionException e) {
			// 
			e.printStackTrace();
		}
		executorService.shutdown();

	}
	
	
	public List<Callable<TaskExecutionResult>> getTaskList(){
		return this.executableTaskList;
	}
	
	public void setTaskList (List<Callable<TaskExecutionResult>> taskList){
		this.executableTaskList = taskList;
	}
	
}
