package com.ashwin.dropwizard.resource;

import com.ashwin.dropwizard.model.KafkaRequest;
import com.ashwin.dropwizard.repository.ProducerRepository;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/kafka")
@Produces(MediaType.APPLICATION_JSON)
public class ProducerResource {
    private static final Logger logger = LoggerFactory.getLogger(ProducerResource.class);

    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMessageToTopic(KafkaRequest kafkaRequest) throws Exception {
        Map<String, String> response = new HashMap<>();
        ProducerRepository producerRepository = new ProducerRepository();
        String status = producerRepository.sendMessage(kafkaRequest.getTopic(), kafkaRequest.getUser());
        if ("success".equalsIgnoreCase(status)) {
            response.put("Status is: " + status, "Message has been sent to Topic: " + kafkaRequest.getTopic());
        } else {
            response.put("Status is: " + status, "Error in sending Message to Topic: " + kafkaRequest.getTopic());
        }
        return Response.ok(response).build();
    }
}
