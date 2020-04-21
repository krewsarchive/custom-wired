package com.krews.plugin.customwired.effects;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectWhisper;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;
import com.eu.habbo.messages.outgoing.rooms.HideDoorbellComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredEffectForwardToRoom extends WiredEffectWhisper {
    public WiredEffectForwardToRoom(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredEffectForwardToRoom(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        Habbo habbo = room.getHabbo(roomUnit);

        if (habbo == null)
            return false;

        int roomId;

        try {
            roomId = Integer.valueOf(this.message);
        } catch (Exception e) {
            return false;
        }

        if (roomId > 0)

            habbo.getClient().sendResponse(new ForwardToRoomComposer(roomId));
            Emulator.getGameEnvironment().getRoomManager().enterRoom(habbo, roomId, "", true);
            habbo.getClient().sendResponse(new HideDoorbellComposer(habbo.getHabboInfo().getUsername()));


        return true;
    }
}
