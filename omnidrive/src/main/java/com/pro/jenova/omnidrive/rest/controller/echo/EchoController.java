package com.pro.jenova.omnidrive.rest.controller.echo;

import com.pro.jenova.omnidrive.rest.controller.RestResponse;
import com.pro.jenova.omnidrive.rest.controller.VoidResponse;
import com.pro.jenova.omnidrive.rest.controller.client.EchoClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/echo")
public class EchoController {

    private static final Logger logger = getLogger(EchoController.class);

    @Autowired
    private EchoClient echoClient;

    @RequestMapping(value = "/{times}", method = RequestMethod.GET)
    public ResponseEntity<RestResponse> create(@PathVariable(value = "times") int times) {
        logger.info("Echo: [{}]", times);

        if (times > 0) {
            echoClient.echo(times - 1);
        }

        return VoidResponse.ok();
    }

}
