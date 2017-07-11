package com.shsxt.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.shsxt.service.CustomerService;

@Component
public class CustomerLossJob {
	
	@Autowired
	private CustomerService customerService;
	
	@Scheduled(cron="0 44 14 * * ?")
	public void runCustomerLoss() {
		customerService.runCustomerLoss();
	}
	
	
}
