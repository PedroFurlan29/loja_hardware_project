package com.lojahardware.unicep.ingest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DummyJsonProductDTO {

    private Integer id;
    private String title;
    private String description;
    private Integer price;
    private String category;
    private String brand;
    private Double rating;
    private Integer stock;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DummyJsonResponse {
        private java.util.List<DummyJsonProductDTO> products;
        private Integer total;
        private Integer skip;
        private Integer limit;
    }
}
