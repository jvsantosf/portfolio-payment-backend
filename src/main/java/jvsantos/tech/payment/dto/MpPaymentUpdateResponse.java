package jvsantos.tech.payment.dto;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jvsantos.tech.payment.enums.PaymentStatus;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public record MpPaymentUpdateResponse(
        PaymentStatus action,
        String apiVersion,
        long id,
        String userId,
        Timestamp dateCreated
) {

    public static MpPaymentUpdateResponse fromJson(String object) {
        JsonObject jsonObject = new GsonBuilder().create().fromJson(object, JsonObject.class);

        return MpPaymentUpdateResponse.builder()
                .action(PaymentStatus.fromStatus(jsonObject.get("action").getAsString()))
                .apiVersion(jsonObject.get("api_version").getAsString())
                .id(Long.parseLong(jsonObject.get("id").getAsString()))
                .userId(jsonObject.get("user_id").getAsString())
                .dateCreated(Timestamp.valueOf(jsonObject.get("date_created").getAsString()))
                .build();
    }

}
