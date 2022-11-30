package com.example.dentalclinicmanagementsystem.controller;

import com.example.dentalclinicmanagementsystem.service.SpecimenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/specimen")
public class SpecimenController {

    @Autowired
    private SpecimenService specimenService;


}
