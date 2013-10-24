package com.mainlab.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecutorManagementServiceImpl implements ExecutionManagementService{
	volatile List<Callable<TaskExecutionResult>> executableTaskList = new LinkedList<Callable<TaskExecutionResult>>();

	@Override
	public void execute() {
		ExecutorService executorService = Executors.newFixedThreadPool(10); 
		//     LinkedBlockingQueue <String> s = new LinkedBlockingQueue<String>(); 

		CompletionService<TaskExecutionResult> taskCompletionService = new ExecutorCompletionService<TaskExecutionResult>(
				executorService);

		try {
			List<Callable<TaskExecutionResult>> executableTasks = executableTaskList;
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


	public List<Callable<TaskExecutionResult>> createExecutableTaskList(Callable<TaskExecutionResult> bidTask) {
		// TODO Auto-generated method stub

		executableTaskList.add(bidTask);
		return executableTaskList;
	}
}
