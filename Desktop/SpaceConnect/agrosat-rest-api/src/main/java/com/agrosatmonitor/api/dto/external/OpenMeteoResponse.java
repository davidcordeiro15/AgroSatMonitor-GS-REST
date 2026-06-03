package com.agrosatmonitor.api.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenMeteoResponse {

    private Double latitude;
    private Double longitude;

    @JsonProperty("current")
    private CurrentData current;

    @JsonProperty("daily")
    private DailyData daily;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentData {
        @JsonProperty("temperature_2m")
        private Double temperature2m;
        @JsonProperty("relative_humidity_2m")
        private Double relativeHumidity2m;
        @JsonProperty("wind_speed_10m")
        private Double windSpeed10m;
        @JsonProperty("precipitation")
        private Double precipitation;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyData {
        @JsonProperty("shortwave_radiation_sum")
        private List<Double> shortwaveRadiationSum;
        @JsonProperty("precipitation_sum")
        private List<Double> precipitationSum;
        @JsonProperty("et0_fao_evapotranspiration")
        private List<Double> et0FaoEvapotranspiration;
    }
}
