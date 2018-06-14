package io.dddbyexamples.factory.shortages.prediction.monitoring.persistence

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.mockito.Mockito

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * @author Marcin Grzejszczak
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("io.dddbyexamples.factory.shortages.prediction.persistence")
@CompileStatic
class Config {

	// https://github.com/spring-projects/spring-boot/issues/7033
	@Bean
	BeanPostProcessor shortagesBeanPostProcessor() {
		return new BeanPostProcessor() {
			@Override
			Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof ShortagesDao) {
					return Mockito.mock(ShortagesDao)
				}
				return bean
			}
		}
	}

	@CompileDynamic
	static ShortagesDao defaultStubbing(ShortagesDao dao) {
		ShortagesEntity entity = new ShortagesEntity("1")
		entity.id = 1L
		entity.version = 1L
		Mockito.doReturn([entity]).when(dao).findAll()
		return dao
	}
}
