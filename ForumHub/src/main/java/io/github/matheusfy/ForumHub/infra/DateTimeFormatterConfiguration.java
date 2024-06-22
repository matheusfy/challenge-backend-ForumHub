package io.github.matheusfy.ForumHub.infra;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatterConfiguration {

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
}
