package com.cdrn.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocation {
    @GeoSpatialIndexed
    private double lat;
    @GeoSpatialIndexed
    private double lng;
    private String address;
}
