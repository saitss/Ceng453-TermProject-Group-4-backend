package com.Spaces.PurchasableSpace;

import com.Player.IPlayer;
import com.Player.Player;


public class RailRoad  extends SpaceDeed {

    public  RailRoad(String name , int cost , int rent , int position){
       this.name = name;
       this.rent = rent;
       this.cost = cost;
       this.position = position;
       state = new NotPurchased();
    }

    @Override
    public void action(IPlayer player) {
        state = state.Action(player , this);
    }


    public void Purchase(IPlayer player , int cost){
        player.moneyTransition(cost);
        player.purchaseSpecialTile();
        setOwner(player);
        this.rent = player.getNumOfSpecialTile() * rent;
    }


}
