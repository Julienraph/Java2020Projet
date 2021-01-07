package data;

import java.util.Objects;

public abstract class AbstractTeachingUnit implements TeachingUnit {
    private final String name;
    private int credit;
    private final String  ID;

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AbstractTeachingUnit teachingUnit)) return false;
        return getName().equals(teachingUnit.getName()) && getID().equals(teachingUnit.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),getID());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AbstractTeachingUnit{");
        sb.append("name='").append(name).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", ID='").append(ID).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
