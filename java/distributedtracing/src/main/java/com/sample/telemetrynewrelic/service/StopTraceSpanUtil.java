package com.sample.telemetrynewrelic.service;

import com.sample.telemetrynewrelic.core.TraceConstants;
import com.sample.telemetrynewrelic.core.TraceSdk;
import com.sample.telemetrynewrelic.model.TraceModel;
import io.opentelemetry.trace.Status;
import java.util.Objects;

/**
 * The type Stop trace span.
 */
public final class StopTraceSpanUtil {

  /**
   * Stop.
   *
   * @param traceName the trace name
   * @param status    the status
   */
  public static void stop(final String traceName, final Status status) {
    Objects.requireNonNull(status);
    Objects.requireNonNull(traceName);

    final TraceModel dataModel =
        TraceSdk.getInstance().getStorage().get(traceName + TraceConstants.TRACE_PARENT_DATA);

    if (dataModel == null) {
      throw new UnsupportedOperationException("Trace data model cannot be null");
    }

    dataModel.getTraceSpan().setStatus(status);
    dataModel.getTraceSpan().stop();
  }

  /**
   * Instantiates a new Stop trace span util.
   */
  private StopTraceSpanUtil() {
  }
}
