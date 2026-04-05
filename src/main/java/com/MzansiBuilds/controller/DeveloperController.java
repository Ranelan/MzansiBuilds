package com.MzansiBuilds.controller;

import com.MzansiBuilds.service.imp.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/developer")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;


}

