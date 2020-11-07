package com.sample.telemetrynewrelic.utils;

import com.sample.telemetrynewrelic.core.TraceConstants;
import com.sample.telemetrynewrelic.core.TraceSdk;
import com.sample.telemetrynewrelic.newrelic.ContextConstants;
import io.grpc.Context;

/**
 * The type Extract trace context.
 */
public final class ExtractTraceContextUtil {

  /**
   * Extract context.
   *
   * @return the context
   */
  public static Context extract() {
    return
        TraceSdk.getInstance().getStorage()
            .get(ContextConstants.REMOTE_PARENT_CONTEXT
                + TraceConstants.TRACE_PARENT_DATA).getTraceSpan().withSpan(Context.current());
  }

  /**
   * Instantiates a new Extract trace context.
   */
  private ExtractTraceContextUtil() {
    //Empty.
  }
}
