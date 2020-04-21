package com.krews.plugin.customwired.effects;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredEffect;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredTrigger;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.wired.WiredEffectType;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.inventory.InventoryRefreshComposer;
import gnu.trove.procedure.TObjectProcedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WiredEffectRemoveInventoryFurni extends InteractionWiredEffect {
    public static final WiredEffectType type = WiredEffectType.SHOW_MESSAGE;

    private String furni = "";

    public WiredEffectRemoveInventoryFurni(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredEffectRemoveInventoryFurni(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void serializeWiredData(ServerMessage message, Room room) {
        message.appendBoolean(false);
        message.appendInt(0);
        message.appendInt(0);
        message.appendInt(this.getBaseItem().getSpriteId());
        message.appendInt(this.getId());
        message.appendString(this.furni);
        message.appendInt(0);
        message.appendInt(0);
        message.appendInt(type.code);
        message.appendInt(this.getDelay());

        if (this.requiresTriggeringUser()) {
            final List<Integer> invalidTriggers = new ArrayList<>();
            room.getRoomSpecialTypes().getTriggers(this.getX(), this.getY()).forEach(new TObjectProcedure<InteractionWiredTrigger>() {
                @Override
                public boolean execute(InteractionWiredTrigger object) {
                    if (!object.isTriggeredByRoomUnit()) {
                        invalidTriggers.add(object.getBaseItem().getSpriteId());
                    }
                    return true;
                }
            });
            message.appendInt(invalidTriggers.size());
            for (Integer i : invalidTriggers) {
                message.appendInt(i);
            }
        } else {
            message.appendInt(0);
        }
    }

    @Override
    public boolean saveData(ClientMessage packet, GameClient gameClient) {
        if (gameClient.getHabbo().hasPermission("acc_superwired")) {
            packet.readInt();

            this.furni = packet.readString();

            packet.readInt();
            this.setDelay(packet.readInt());
            return true;

        }

        gameClient.getHabbo().whisper("What the fuck are you doing?", RoomChatMessageBubbles.ALERT);
        return false;
    }

    @Override
    public WiredEffectType getType() {
        return type;
    }
    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        Habbo habbo = room.getHabbo(roomUnit);
        if (habbo != null) {
            for (HabboItem item : habbo.getInventory().getItemsComponent().getItemsAsValueCollection()) {
                if (item.getBaseItem().getName().equals(furni)) {
                    item.needsDelete(true);
                    habbo.removeFurniture(item);
                    Emulator.getThreading().run(item);
                    habbo.getClient().sendResponse(new InventoryRefreshComposer());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String getWiredData() {
        return this.getDelay() + "\t" + this.furni;
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException {
        String wireData = set.getString("wired_data");
        String[] data = wireData.split("\t");

        if (data.length >= 2) {
            super.setDelay(Integer.valueOf(data[0]));
            this.furni = data[1];
        }
    }

    @Override
    public void onPickUp() {
        this.furni = "";
        this.setDelay(0);
    }

    @Override
    public boolean requiresTriggeringUser() {
        return true;
    }
}

