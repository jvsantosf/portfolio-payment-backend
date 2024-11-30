package jvsantos.tech.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record MpPaymentUpdateResponse(
        @JsonProperty("action") String action,
        @JsonProperty("api_version") String apiVersion,
        @JsonProperty("data") Data data,
        @JsonProperty("date_created") Timestamp dateCreated,
        @JsonProperty("id") String id,
        @JsonProperty("live_mode") boolean liveMode,
        @JsonProperty("type") String type,
        @JsonProperty("user_id") Long userId
) {

    public record Data(
            @JsonProperty("id") String id
    ) {}

}
