package com.vintiduo.logfrontend.core.controllers;

import com.vintiduo.logfrontend.core.models.LogsViewModel;
import com.vintiduo.logfrontend.core.services.LogProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by Kostas on 2016.10.27.
 */
@Controller
public class LogsController {

    @Autowired
    LogProvider logProvider;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    Logger logger = LoggerFactory.getLogger(LogsController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "static/index.html";
    }

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public @ResponseBody
    LogsViewModel logs(Long seekFrom, Long seekTo) {
        logger.info("Try to get logs");
        try {
            return logProvider.logLines(seekFrom, seekTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
