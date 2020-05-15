package com.krews.plugin.customwired.effects;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectWhisper;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;


import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredEffectUnFreezeHabbo extends WiredEffectWhisper {
    public WiredEffectUnFreezeHabbo(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredEffectUnFreezeHabbo(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        Habbo habbo = room.getHabbo(roomUnit);

        if (habbo != null) {
            if (!habbo.getRoomUnit().canWalk()) {
                habbo.getRoomUnit().setCanWalk(true);
                habbo.whisper(this.message, RoomChatMessageBubbles.ALERT);
            }
        }
        return true;
    }
}
