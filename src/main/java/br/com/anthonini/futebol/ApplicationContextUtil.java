package br.com.anthonini.futebol;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext ac;
	
	public static <T> T getBean(Class<T> type) {
		return ac.getBean(type);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext ac) {
		ApplicationContextUtil.ac = ac;
	}
}
