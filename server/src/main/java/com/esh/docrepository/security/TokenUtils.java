package com.esh.docrepository.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

public class TokenUtils {

    // Key for generating signature
    public static final String MAGIC_KEY = "2637485afSGdhf1726374";

    /**
     * Creates the token to be sent to the client to hold the session.
     * 
     * Token format:
     * 
     * <pre>
     *      {username}:{expiryDate}:{serverSignature}
     * </pre>
     * 
     * @param userDetails
     *            to create a unique token
     * @return
     */
    public static String createToken(UserDetails userDetails) {
        // Token expires in 15 minutes
        long expires = System.currentTimeMillis() + 1000L * 60 * 15;

        // Use StringBuilder for building the token
        StringBuilder tokenBuilder = new StringBuilder();
        // Append username
        tokenBuilder.append(userDetails.getUsername());
        tokenBuilder.append(":");
        // Append expiration date
        tokenBuilder.append(expires);
        tokenBuilder.append(":");
        // Compute the server signature and append it
        tokenBuilder.append(TokenUtils.computeSignature(userDetails, expires));

        return tokenBuilder.toString();
    }

    /**
     * Gets the user name from the authorization token
     * 
     * @param authToken
     * @return
     */
    public static String getUserNameFromToken(String authToken) {
        if (null == authToken) {
            return null;
        }

        String[] parts = authToken.split(":");
        return parts[0];
    }

    /**
     * Validates the token agains the user details passed as parameter
     * 
     * @param authToken
     * @param userDetails
     * @return
     */
    public static boolean validateToken(String authToken, UserDetails userDetails) {
        String[] parts = authToken.split(":");
        long expires = Long.parseLong(parts[1]);
        String signature = parts[2];

        if (expires < System.currentTimeMillis()) {
            return false;
        }

        return signature.equals(TokenUtils.computeSignature(userDetails, expires));
    }

    /**
     * Computes signature by encrypting the following string and transforming it into hexadecimal:
     * 
     * <pre>
     *      {username}:{expiryDate}:{password}:{MAGIC_KEY}
     * </pre>
     * 
     * Encrypting algorithm to be used is MD5
     * 
     * @param userDetails
     * @param expires
     * @return
     */
    private static String computeSignature(UserDetails userDetails, long expires) {
        // Build string to encrypt
        StringBuilder signatureBuilder = new StringBuilder();
        signatureBuilder.append(userDetails.getUsername());
        signatureBuilder.append(":");
        signatureBuilder.append(expires);
        signatureBuilder.append(":");
        signatureBuilder.append(userDetails.getPassword());
        signatureBuilder.append(":");
        signatureBuilder.append(TokenUtils.MAGIC_KEY);

        // Configure Md5 encryption
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No MD5 algorithm available!");
        }

        // Perform encryption and transformation to hexadecimal
        return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
    }
}