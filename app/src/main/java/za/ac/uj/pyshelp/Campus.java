package za.ac.uj.pyshelp;

public class Campus {
    String name, location, block, abbreviation;

    public Campus() {
    }

    public String getBlock() {
        return block;
    }




    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Campus(String name, String location, String block, String abbreviation) {
        this.name = name;
        this.location = location;
        this.block = block;
        this.abbreviation = abbreviation;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }






}
