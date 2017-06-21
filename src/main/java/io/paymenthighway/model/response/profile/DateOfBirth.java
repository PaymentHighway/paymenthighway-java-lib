package io.paymenthighway.model.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DateOfBirth {

  @JsonProperty(value = "year", required = true)
  private int year;

  @JsonProperty(value = "month", required = true)
  private int month;

  @JsonProperty(value = "day", required = true)
  private int day;

  /**
   * @return DOB Year
   */
  public int getYear() {
    return year;
  }

  /**
   * @return DOB Month
   */
  public int getMonth() {
    return month;
  }

  /**
   * @return DOB Day
   */
  public int getDay() {
    return day;
  }
}
