package com.sideproject.starter.controller;

import com.sideproject.starter.dao.SavingTipzDao;
import com.sideproject.starter.exception.DaoException;
import com.sideproject.starter.model.SavingTipz;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SavingTipzController {

    final private SavingTipzDao savingTipzDao;

    public SavingTipzController(SavingTipzDao savingTipzDao) {
        this.savingTipzDao = savingTipzDao;
    }


    @RequestMapping(path = "/",method = RequestMethod.GET)
    public SavingTipz getMessage(){
        try {
            SavingTipz savingTipz = new SavingTipz();
            savingTipz.setMessage(savingTipzDao.getSavingTipz().getMessage());
            return savingTipz;
        } catch (ResourceAccessException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/all",method = RequestMethod.GET)
    public List<SavingTipz> getAllMessages(){
        List<SavingTipz> savingTipzList;
        savingTipzList = savingTipzDao.getAllSavingTipzs();
        if (savingTipzList == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "SavingTipz List Not Found");
        } else {
            return savingTipzList;
        }
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST)
    public SavingTipz setMessage(@Valid @RequestBody SavingTipz savingMessage ){
        try{
            return savingTipzDao.addMessage(savingMessage);
        } catch (DaoException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error adding new message", e);
        }
    }

    @RequestMapping(path = "/delete/{messages}", method = RequestMethod.DELETE)
    public void deleteMessage(@PathVariable("messages") String messages) {
        try {
            savingTipzDao.deleteMessage(messages);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting message", e);
        }
    }

}
