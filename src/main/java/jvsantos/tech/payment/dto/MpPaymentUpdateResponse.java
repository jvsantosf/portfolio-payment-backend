package jvsantos.tech.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record MpPaymentUpdateResponse(
        @JsonProperty("action") String action,
        @JsonProperty("api_version") String apiVersion,
        @JsonProperty("id") String id,
        @JsonProperty("user_id") String userId,
        @JsonProperty("date_created") Timestamp dateCreated
) { }
