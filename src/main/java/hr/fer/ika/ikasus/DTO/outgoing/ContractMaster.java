package hr.fer.ika.ikasus.DTO.outgoing;

/**
 * @author Luka Ćurić
 */
public class ContractMaster {
    private Integer id;
    private String title;

    public ContractMaster() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
