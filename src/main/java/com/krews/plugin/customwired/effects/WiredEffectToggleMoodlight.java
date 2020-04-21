package com.krews.plugin.customwired.effects;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionMoodLight;
import com.eu.habbo.habbohotel.items.interactions.games.battlebanzai.InteractionBattleBanzaiTile;
import com.eu.habbo.habbohotel.items.interactions.wired.effects.WiredEffectWhisper;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomMoodlightData;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.messages.outgoing.habboway.nux.NuxAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.ForwardToRoomComposer;
import com.eu.habbo.messages.outgoing.rooms.HideDoorbellComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredEffectToggleMoodlight extends WiredEffectWhisper {
    public WiredEffectToggleMoodlight(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredEffectToggleMoodlight(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        Habbo habbo = room.getHabbo(roomUnit);

        if (habbo != null) {
                for (HabboItem item : room.getWallItems()) {
                    if (item instanceof InteractionMoodLight) {
                        for (HabboItem moodLight : room.getRoomSpecialTypes().getItemsOfType(InteractionMoodLight.class)) {
                            String extradata = "2,1,2,#FF00FF,255";
                            for (RoomMoodlightData data : room.getMoodlightData().valueCollection()) {
                                if (data.isEnabled()) {
                                    extradata = data.toString();
                                    break;
                                }
                            }

                            RoomMoodlightData adjusted = RoomMoodlightData.fromString(extradata);
                            if (RoomMoodlightData.fromString(moodLight.getExtradata()).isEnabled()) adjusted.disable();
                            moodLight.setExtradata(adjusted.toString());

                            moodLight.needsUpdate(true);
                            room.updateItem(moodLight);
                            Emulator.getThreading().run(moodLight);
                        }
                    }
                }
                return true;
            }
            return false;
        }
}
