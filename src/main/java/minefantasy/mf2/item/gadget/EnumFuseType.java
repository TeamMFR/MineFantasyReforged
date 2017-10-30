package minefantasy.mf2.item.gadget;

public enum EnumFuseType {
    BASIC("basic", 30), LONGFUSE("long", 60);

    public String name;
    public int time;

    private EnumFuseType(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public static EnumFuseType getType(byte i) {
        if (i == 1) {
            return LONGFUSE;
        }
        return BASIC;
    }
}
