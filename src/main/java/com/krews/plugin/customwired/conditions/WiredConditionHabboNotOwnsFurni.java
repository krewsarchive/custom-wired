package com.krews.plugin.customwired.conditions;

import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.wired.conditions.WiredConditionHabboWearsBadge;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.wired.WiredConditionType;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredConditionHabboNotOwnsFurni extends WiredConditionHabboWearsBadge {
    public WiredConditionHabboNotOwnsFurni(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }
    public WiredConditionHabboNotOwnsFurni(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }
    protected String furni = "";

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        Habbo habbo = room.getHabbo(roomUnit);

        if (habbo != null) {
            for (HabboItem item : habbo.getInventory().getItemsComponent().getItemsAsValueCollection()) {
                if (item.getBaseItem().getName().equals(furni)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String getWiredData() {
        return this.furni;
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException {
        this.furni = set.getString("wired_data");
    }

    @Override
    public void onPickUp() {
        this.furni = "";
    }

    @Override
    public WiredConditionType getType() {
        return type;
    }

    @Override
    public void serializeWiredData(ServerMessage message, Room room) {
        message.appendBoolean(false);
        message.appendInt(5);
        message.appendInt(0);
        message.appendInt(this.getBaseItem().getSpriteId());
        message.appendInt(this.getId());
        message.appendString(this.furni);
        message.appendInt(0);
        message.appendInt(0);
        message.appendInt(this.getType().code);
        message.appendInt(0);
        message.appendInt(0);
    }

    @Override
    public boolean saveData(ClientMessage packet) {
        packet.readInt();

        this.furni = packet.readString();

        return true;
    }
}
