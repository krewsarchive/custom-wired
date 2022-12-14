package com.krews.plugin.customwired.effects;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.InteractionWiredEffect;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.habbohotel.wired.WiredEffectType;
import com.eu.habbo.habbohotel.wired.WiredHandler;
import com.eu.habbo.messages.ClientMessage;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.rooms.items.FloorItemOnRollerComposer;
import gnu.trove.set.hash.THashSet;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WiredEffectRaiseFurni extends InteractionWiredEffect {
    public static final WiredEffectType type = WiredEffectType.TELEPORT;

    private THashSet<HabboItem> items = new THashSet<>();

    private int offset = 0;

    public WiredEffectRaiseFurni(ResultSet set, Item baseItem) throws SQLException {
        super(set, baseItem);
    }

    public WiredEffectRaiseFurni(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells) {
        super(id, userId, item, extradata, limitedStack, limitedSells);
    }

    @Override
    public void serializeWiredData(ServerMessage message, Room room) {
        THashSet<HabboItem> items = new THashSet<>();

        for (HabboItem item : this.items) {
            if (item.getRoomId() != this.getRoomId() || Emulator.getGameEnvironment().getRoomManager().getRoom(this.getRoomId()).getHabboItem(item.getId()) == null)
                items.add(item);
        }

        for (HabboItem item : items) {
            this.items.remove(item);
        }
        message.appendBoolean(false);
        message.appendInt(WiredHandler.MAXIMUM_FURNI_SELECTION);
        message.appendInt(this.items.size());
        for (HabboItem item : this.items)
            message.appendInt(item.getId());
        message.appendInt(this.getBaseItem().getSpriteId());
        message.appendInt(this.getId());
        message.appendString("");
        message.appendInt(0);
        message.appendInt(type.code);
        message.appendInt(0);
        message.appendInt(this.offset);
        message.appendInt(0);
        message.appendString("");
    }

    @Override
    public boolean saveData(ClientMessage packet, GameClient gameClient) {
        packet.readInt();
        packet.readString();

        this.items.clear();

        int count = packet.readInt();

        for (int i = 0; i < count; i++) {
            this.items.add(Emulator.getGameEnvironment().getRoomManager().getRoom(this.getRoomId()).getHabboItem(packet.readInt()));
        }

        this.offset = packet.readInt();

        return true;
    }

    @Override
    public WiredEffectType getType() {
        return type;
    }

    @Override
    public boolean execute(RoomUnit roomUnit, Room room, Object[] stuff) {
        for (HabboItem item : this.items) {
            if (item.getRoomId() == 0)
                continue;
            double offsetZ = ((this.offset + 0.5));
            double totalHeight = item.getZ() + offsetZ;
            if (totalHeight > 40) break;
            room.sendComposer(new FloorItemOnRollerComposer(item, null, room.getLayout().getTile(item.getX(), item.getY()), offsetZ, room).compose());
            room.updateHabbosAt(item.getX(), item.getY());
            room.updateBotsAt(item.getX(), item.getY());
        }

        return true;
    }

    @Override
    public String getWiredData() {
        StringBuilder wiredData = new StringBuilder(this.offset + "\t");

        if (this.items != null && !this.items.isEmpty()) {
            for (HabboItem item : this.items) {
                wiredData.append(item.getId()).append(";");
            }
        }

        return wiredData.toString();
    }

    @Override
    public void loadWiredData(ResultSet set, Room room) throws SQLException {
        this.items = new THashSet<>();
        String wiredData = set.getString("wired_data");

        if (wiredData.contains("\t")) {
            String[] data = wiredData.split("\t");

            try {
                this.offset = Integer.valueOf(data[0]);
            } catch (Exception e) {
            }

            if (data.length >= 2) {
                if (data[1].contains(";")) {
                    for (String s : data[1].split(";")) {
                        HabboItem item = room.getHabboItem(Integer.valueOf(s));

                        if (item != null)
                            this.items.add(item);
                    }
                }
            }
        }
    }

    @Override
    public void onPickUp() {
        this.offset = 0;
        this.items.clear();
        this.setDelay(0);
    }
}
