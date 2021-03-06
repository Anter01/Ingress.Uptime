// Copyright (c) Dolittle. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package io.dolittle.ingress.uptime.web.rest;

import io.dolittle.ingress.uptime.web.component.KeyManager;
import io.dolittle.ingress.uptime.web.model.Response;
import io.dolittle.ingress.uptime.web.util.UptimeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "dolittle/ingress", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
@Slf4j
public class PingController {

    private final KeyManager keyManager;

    public PingController(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    @RequestMapping(value = "/ping")
    public Response Ping(@RequestHeader(value = UptimeConstants.CHALLENGE_KEY) String verificationCode, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.debug("Ping request received: {}, challenge-key: {}", httpServletRequest.getRemoteHost(), verificationCode);

        String responseKey = keyManager.hashKeyWithSalt(verificationCode);
        httpServletResponse.setHeader(UptimeConstants.RESPONSE_KEY, responseKey);
        return Response.ok(responseKey);
    }


}
