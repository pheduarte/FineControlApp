package com.pheduarte.finecontrol.login;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class PhoneValidator {

    // Returns true if the number is valid for the user's country
    public static boolean isValidPhoneNumber(Context context, String number) {
        // Get ISO country code (like "BR", "AU", "US")
        String countryCode = getUserCountryCode(context);
        if (countryCode == null) {
            return false;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(number, countryCode);
            return phoneUtil.isValidNumber(parsedNumber);
        } catch (NumberParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Optionally format the number for display
    public static String formatInternational(Context context, String number) {
        String countryCode = getUserCountryCode(context);
        if (countryCode == null) {
            return number;
        }

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber parsedNumber = phoneUtil.parse(number, countryCode);
            return phoneUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            return number;
        }
    }

    // Helper method to get the user's country code
    private static String getUserCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            String countryIso = tm.getNetworkCountryIso();
            if (countryIso != null && !countryIso.isEmpty()) {
                return countryIso.toUpperCase(Locale.ROOT);
            }
        }
        // Fallback if device has no SIM
        return Locale.getDefault().getCountry();
    }
}
