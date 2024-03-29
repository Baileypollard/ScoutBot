import org.osbot.rs07.api.map.Area;

public enum Loc {

    LUMBRIDGE_GROUND_FLOOR(new Area(3204, 3206, 3230, 3231), 0),
    LUMBRIDGE_UPPER_FLOOR(new Area(3204, 3206, 3230, 3231), 2),
    REV_SCOUT_AREA(new Area(3226, 10134, 3258, 10187), 0),
    LAVA_MAZE_SCOUT_AREA(new Area(3021, 3822, 3045, 3844), 0);

    private Area area;

    Loc(Area area, int plane) {
        this.area = area;
        this.area.setPlane(plane);
    }
    public Area getArea() {
        return area;
    }
}
