package com.sideproject.starter.dao;

import com.sideproject.starter.model.SavingTipz;

import java.util.List;

public interface SavingTipzDao {

    public SavingTipz getSavingTipz();

    public List<SavingTipz> getAllSavingTipzs();

    public SavingTipz addMessage(SavingTipz savingTipz);

    public void deleteMessage(String messages);
}
