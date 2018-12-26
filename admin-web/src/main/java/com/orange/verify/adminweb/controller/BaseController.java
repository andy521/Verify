package com.orange.verify.adminweb.controller;

import com.orange.verify.adminweb.annotation.ParameterError;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

@Controller
public class BaseController {

    public void parametric(BindingResult result) throws ParameterError {
        if(result.hasErrors()){
            String defaultMessage = result.getAllErrors().get(0).getDefaultMessage();
            throw new ParameterError(defaultMessage);
        }
    }

}
