package hr.fer.ika.ikasus.DTO.inout;

/**
 * @author Luka Ćurić
 */
public class VehicleType {
    private String id;
    private String typeName;
    private String category;

    public VehicleType() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
