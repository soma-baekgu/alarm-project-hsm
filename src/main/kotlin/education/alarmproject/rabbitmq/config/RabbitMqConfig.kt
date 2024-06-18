// package education.alarmproject.rabbitmq.config
//
// import org.springframework.amqp.core.Binding
// import org.springframework.amqp.core.BindingBuilder
// import org.springframework.amqp.core.DirectExchange
// import org.springframework.amqp.core.Queue
// import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
// import org.springframework.amqp.rabbit.connection.ConnectionFactory
// import org.springframework.amqp.rabbit.core.RabbitTemplate
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
// import org.springframework.amqp.support.converter.MessageConverter
// import org.springframework.beans.factory.annotation.Value
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
//
// @Configuration
// class RabbitMqConfig(
//    private val rabbitMqProperties: RabbitMqProperties,
//    @Value("\${rabbitmq.queue.name}") private val queueName: String,
//    @Value("\${rabbitmq.exchange.name}") private val exchangeName: String,
//    @Value("\${rabbitmq.routing.key}") private val routingKey: String,
// ) {
//    // org.springframework.amqp.core.Queue
//    @Bean
//    fun queue(): Queue {
//        return Queue(queueName)
//    }
//
//    /**
//     * 지정된 Exchange 이름으로 Direct Exchange Bean 을 생성
//     */
//    @Bean
//    fun directExchange(): DirectExchange {
//        return DirectExchange(exchangeName)
//    }
//
//    /**
//     * 주어진 Queue 와 Exchange 을 Binding 하고 Routing Key 을 이용하여 Binding Bean 생성
//     * Exchange 에 Queue 을 등록한다고 이해하자
//     */
//    @Bean
//    fun binding(
//        queue: Queue,
//        exchange: DirectExchange,
//    ): Binding {
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey)
//    }
//
//    /**
//     * RabbitMQ 연동을 위한 ConnectionFactory 빈을 생성하여 반환
//     */
//    @Bean
//    fun connectionFactory(): CachingConnectionFactory {
//        val connectionFactory = CachingConnectionFactory()
//        connectionFactory.setHost(rabbitMqProperties.host)
//        connectionFactory.port = rabbitMqProperties.port
//        connectionFactory.username = rabbitMqProperties.username
//        connectionFactory.setPassword(rabbitMqProperties.password)
//        return connectionFactory
//    }
//
//    /**
//     * RabbitTemplate
//     * ConnectionFactory 로 연결 후 실제 작업을 위한 Template
//     */
//    @Bean
//    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
//        val rabbitTemplate = RabbitTemplate(connectionFactory)
//        rabbitTemplate.messageConverter = jackson2JsonMessageConverter()
//        return rabbitTemplate
//    }
//
//    /**
//     * 직렬화(메세지를 JSON 으로 변환하는 Message Converter)
//     */
//    @Bean
//    fun jackson2JsonMessageConverter(): MessageConverter {
//        return Jackson2JsonMessageConverter()
//    }
// }
