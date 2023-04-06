package com.mrgb.dispatcherservice;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class FunctionsStreamIntegrationTests {

  @Autowired
  private InputDestination input;

  @Autowired
  private OutputDestination output;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void whenOrderAcceptedThenDispatched() throws IOException {
    long orderId = 121;
    Message<OrderAcceptedMessage> inpuMessage = MessageBuilder
        .withPayload(new OrderAcceptedMessage(orderId)).build();
    Message<OrderDispatchedMessage> outpuMessage = MessageBuilder
        .withPayload(new OrderDispatchedMessage(orderId)).build();

    this.input.send(inpuMessage);
    assertThat(objectMapper.readValue(output.receive().getPayload(), OrderDispatchedMessage.class))
        .isEqualTo(outpuMessage.getPayload());
  }
}
