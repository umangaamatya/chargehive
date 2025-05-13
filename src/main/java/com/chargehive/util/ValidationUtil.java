package com.chargehive.util;

public class ValidationUtil {
	/**
     * Validates if the station ID is within the range of 100000 to 999999.
     *
     * @param stationId the station ID to validate
     * @return true if the ID is in range, false otherwise
     */
    public static boolean isIdInRange(String stationId) {
        try {
            int id = Integer.parseInt(stationId);

            if (id < 1000 || id > 9999999) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Validates if the station ID starts with "10" followed by four digits.
     *
     * @param stationId the station ID to validate
     * @return true if the ID starts correctly, false otherwise
     */
    public static boolean isIdStartCorrect(String stationId) {
        try {
            if (!stationId.matches("^13\\d{4}$")) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Validates if the name contains only alphabets and spaces.
     *
     * @param name the name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean isNameValid(String name) {
        return name.matches("^[a-zA-Z ]+$");
    }

    /**
     * Checks if a given string is numeric.
     *
     * @param num the string to check
     * @return true if the string is numeric, false otherwise
     */
    public static boolean isNum(String num) {
        try {
            Long.parseLong(num);  // allows longer numeric strings
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the price is within the range of 300 to 800.
     *
     * @param price the price to validate
     * @return true if the price is valid, false otherwise
     */
    public static boolean isPriceValid(String price) {
        try {
            int pri = Integer.parseInt(price);

            if (pri < 300 || pri > 800) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return true;
        }
    }
    
    /**
     * Validates if the ports is within the range of 1 to 12.
     *
     * @param ports to validate
     * @return true if the stock is valid, false otherwise
     */
    public static boolean isPortsValid(String ports) {
        try {
            int pt = Integer.parseInt(ports);

            if (pt < 1 || pt > 12) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return true;
        }
    }
    
    /**
     * Validates if the ports is within the permissible limit (up to 20).
     *
     * @param ports the ports to validate
     * @return true if the ports is valid, false otherwise
     */
    public static boolean isPortsEnough(String ports) {
        try {
            int port = Integer.parseInt(ports);

            if (port > 20) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return true;
        }
    }


    /**
     * Placeholder for validating type (currently always returns true).
     *
     * @param type the type to validate
     * @return true if the type is valid, false otherwise
     */
    public static boolean isTypeValid(String type) {
            // Check if the value matches one of the predefined valid type options
    	return "Fast".equals(type) || "Slow".equals(type);
        
    }

    /**
     * Checks if a given value is empty or null.
     *
     * @param value the value to check
     * @return true if the value is empty or null, false otherwise
     */
    public static boolean IsEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    
    /**
     * Validates if the password is at least 6 characters long and contains at least:
     * - one uppercase letter
     * - one number
     * - one special character (@, $, !, %, *, ?, &)
     *
     * @param password the password to validate
     * @return true if the password meets the criteria, false otherwise
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(pattern);
    }
}
