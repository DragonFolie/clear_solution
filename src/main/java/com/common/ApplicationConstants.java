package com.common;

import lombok.experimental.UtilityClass;

/**
 * Constants for app.
 */

@UtilityClass
public class ApplicationConstants {

  /**
   * Inner utility class for dto validation.
   */
  @UtilityClass
  public class DataValidation {

    public static final int MIN_SIZE_OF_NAME = 1;
    public static final int MAX_SIZE_OF_NAME = 255;
    public static final int MIN_SIZE_OF_SURNAME = 1;
    public static final int MAX_SIZE_OF_SURNAME = 255;
    public static final int MIN_SIZE_OF_EMAIL = 1;
    public static final int MAX_SIZE_OF_EMAIL = 100;
    public static final int MIN_SIZE_OF_ADDRESS = 1;
    public static final int MAX_SIZE_OF_ADDRESS = 255;
  }

}
