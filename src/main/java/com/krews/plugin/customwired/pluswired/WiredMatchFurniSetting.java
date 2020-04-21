package com.krews.plugin.customwired.pluswired;

public class WiredMatchFurniSetting
{
    public final int itemId;
    public final String state;
    public final int rotation;
    public final int x;
    public final int y;
    public final double z;
    public final boolean useZ;

    public WiredMatchFurniSetting(int itemId, String state, int rotation, int x, int y)
    {
        this.itemId = itemId;
        this.state = state.replace("\t\t\t", " ").replace("~", "-");
        this.rotation = rotation;
        this.x = x;
        this.y = y;
        this.z = -1d;
        this.useZ = false;
    }

    public WiredMatchFurniSetting(int itemId, String state, int rotation, int x, int y, double z)
    {
        this.itemId = itemId;
        this.state = state.replace("\t\t\t", " ").replace("~", "-");
        this.rotation = rotation;
        this.x = x;
        this.y = y;
        this.z = z;
        this.useZ = true;
    }

    @Override
    public String toString()
    {
        return this.toString(true);
    }

    public String toString(boolean includeState)
    {
        if(this.useZ && this.z >= 0d) {
            return this.itemId + "-" + (this.state.isEmpty() || !includeState ? " " : this.state.replace("-", "~")) + "-" + this.rotation + "-" + this.x + "-" + this.y + "-" + String.format("%f", this.z).replace(',','.');
        }
        else {
            return this.itemId + "-" + (this.state.isEmpty() || !includeState ? " " : this.state.replace("-", "~")) + "-" + this.rotation + "-" + this.x + "-" + this.y;
        }
    }

}