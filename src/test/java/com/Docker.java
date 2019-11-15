package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import org.testng.annotations.Test;

import utils.FileHelper;

public class Docker {
	
	public static void StartDockerAndGrid() throws IOException, InterruptedException {
		String fileName = System.getProperty("user.dir") + "\\docker-compose.log";
		File fi = new File(fileName);
		if (!fi.exists()) {
			fi.createNewFile();
		}
		FileReader file = new FileReader(fileName);
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("cmd /c start " + System.getProperty("user.dir") + "\\DockerUp.bat");
		Thread.sleep(3000);
		boolean flag = false;
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.SECOND, 60);
		long stopTime = calender.getTimeInMillis();
		BufferedReader reader = null;
		while (System.currentTimeMillis() <= stopTime) {
			if (FileHelper.IsRegistrationComplete(file, "The node is registered to the hub and ready to use")) {
				System.out.println("Node registration is complete");
				flag = true;
				break;
			}
		}
		// reader.close();
		file.close();

		/*if (flag) {
			runtime.exec("cmd /c start " + System.getProperty("user.dir") + "\\ScaleChrome.bat");
			Thread.sleep(15000);
		}*/
	}
	
	public static void StopDockerInstance() throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("cmd /c start " + System.getProperty("user.dir") + "\\DockerDown.bat");
		Thread.sleep(3000);
		String fileName = System.getProperty("user.dir") + "\\docker-compose.log";
		FileReader file = new FileReader(fileName);
		Calendar calender = Calendar.getInstance();
		calender.add(Calendar.SECOND, 30);
		long stopTime = calender.getTimeInMillis();
		while (System.currentTimeMillis() <= stopTime) {
			if (FileHelper.IsRegistrationComplete(file, "selenium-hub exited with code")) {
				System.out.println("DockerShutdown");
				File fi = new File(fileName);
				if (fi.isFile()) {
					fi.deleteOnExit();
					System.out.println("File Deleted Successfully");
				}
				break;
			}
		}

		// reader.close();
		file.close();
	}
}
