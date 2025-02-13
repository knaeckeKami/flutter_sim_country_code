package com.example.flutter_sim_country_code;

import android.content.Context;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

public class FlutterSimCountryCodePlugin implements FlutterPlugin, MethodCallHandler {

    private static final String CHANNEL_NAME = "flutter_sim_country_code";
    private MethodChannel channel;
    private Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        BinaryMessenger messenger = binding.getBinaryMessenger();
        context = binding.getApplicationContext();
        channel = new MethodChannel(messenger, CHANNEL_NAME);
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if ("getSimCountryCode".equals(call.method)) {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (manager != null) {
                String countryId = manager.getSimCountryIso();
                if (countryId != null) {
                    result.success(countryId.toUpperCase());
                    return;
                }
            }
            result.error("SIM_COUNTRY_CODE_ERROR", null, null);
        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
        channel = null;
        context = null;
    }
}
