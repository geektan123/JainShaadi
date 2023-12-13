package com.example.jainshaadi;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private List<CardItem> cardItemList;

    public List<CardItem> getCardItemList() {
        if (cardItemList == null) {
            cardItemList = new ArrayList<>();
        }
        return cardItemList;
    }

    public void setCardItemList(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }
}
