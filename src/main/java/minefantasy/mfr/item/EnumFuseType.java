package minefantasy.mfr.item;

public enum EnumFuseType {
    BASIC("basic", 30), LONGFUSE("long", 60);

    public String name;
    public int time;

    EnumFuseType(String name, int time) {
        this.name = name;
        this.time = time;
    }

    public static EnumFuseType getType(String string) {
        if (string.equals("long_fuse")) {
            return LONGFUSE;
        }
        return BASIC;
    }
}
