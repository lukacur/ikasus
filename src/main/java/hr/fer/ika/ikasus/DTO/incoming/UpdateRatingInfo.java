package hr.fer.ika.ikasus.DTO.incoming;

/**
 * @author Luka Ćurić
 */
public class UpdateRatingInfo {
    private String content;
    private Integer rating;

    public UpdateRatingInfo() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
