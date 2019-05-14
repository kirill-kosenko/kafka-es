package teg.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {

    private String description;

    @Override
    public String toString() {
        return "CreateItemRequest{" +
                "description='" + description + '\'' +
                '}';
    }
}
