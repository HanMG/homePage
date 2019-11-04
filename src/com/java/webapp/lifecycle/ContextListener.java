package com.java.webapp.lifecycle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author : HMG (HANMOONGOO)
 * @Date : 2019. 11. 4.
 * @Detail : 웹 어플리케이션 생명 주기 (LifeCycle) 리스너의 해당 메소드가 웹 어플리케이션 시작과 종료를 호출한다.
 */

public class ContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		System.out.println("WEP APP END -------------- ");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("WEP APP START -------------- ");
	}
	
}
