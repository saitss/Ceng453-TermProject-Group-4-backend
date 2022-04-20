package com.frontend.Spaces.PurchasableSpace;

import com.frontend.Player.IPlayer;

public class Purchased implements SpaceState{

    @Override
    public SpaceState Action(IPlayer player , SpaceDeed space) {

        if(player.equals(space.GetOwner())) return this;

        int rent = space.GetRent();

        player.moneyTransition(-rent);
        space.GetOwner().moneyTransition(rent);
        return this;
    }

}