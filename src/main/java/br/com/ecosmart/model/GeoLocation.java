package br.com.ecosmart.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeoLocation {

    @NotBlank(message = "O tipo geoespacial (type) é obrigatório")
    @JsonProperty("type")
    private String type = "Point";

    @NotEmpty(message = "As coordenadas geoespaciais (coordinates) são obrigatórias")
    @Size(min = 2, max = 2, message = "As coordenadas devem conter [longitude, latitude]")
    @JsonProperty("coordinates")
    private List<Double> coordinates;

    public GeoLocation() {
    }

    public GeoLocation(String type, List<Double> coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public static GeoLocation of(double longitude, double latitude) {
        return new GeoLocation("Point", List.of(longitude, latitude));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}

