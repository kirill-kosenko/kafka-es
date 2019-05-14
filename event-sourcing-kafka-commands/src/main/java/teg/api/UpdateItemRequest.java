package teg.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateItemRequest {

    private Long dueDate;

    private Integer requiredTime;

    private List<String> tags;

    private String associatedList;

    @Override
    public String toString() {
        return "UpdateItemRequest{" +
                "dueDate=" + dueDate +
                ", requiredTime=" + requiredTime +
                ", tags=" + tags +
                ", associatedList='" + associatedList + '\'' +
                '}';
    }
}
