package data;

import java.util.Objects;

public abstract class AbstractTeachingUnit implements TeachingUnit {
    private String name;
    private int credit;
    private String ID;

    public AbstractTeachingUnit(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public AbstractTeachingUnit(String name, String ID, int credit) {
        this.name = name;
        this.credit = credit;
        this.ID = ID;
    }


    public String getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public int getCredit() { return this.credit; }

    @Override
    public boolean equals(Object o) {
        AbstractTeachingUnit that = (AbstractTeachingUnit) o;
        return ID.equals(that.ID);
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
