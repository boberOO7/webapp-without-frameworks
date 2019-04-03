package com.liakhovskyi.controller;

import com.liakhovskyi.web.Request;
import com.liakhovskyi.web.ViewModel;


public interface Controller {

    ViewModel process(Request req);

}
