package com.beanbeanjuice.tables.warns;

import org.jetbrains.annotations.NotNull;

/**
 * A class mimicking the warns table in the database.
 *
 * @author beanbeanjuice
 * @since 1.0.0
 */
public class Warn {

    private final int WARN_ID;
    private final String USER_ID;
    private final String USER_NICKNAME;
    private final String ISSUER_ID;
    private final String ISSUER_NICKNAME;
    private final String DATE;
    private final String REASON;
    private Boolean isActive;

    /**
     * Creates a new {@link Warn} object.
     * @param warnID The {@link Integer ID} of the warn.
     * @param userID The {@link String ID} of the user who was warned.
     * @param userNickname The {@link String nickname} of the user who was warned.
     * @param issuerID The {@link String ID} of the person who issued the warn.
     * @param issuerNickname The {@link String nickname} of the user who issued the warn.
     * @param date The {@link String date} the warn was issued.
     * @param reason The {@link String reason} for the warn.
     * @param isActive True, if the warn is active.
     */
    public Warn(@NotNull Integer warnID, @NotNull String userID, @NotNull String userNickname,
                @NotNull String issuerID, @NotNull String issuerNickname, @NotNull String date,
                @NotNull String reason, @NotNull Boolean isActive) {
        this.WARN_ID = warnID;
        this.USER_ID = userID;
        this.USER_NICKNAME = userNickname;
        this.ISSUER_ID = issuerID;
        this.ISSUER_NICKNAME = issuerNickname;
        this.DATE = date;
        this.REASON = reason;
        this.isActive = isActive;
    }

    /**
     * @return The {@link Integer ID} of the warn.
     */
    @NotNull
    public Integer getWarnID() {
        return WARN_ID;
    }

    /**
     * @return The {@link String ID} of the user who was warned.
     */
    @NotNull
    public String getUserID() {
        return USER_ID;
    }

    /**
     * @return The {@link String nickname} of the user who was warned.
     */
    @NotNull
    public String getUserNickname() {
        return USER_NICKNAME;
    }

    /**
     * @return The {@link String ID} of the user who issued the warn.
     */
    @NotNull
    public String getIssuerID() {
        return ISSUER_ID;
    }

    /**
     * @return The {@link String nickname} of the user who issued the warn.
     */
    @NotNull
    public String getIssuerNickname() {
        return ISSUER_NICKNAME;
    }

    /**
     * @return The {@link String date} the warn was issued.
     */
    @NotNull
    public String getDateString() {
        return DATE;
    }

    /**
     * @return The {@link String reason} the warn was issued.
     */
    @NotNull
    public String getReason() {
        return REASON;
    }

    /**
     * @return True, if the warn is active.
     */
    @NotNull
    public Boolean isActive() {
        return isActive;
    }

    /**
     * Change the active status of the warn.
      * @param isActive True, if the warn is active.
     */
    protected void changeActiveStatus(@NotNull Boolean isActive) {
        this.isActive = isActive;
    }

}
