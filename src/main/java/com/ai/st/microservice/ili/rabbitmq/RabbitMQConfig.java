package com.ai.st.microservice.ili.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${st.rabbitmq.queueIntegrations.queue}")
	public String queueIntegrationsName;

	@Value("${st.rabbitmq.queueIntegrations.exchange}")
	public String exchangeIntegrationsName;

	@Value("${st.rabbitmq.queueIntegrations.routingkey}")
	public String routingkeyIntegrationsName;

	@Value("${st.rabbitmq.queueUpdateIntegration.queue}")
	public String queueUpdateIntegrationsName;

	@Value("${st.rabbitmq.queueUpdateIntegration.exchange}")
	public String exchangeUpdateIntegrationsName;

	@Value("${st.rabbitmq.queueUpdateIntegration.routingkey}")
	public String routingkeyUpdateIntegrationsName;

	@Value("${st.rabbitmq.queueExports.queue}")
	public String queueExportsName;

	@Value("${st.rabbitmq.queueExports.exchange}")
	public String exchangeExportsName;

	@Value("${st.rabbitmq.queueExports.routingkey}")
	public String routingkeyExportsName;

	@Value("${st.rabbitmq.queueUpdateExport.queue}")
	public String queueUpdateExportName;

	@Value("${st.rabbitmq.queueUpdateExport.exchange}")
	public String exchangeUpdateExportName;

	@Value("${st.rabbitmq.queueUpdateExport.routingkey}")
	public String routingkeyUpdateExportName;

	@Value("${st.rabbitmq.queueIlivalidator.queue}")
	public String queueIlivalidatorName;

	@Value("${st.rabbitmq.queueIlivalidator.exchange}")
	public String exchangeIlivalidatorName;

	@Value("${st.rabbitmq.queueIlivalidator.routingkey}")
	public String routingkeyIlivalidatorName;

	@Value("${st.rabbitmq.queueUpdateStateSupply.queue}")
	public String queueUpdateStateSupplyName;

	@Value("${st.rabbitmq.queueUpdateStateSupply.exchange}")
	public String exchangeUpdateStateSupplyName;

	@Value("${st.rabbitmq.queueUpdateStateSupply.routingkey}")
	public String routingkeyUpdateStateSupplyName;

	@Value("${st.rabbitmq.queueIli.queue}")
	public String queueIliName;

	@Value("${st.rabbitmq.queueIli.exchange}")
	public String exchangeIliName;

	@Value("${st.rabbitmq.queueIli.routingkey}")
	public String routingkeyIliName;

	// queue integrations

	@Bean
	public Queue queueIntegrations() {
		return new Queue(queueIntegrationsName, false);
	}

	@Bean
	public DirectExchange exchangeIntegrations() {
		return new DirectExchange(exchangeIntegrationsName);
	}

	@Bean
	public Binding bindingQueueIntegrations() {
		return BindingBuilder.bind(queueIntegrations()).to(exchangeIntegrations()).with(routingkeyIntegrationsName);
	}

	// queue update integrations

	@Bean
	public Queue queueUpdateIntegrations() {
		return new Queue(queueUpdateIntegrationsName, false);
	}

	@Bean
	public DirectExchange exchangeUpdateIntegrations() {
		return new DirectExchange(exchangeUpdateIntegrationsName);
	}

	@Bean
	public Binding bindingQueueUpdateIntegrations() {
		return BindingBuilder.bind(queueUpdateIntegrations()).to(exchangeUpdateIntegrations())
				.with(routingkeyUpdateIntegrationsName);
	}

	// queue exports

	@Bean
	public Queue queueExports() {
		return new Queue(queueExportsName, false);
	}

	@Bean
	public DirectExchange exchangeExports() {
		return new DirectExchange(exchangeExportsName);
	}

	@Bean
	public Binding bindingQueueExports() {
		return BindingBuilder.bind(queueExports()).to(exchangeExports()).with(routingkeyExportsName);
	}

	// queue update exports

	@Bean
	public Queue queueUpdateExports() {
		return new Queue(queueUpdateExportName, false);
	}

	@Bean
	public DirectExchange exchangeUpdateExports() {
		return new DirectExchange(exchangeUpdateExportName);
	}

	@Bean
	public Binding bindingQueueUpdateExports() {
		return BindingBuilder.bind(queueUpdateExports()).to(exchangeUpdateExports()).with(routingkeyUpdateExportName);
	}

	// queue ilivalidator

	@Bean
	public Queue queueIlivalidator() {
		return new Queue(queueIlivalidatorName, false);
	}

	@Bean
	public DirectExchange exchangeIlivalidator() {
		return new DirectExchange(exchangeIlivalidatorName);
	}

	@Bean
	public Binding bindingQueueIlivalidator() {
		return BindingBuilder.bind(queueIlivalidator()).to(exchangeIlivalidator()).with(routingkeyIlivalidatorName);
	}

	// queue update state supply

	@Bean
	public Queue queueUpdateStateSupply() {
		return new Queue(queueUpdateStateSupplyName, false);
	}

	@Bean
	public DirectExchange exchangeUpdateStateSupply() {
		return new DirectExchange(exchangeUpdateStateSupplyName);
	}

	@Bean
	public Binding bindingQueueUpdateStateSupply() {
		return BindingBuilder.bind(queueUpdateStateSupply()).to(exchangeUpdateStateSupply())
				.with(routingkeyUpdateStateSupplyName);
	}

	// queue ili

	@Bean
	public Queue queueIli() {
		return new Queue(queueIliName, false);
	}

	@Bean
	public DirectExchange exchangeIli() {
		return new DirectExchange(exchangeIliName);
	}

	@Bean
	public Binding bindingQueueIli() {
		return BindingBuilder.bind(queueIli()).to(exchangeIli()).with(routingkeyIliName);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

}
