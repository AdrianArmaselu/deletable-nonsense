package com.airena;

import com.google.gson.JsonObject;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;
import java.util.Collections;

public class Pubnub {

    public static void main(String[] args) {
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-b823be88-83a7-11e7-9034-1e9edc6dd7f6");
        pnConfiguration.setPublishKey("pub-c-6584b631-f619-4b57-8683-522b8c83b1a5");
        pnConfiguration.setSecure(false);

        JsonObject position = new JsonObject();
        position.addProperty("text", "show");
//        position.addProperty("text", "Hellloo From Java SDK");


        PubNub pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                System.out.println(message);
                System.out.println(pubnub.fetchMessages());
                pubnub.history().channel("Channel-ey0l7ygz4") // where to fetch history from
                        .count(100) // how many items to fetch
                        .async(new PNCallback<PNHistoryResult>() {
                            @Override
                            public void onResponse(PNHistoryResult result, PNStatus status) {
                                System.out.println("HISTORY");
                                System.out.println(status);
                            }
                        });
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubnub.subscribe().channels(Collections.singletonList("Channel-ey0l7ygz4")).execute();
        pubnub.publish()
                .message(position)
                .channel("Channel-ey0l7ygz4")
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        System.out.println(result);
                        System.out.println(status);
                        // handle response
                    }
                });

        pubnub.history().channel("Channel-ey0l7ygz4") // where to fetch history from
                .count(100) // how many items to fetch
                .async(new PNCallback<PNHistoryResult>() {
                    @Override
                    public void onResponse(PNHistoryResult result, PNStatus status) {
                        System.out.println("HISTORY");
                        System.out.println(result);
                    }
                });


    }
}
