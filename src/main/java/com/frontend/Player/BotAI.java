package com.frontend.Player;

public class BotAI extends  PlayerAbstract{

    @Override
    public boolean purchaseSpace(int spacePrice) {
        if(getMoney() > spacePrice){
            return  true;
        }
        return  false;
    }
}